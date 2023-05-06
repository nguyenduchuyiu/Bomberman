package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.character.enemy.AI.MediumAI;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {
	
	
	public Kondoria(int x, int y, GameBoard gameBoard) {
		super(x, y, gameBoard, Sprite.kondoria_dead, Game.getBomberSpeed() / 2, 100);
		
		_sprite = Sprite.kondoria_left1;
		
		_ai = new MediumAI(gameBoard.getBomber(), this, gameBoard);
		_direction = _ai.calculateDirection();
	}

	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				break;
			case 2:
			case 3:
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				break;
		}
	}
}
