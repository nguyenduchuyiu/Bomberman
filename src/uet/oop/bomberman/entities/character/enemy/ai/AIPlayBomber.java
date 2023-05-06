package uet.oop.bomberman.entities.character.enemy.AI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.CoincidentEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;

public class AIPlayBomber {
    Bomber bomber;
    int direction = -1;
    public GameBoard gameBoard;
    public List<Bomb> bombs;
    boolean isSafe = true;
    public Enemy target; // Mục tiêu của bomber
    boolean targetSlain = true; // Kiểm tra xem mục tiêu chếch chưa
    int steps;

    public AIPlayBomber(Bomber bomber, GameBoard gameBoard) {
        this.bomber = bomber;
        this.gameBoard = gameBoard;
        bombs = gameBoard.getBombs();
        steps = 0;
    }

    public void autoMove() {
        if (steps <= 0) {
            direction = findDirection(target);
            steps = 16;
        }
        int xx = 0;
        int yy = 0;
        switch(direction) {
            case 0:
                yy--;
                break;
            case 1:
                xx++;
                break;
            case 2:
                yy++;
                break;
            case 3:
                xx--;
                break;
        }
        if (xx != 0 || yy != 0) {
            steps -= 1;
            bomber.move(xx * Game.getBomberSpeed(), yy * Game.getBomberSpeed());
            bomber._moving = true;
        } else {
            bomber._moving = false;
            steps = 0;
        }
    }

    public int findDirection(Entity e) {

        if (e == null) {
            return -1;
        }
        int _direction = -1;
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean[][] visited = new boolean[gameBoard.getWidth()][gameBoard.getHeight()];
        Pair<Integer, Integer>[][] prev = new Pair[gameBoard.getWidth()][gameBoard.getHeight()];
 
        queue.add(new Pair<>(bomber.getXTileBomber(), bomber.getYTileBomber()));
        visited[bomber.getXTileBomber()][bomber.getYTileBomber()] = true;

        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { -1, 0, 1, 0 };
        boolean found = false;

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.poll();
            int x = current.getKey();
            int y = current.getValue();

            if (x == e.getXTile() && y == e.getYTile()) {
                found = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (newX >= 0 && newX < gameBoard.getWidth() && newY >= 0 && newY < gameBoard.getHeight()
                        && !visited[newX][newY]) {
                    Entity a = gameBoard.getEntityAt(newX, newY);
                    if (!(a instanceof Wall)
                            && (!(a instanceof CoincidentEntity) || !(((CoincidentEntity) a).getTopEntity() instanceof Brick)
                                    && !(((CoincidentEntity) a).getTopEntity() instanceof Portal))) {
                        visited[newX][newY] = true;
                        prev[newX][newY] = new Pair<>(x, y);
                        queue.add(new Pair<>(newX, newY));
                    }
                }
            }
        }

        if (found) {
            Pair<Integer, Integer> current = new Pair<>(e.getXTile(), e.getYTile());
            while (prev[current.getKey()][current.getValue()] != null
                    && !(prev[current.getKey()][current.getValue()].getKey() == bomber.getXTileBomber()
                            && prev[current.getKey()][current.getValue()].getValue() == bomber.getYTileBomber())) {
                current = prev[current.getKey()][current.getValue()];
            }
            int directionX = current.getKey() - bomber.getXTileBomber();
            int directionY = current.getValue() - bomber.getYTileBomber();

            if (directionY == -1) {
                _direction = 0;
            } else if (directionX == 1) {
                _direction = 1;
            } else if (directionY == 1) {
                _direction = 2;
            } else if (directionX == -1) {
                _direction = 3;
            }
            return _direction;
        }
        return -1;
    }

    public void action() {
        if (targetSlain) {
            checkIfEnemyNearBy();
        } else {
            killTarget();
        }
    }

    public void killTarget() {
        if (!target.getAlive()) {
            targetSlain = true;
            return;
        }
        double a = bomber.getXTile() + 0.5;
        double b = bomber.getYTile() + 0.5;
        double _x = target.getXTile();
        double _y = target.getYTile();
        if ((a + 2.5 > _x && a - 2.5 < _x && bomber.getYTile() == _y) || (b + 2.5 > _y && b - 2.5 < _y && bomber.getXTile() == _x)) {
            autoPlaceBomb();
        } else {
            direction = findDirection(target);
            autoMove();
        }
    }

    public void autoPlaceBomb() {
        bomber.doPlaceBomb();
    }

    // public boolean nearBomber(Enemy e) {
    //     double a = bomber.getXTile() + 0.5;
    //     double b = bomber.getYTile() + 0.5;
    //     double _x = e.getXTile();
    //     double _y = e.getYTile();
    //     // Trong vòng 3 ô xung quanh bomber gần enemy
    //     boolean firstCondition = a + 3.5 > _x && a - 3.5 < _x && b + 3.5 > _y && b - 3.5 < _y;
    //     // Nếu không bị tường chắn thì không tính là gần bomber
    //     if (findDirection(e) != -1 && firstCondition == true) {
    //         return true;
    //     }
    //     return false;
    // }

    public double distance(Entity e) {
        return (double) Math.sqrt(Math.pow(bomber.getXTile() - e.getXTile(), 2) + Math.pow(bomber.getYTile() - e.getYTile(), 2));
    }

    public void checkIfEnemyNearBy() {
        for (uet.oop.bomberman.entities.character.Character character : gameBoard.getCharacters()) {
            if (character instanceof Enemy) {
                if (findDirection(character) != -1) {
                    target = (Enemy) character;
                    targetSlain = false;
                    return;
                }
            }
        }
    }

    // public Brick findBrick() {
    //     for (Entity e : gameBoard.getEntity()) {
    //             if (e instanceof CoincidentEntity) {
    //                 if (e.getTopEntity() instanceof Brick)
    //                 System.out.println("co");
    //                 if (findDirection(e) != -1) {   
    //                     return (Brick) e;
    //                 }
    //             } 
    //     }
    //     return null;
    // }

    // public void breakBrick() {
    //     Brick brick = findBrick();
    //     while (!brick.isRemoved()) {
    //         System.out.println("yeah");
    //         direction = findDirection(brick);
    //         autoMove();
    //     }
    // }

    public void Bot() {
        action();
    }
}
