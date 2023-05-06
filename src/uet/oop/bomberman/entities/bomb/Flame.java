package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected GameBoard _gameBoard;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	public Flame(int x, int y, int direction, int radius, GameBoard gameBoard) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_gameBoard = gameBoard;
		createFlameSegments();
	}

	private void createFlameSegments() {

		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		boolean last;

		int x = (int)_x;
		int y = (int)_y;
		for (int i =0; i < _flameSegments.length;i++)
		{
			if (i==_flameSegments.length-1) last = false;
			else last = true;

			switch (_direction)
			{
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}

			_flameSegments[i]= new FlameSegment(x,y,_direction,last);
		}
	}

	private int calculatePermitedDistance() {
		int radius = 0;
		int x = (int)_x;
		int y = (int)_y;

		while (radius< _radius)
		{
			if (_direction==0) y--;
			if (_direction==1) x++;
			if (_direction==2) y++;
			if (_direction==3) x--;

			Entity a = _gameBoard.getEntity(x,y,null);

			if (a instanceof Character) radius ++;

			if (a.collide(this) == true) break;

			radius ++;
		}

		return radius;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
