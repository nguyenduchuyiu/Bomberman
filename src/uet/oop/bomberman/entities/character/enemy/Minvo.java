package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.character.enemy.AI.LowMediumAI;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
	
	
	public Minvo(int x, int y, GameBoard gameBoard) {
		super(x, y, gameBoard, Sprite.minvo_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.minvo_left1;
       
		_ai = new LowMediumAI(gameBoard, this);
		_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, _animate, 60);
				break;
		}
	}
}
