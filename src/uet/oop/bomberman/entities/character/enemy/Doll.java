package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.character.enemy.AI.LowMediumAI;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
	
	
	public Doll(int x, int y, GameBoard gameBoard) {
		super(x, y, gameBoard, Sprite.doll_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.doll_left1;
       
		_ai = new LowMediumAI(gameBoard, this);
		_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
				break;
		}
	}
}
