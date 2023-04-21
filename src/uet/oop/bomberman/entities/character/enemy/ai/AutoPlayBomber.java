package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AutoPlayBomber {
    Bomber bomber;
    public int time = 180;
    int x = 0, y = 0;
    Random random = new Random();
    int direction = 0;
    public GameBoard gameBoard;

    public AutoPlayBomber(Bomber bomber, GameBoard gameBoard) {
        this.bomber = bomber;
        this.gameBoard = gameBoard;
    }
    
    public void autoMove() {
        if (time > 0) {
            time--;
        } else {
            time = 60;
            direction = random.nextInt(4);
        }
        switch(direction) {
            case 1 :
                x = 1;
                y = 0;
                break;
            case 2 :
                x = 0;
                y = 1;
                break;
            case 3 :
                x = -1;
                y = 0;
                break;
            case 0 :
                x = 0;
                y = -1;
                break;
        }
        bomber.doMove(x, y);
    }

    public void autoPlaceBomb() {
        if (time == 0) {
            bomber.doPlaceBomb();
            time = 180;
        } 
        else {
            time--;
        }

    }

    public void checkEnemy() {
        for (Character character : gameBoard.getCharacters()) {

        }
    }

    public void Bot() {
       autoMove();
       autoPlaceBomb();
    }
}
