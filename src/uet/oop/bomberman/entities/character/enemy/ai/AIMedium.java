package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;

import java.util.ArrayList;
import java.util.List;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	GameBoard _gameBoard;
	List<PathFinding> aStarList = new ArrayList<>();

	public AIMedium (Bomber bomber, Enemy enemy, GameBoard gameBoard) {
		_bomber = bomber;
		_e = enemy;
		_gameBoard = gameBoard;
		aStarList.clear();
	}


	@Override
	public int calculateDirection() {


		if (_bomber == null) return random.nextInt(4);

		int v = returnDirection();

		return v;
	}

/*
 * 0 - up
 * 1 - right
 * 2 - down
 * 3 - left
 */
	public int returnDirection() {

		int _direction = -1;
		double minF = Double.MAX_VALUE;
		for (int i = 0; i < 4; i++) {
			int xx = _e.getXTile(), yy = _e.getYTile();

			if (i == 0) {
				yy --;
			}

			if (i == 1) {
				xx ++;
			}

			if (i == 2) {
				yy ++;
			}

			if (i == 3) {
				xx --;
			}
			Entity a = _gameBoard.getEntityAt(xx + 1, yy);

			if(a instanceof LayeredEntity) {
				LayeredEntity tmp = (LayeredEntity) a;
				if (!(tmp.getTopEntity() instanceof Brick) || !(tmp.getTopEntity() instanceof Portal)) {
					double G = 1;
					double H = Math.pow(xx - _bomber.getXTile(), 2) + Math.pow(yy - _bomber.getYTile(), 2);
					aStarList.add(new PathFinding(G, H, G + H, i));
				}
			}

			if (!(a instanceof Wall)){
				double G = 1;
				double H = Math.pow(xx - _bomber.getXTile(), 2) + Math.pow(yy - _bomber.getYTile(), 2);
				aStarList.add(new PathFinding(G, H, G + H, i));
			}
		}

		if (aStarList != null || aStarList.size() != 0) {
			for (int i = 1; i < aStarList.size(); i++) {
				if (aStarList.get(i).getF() < minF) {
					minF = aStarList.get(i).getF();
					_direction = aStarList.get(i).getDirection();
				}
			}
		}

		if (_direction == - 1) _direction = random.nextInt(4);

		return _direction;
	}
}