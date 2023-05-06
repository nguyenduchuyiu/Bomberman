package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.graphics.Screen;

/**
 * Bao gồm Bomber và Enemy
 */
public abstract class Character extends AnimatedEntitiy {
	
	protected GameBoard _gameBoard;
	public int _direction = -1;
	protected boolean _alive = true;
	public boolean _moving = false;
	public int _timeAfter = 40;
	
	public Character(int x, int y, GameBoard gameBoard) {
		_x = x;
		_y = y;
		_gameBoard = gameBoard;
	}
	
	@Override
	public abstract void update();
	
	@Override
	public abstract void render(Screen screen);

	/**
	 * Tính toán hướng đi
	 */
	protected abstract void calculateMove();
	
	protected abstract void move(double xa, double ya);

	/**
	 * Được gọi khi đối tượng bị tiêu diệt
	 */
	public abstract void kill();

	/**
	 * Xử lý hiệu ứng bị tiêu diệt
	 */
	protected abstract void afterKill();

	/**
	 * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
	 * @param x
	 * @param y
	 * @return
	 */
	protected abstract boolean canMove(double x, double y);

	protected double getXNotification() {
		return (_x * Game.SCALE) + (_sprite.SIZE / 2 * Game.SCALE);
	}
	
	protected double getYNotification() {
		return (_y* Game.SCALE) - (_sprite.SIZE / 2 * Game.SCALE);
	}

	public int get_direction() {
		return _direction;
	}

	public void set_direction(int _direction) {
		this._direction = _direction;
	}

	public boolean getAlive() {
		return this._alive;
	}
}
