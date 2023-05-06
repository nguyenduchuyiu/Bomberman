package uet.oop.bomberman.entities.character.enemy.AI;

public class LowAI extends AI {

	@Override
	public int calculateDirection() {
		int i;
		i = random.nextInt(4);
		return i;
	}

}
