package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.AI.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.ChangeUnits;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, GameBoard gameBoard, Sprite dead, double speed, int points) {
		super(x, y, gameBoard);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	public void setSpeed(double speed) {
		this._speed = speed;
	}

	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		int x= 0, y = 0;
		if (_steps<=0)
		{
			_direction  = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}


		if (_direction==0) y--;
		if (_direction==1) x++;
		if (_direction==2) y++;
		if (_direction==3) x--;
		if (canMove(x,y))
		{
			_steps -= 1 +rest;
			move(x* _speed, y *_speed);
			_moving = true;
		} else {
			_steps = 0;
			_moving = false;
		}


	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		double x1 = _x, y1 = _y ;
		// y = _y -16

		if(_direction == 0) { x1=x1 + 8; y1-- ; } // 8 = _sprite.getSize()/2
		if(_direction == 1) { x1 ++; y1 = y1 -8;}
		if(_direction == 2) { x1= x1+8; y1= y1-15;}
		if(_direction == 3) { x1= x1 +15; y1 = y1-8;}

		int xx = ChangeUnits.pixelToTile(x1) +(int)x;
		int yy = ChangeUnits.pixelToTile(y1) +(int)y;

		Entity a = _gameBoard.getEntity(xx, yy, this);

		return !a.collide(this);
	}

	@Override
	public boolean collide(Entity e) {
		if (e instanceof Flame)
		{
			kill();
			return true;
		}
		if (e instanceof Bomber)
		{
			((Bomber) e).kill();
			return true;
		}
		return false;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		Game.playSE(6);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
