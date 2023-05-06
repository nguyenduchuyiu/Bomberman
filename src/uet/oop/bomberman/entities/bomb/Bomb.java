package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.ChangeUnits;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected GameBoard _gameBoard;
	protected Flame[] _flames;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	
	public Bomb(int x, int y, GameBoard gameBoard) {
		_x = x;
		_y = y;
		_gameBoard = gameBoard;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explode();
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].update();
		}
	}

    /**
     * Xử lý Bomb nổ
     */
	protected void explode() {

		_allowedToPassThru = true;
		_exploded = true;
		Character a = _gameBoard.getCharacterAtExcluding((int)_x,(int)_y,null);
		if (a!= null)
		{
			a.kill();
		}
		_flames = new Flame[4];
		for (int i = 0; i < _flames.length; i++)
			_flames[i] = new Flame((int)_x,(int)_y,i, Game.getBombRadius(),_gameBoard);
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Bomber)
		{
			double x = e.getX() - ChangeUnits.tileToPixel(getX());
			double y = e.getY() - ChangeUnits.tileToPixel(getY());
			// kiem tra cac gia tri xem nguoi choi da ra ngoai bom chua thu cac gia tri
			if (!(x>=-10)&& x <16 && y >=1 && y <=28)
			{
				_allowedToPassThru = false;
				return true;
			}
			return false;
		}

		if (e instanceof Bomb)
		{
			_timeToExplode = 0;
			return false;
		}
        return true;
	}
}
