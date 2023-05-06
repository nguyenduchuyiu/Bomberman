package uet.oop.bomberman.entities.character.enemy.AI;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.Minvo;

import java.util.ArrayList;
import java.util.List;

public class LowMediumAI extends AI{
    Bomber _bomber;
    Enemy _e;
    List<Bomb> bombs = new ArrayList<Bomb>();

    public LowMediumAI(GameBoard gameBoard, Enemy enemy) {
        _bomber = gameBoard.getBomber();
        _e = enemy;
        bombs = gameBoard.getBombs();
    }

    @Override
    public int calculateDirection() {
        if (_e instanceof Minvo) {
            if ((_bomber.getXTile() == _e.getXTile() && Math.abs(_bomber.getYTile() - _e.getYTile()) <= 4) || (_bomber.getYTile() == _e.getYTile() && Math.abs(_bomber.getXTile() - _e.getXTile()) <= 4)) {
                _e.setSpeed(0.6);
            } else {
                _e.setSpeed(0);
            }
        }
        return findDirection();
    }

    public int findDirection() {
        for (Bomb bomb : bombs) {
            if (bomb.getXTile() == _e.getXTile()) {
                if (_e.getYTile() - 1 == bomb.getYTile()) return 2;
                if (_e.getYTile() + 1 == bomb.getYTile()) return 0;
            }
            if (bomb.getYTile() == _e.getYTile()) {
                if (_e.getXTile() - 1 == bomb.getXTile()) return 1;
                if (_e.getXTile() + 1 == bomb.getXTile()) return 3;
            }
        }

        int way = random.nextInt(10);
        if (way % 2 == 0) {
            int _dir = dicrectionRow();
            if (_dir != - 1) {
                    return _dir;
            }
            return dicrectionCol();
        }else {
            int _dir = dicrectionCol();
            if (_dir != - 1) {
                    return _dir;
            }
            return dicrectionRow();
        }
    }

    public int dicrectionRow() {
        if (_bomber.getXTile() > _e.getXTile()) return 1;
        if (_bomber.getXTile() < _e.getXTile()) return 3;
        return -1;
    }
    public int dicrectionCol(){
        if (_bomber.getYTile() < _e.getYTile()) return 0;
        if (_bomber.getYTile() > _e.getYTile()) return 2;
        return -1;
    }
}
