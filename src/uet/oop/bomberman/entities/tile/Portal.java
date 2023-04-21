package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
	private GameBoard _gameBoard;

	public Portal(int x, int y, Sprite sprite, GameBoard gameBoard) {
		super(x, y, sprite);
		_gameBoard = gameBoard;
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber) {
			if (_gameBoard.noEnemyRemaining() && e.getXTile() == getX() && e.getYTile() == getY()) {
				_gameBoard.levelUp();
				return true;
			} 
			else {
				return false;
			}
		}
		return true;
	}

}
