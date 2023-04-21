package uet.oop.bomberman.level;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width = 20, _height = 20; // default values just for testing
	protected int _level;
	protected GameBoard _gameBoard;

	public LevelLoader(GameBoard gameBoard, int level) throws LoadLevelException {
		_gameBoard = gameBoard;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

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
