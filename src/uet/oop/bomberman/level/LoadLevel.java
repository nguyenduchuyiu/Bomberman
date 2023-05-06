package uet.oop.bomberman.level;

import uet.oop.bomberman.GameBoard;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LoadLevel {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected GameBoard _gameBoard;

	public LoadLevel(GameBoard gameBoard, int level) {
		_gameBoard = gameBoard;
		loadLevel(level);
	}

	public abstract void loadLevel(int level);

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
