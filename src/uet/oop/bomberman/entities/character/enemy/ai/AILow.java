package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
		int i;
		i = random.nextInt(4);
		return i;
	}

}
