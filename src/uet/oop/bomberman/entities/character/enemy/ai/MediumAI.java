package uet.oop.bomberman.entities.character.enemy.AI;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.entities.CoincidentEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;

public class MediumAI extends AI {

    Bomber bomber;
    Enemy enemy;
    GameBoard board;
    List<Bomb> bombs;

	public MediumAI(Bomber bomber, Enemy enemy, GameBoard board) {
		this.bomber = bomber;
		this.enemy = enemy;
		this.board = board;
		bombs = board.getBombs();

	}

	@Override
	public int calculateDirection() {

		if (bomber == null)
			return random.nextInt(4);

		// Cập nhật danh sách bombs
		bombs = board.getBombs();

		// Kiểm tra xem có bom nào trên bản đồ không
		boolean hasBombs = !bombs.isEmpty();

		if (hasBombs) {
			return avoidBombs();

		} else {
			return findDirection();
		}
	}

	/**
	 * Phương thức avoidBombs() để tránh bom.
	 *
	 * @return int Mã hướng di chuyển (0 - lên, 1 - phải, 2 - xuống, 3 - trái).
	 */
	private int avoidBombs() {
		int[] dx = { 0, 1, 0, -1 };
		int[] dy = { -1, 0, 1, 0 };

		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
		boolean[][] visited = new boolean[board.getWidth()][board.getHeight()];
		int[][] distance = new int[board.getWidth()][board.getHeight()];

		queue.add(new Pair<>(enemy.getXTile(), enemy.getYTile()));
		visited[enemy.getXTile()][enemy.getYTile()] = true;
		distance[enemy.getXTile()][enemy.getYTile()] = 0;

		while (!queue.isEmpty()) {
			Pair<Integer, Integer> current = queue.poll();
			int x = current.getKey();
			int y = current.getValue();

			for (int i = 0; i < 4; i++) {
				int newX = x + dx[i];
				int newY = y + dy[i];

				if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()
						&& !visited[newX][newY]) {
					Entity a = board.getEntityAt(newX, newY);
					if (!(a instanceof Wall) && (!(a instanceof CoincidentEntity)
							|| !(((CoincidentEntity) a).getTopEntity() instanceof Brick))) {
						visited[newX][newY] = true;
						distance[newX][newY] = distance[x][y] + 1;
						queue.add(new Pair<>(newX, newY));

						boolean safe = true;
						for (Bomb bomb : bombs) {
							if ((newX == bomb.getX() && Math.abs(newY - bomb.getY()) <= Game.getBombRadius())
									|| (newY == bomb.getY() && Math.abs(newX - bomb.getX()) <= Game.getBombRadius())) {
								safe = false;
								break;
							}
						}

						if (!safe) {
							int directionX = newX - enemy.getXTile();
							int directionY = newY - enemy.getYTile();

							if (directionY == -1) {
								return 2;
							} else if (directionX == 1) {
								return 3;
							} else if (directionY == 1) {
								return 0;
							} else if (directionX == -1) {
								return 1;
							}
						}
					}
				}
			}
		}

		return -1;
	}

	/**
	 * Phương thức findDirection() để xác định hướng di chuyển theo Bomber.
	 *
	 * @return int Mã hướng di chuyển (0 - lên, 1 - phải, 2 - xuống, 3 - trái).
	 */
	public int findDirection() {
		int _direction = -1;
		Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
		boolean[][] visited = new boolean[board.getWidth()][board.getHeight()];
		Pair<Integer, Integer>[][] prev = new Pair[board.getWidth()][board.getHeight()];

		queue.add(new Pair<>(enemy.getXTile(), enemy.getYTile()));
		visited[enemy.getXTile()][enemy.getYTile()] = true;

		int[] dx = { 0, 1, 0, -1 };
		int[] dy = { -1, 0, 1, 0 };
		boolean found = false;

		while (!queue.isEmpty()) {
			Pair<Integer, Integer> current = queue.poll();
			int x = current.getKey();
			int y = current.getValue();

			if (x == bomber.getXTile() && y == bomber.getYTile()) {
				found = true;
				break;
			}

			for (int i = 0; i < 4; i++) {
				int newX = x + dx[i];
				int newY = y + dy[i];

				if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()
						&& !visited[newX][newY]) {
					Entity a = board.getEntityAt(newX, newY);
					if (!(a instanceof Wall) && (!(a instanceof CoincidentEntity)
							|| !(((CoincidentEntity) a).getTopEntity() instanceof Brick)
									&& !(((CoincidentEntity) a).getTopEntity() instanceof Portal))) {
						visited[newX][newY] = true;
						prev[newX][newY] = new Pair<>(x, y);
						queue.add(new Pair<>(newX, newY));
					}
				}
			}
		}

		if (found) {
			// Truy vết ngược từ vị trí _bomber đến vị trí _e để tìm hướng di chuyển đầu
			// tiên
			Pair<Integer, Integer> current = new Pair<>(bomber.getXTile(), bomber.getYTile());
			while (prev[current.getKey()][current.getValue()] != null
					&& !(prev[current.getKey()][current.getValue()].getKey() == enemy.getXTile()
							&& prev[current.getKey()][current.getValue()].getValue() == enemy.getYTile())) {
				current = prev[current.getKey()][current.getValue()];
			}
			int directionX = current.getKey() - enemy.getXTile();
			int directionY = current.getValue() - enemy.getYTile();

			if (directionY == -1) {
				_direction = 0;
			} else if (directionX == 1) {
				_direction = 1;
			} else if (directionY == 1) {
				_direction = 2;
			} else if (directionX == -1) {
				_direction = 3;
			}
		} else {
			_direction = random.nextInt(4);
		}

		return _direction;
	}
}
