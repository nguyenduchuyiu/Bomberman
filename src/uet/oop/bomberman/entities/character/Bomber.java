package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.AI.AIPlayBomber;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.ChangeUnits;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    public boolean autoPlay = false;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong
     * mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    AIPlayBomber ai;

    public Bomber(int x, int y, GameBoard gameBoard) {
        super(x, y, gameBoard);
        _bombs = _gameBoard.getBombs();
        _input = _gameBoard.getInput();
        _sprite = Sprite.player_right;
        ai = new AIPlayBomber(this, gameBoard);
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500)
            _timeBetweenPutBombs = 0;
        else
            _timeBetweenPutBombs--;

        animate();

        if (autoPlay) {
            ai.Bot();
        } 
        else {
            Player();
        }
        detectAutoPlay();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_gameBoard, this);
        Screen.setOffset(xScroll, 0);
    }


    public void Player() {
        calculateMove();
        detectPlaceBomb();
    }

    public void detectPlaceBomb() {
        if (_input.space) {
            doPlaceBomb();
        }
    }

    public void doPlaceBomb() {
        if (_timeBetweenPutBombs < 0 && Game.getBombRate() > 0) {
            int xx = ChangeUnits.pixelToTile(_x + 6);
            int yy = ChangeUnits.pixelToTile(_y - 9);
            placeBomb(xx, yy);
            Game.addBombRate(-1);
            _timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, _gameBoard);
        _gameBoard.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive)
            return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0)
            --_timeAfter;
        else {
            _gameBoard.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        int x = 0, y = 0;
        if (_input.up)
            y--;// len
        if (_input.down)
            y++;// xuong
        if (_input.left)
            x--;// trai
        if (_input.right)
            x++;// phai
        doMove(x, y);
    }

    public void doMove(int xx, int yy) {
        if (xx != 0 || yy != 0) {
            move(xx * Game.getBomberSpeed(), yy * Game.getBomberSpeed());
            _moving = true;
        } else {
            _moving = false;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) {
            double xt = ((_x + x) + c % 2 * 11) / 16; // 16 = Game.tiles_size
            double yt = ((_y + y) + c / 2 * 12 - 13) / 16;

            Entity a = _gameBoard.getEntity(xt, yt, this);
            if (a.collide(this))
                return false;
        }
        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (xa > 0)
            _direction = 1;
        if (xa < 0)
            _direction = 3;
        if (ya > 0)
            _direction = 2;
        if (ya < 0)
            _direction = 0;

        if (canMove(0, ya))
            _y += ya;
        if (canMove(xa, 0))
            _x += xa;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flame) {
            kill();
        }

        if (e instanceof Enemy) {
            kill();
        }

        return true;
    }

    public void detectAutoPlay() {
        if (_input.auto) {
            if (autoPlay) {
                autoPlay = false;
            } else {
                autoPlay = true;
            }
        }
    }

    public int getXTileBomber() {
		return ChangeUnits.pixelToTile(getX());
	}
	
	public int getYTileBomber() {
		return ChangeUnits.pixelToTile(getY() - 16);
	}

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
