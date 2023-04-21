package uet.oop.bomberman.level;

import uet.oop.bomberman.GameBoard;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileReader;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;

	public FileLevelLoader(GameBoard gameBoard, int level) throws LoadLevelException {
		super(gameBoard, level);
	}

	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try {
			FileReader file = new FileReader("res/levels/Level" + Integer.toString(level) + ".txt");
			Scanner sc = new Scanner(file);
			_level = sc.nextInt();
			_height = sc.nextInt();
			_width = sc.nextInt();
			_map = new char[_height][_width];
			sc.nextLine();

			for (int i = 0; i < _height; i++) {
				String line = sc.nextLine();
				for (int j = 0; j < _width; j++)
					_map[i][j] = line.charAt(j);
			}
			sc.close();
			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _gameBoard.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				int pos = x + y * getWidth();
				char c = _map[y][x];
				switch (c) { // them wall
					case '#':
						Sprite sprite = Sprite.wall;
						_gameBoard.addEntity(pos, new Wall(x, y, sprite));
						break;
					// add Brick (gach)
					case '*':
						_gameBoard.addEntity(pos, new LayeredEntity(x, y,
								new Grass(x, y, Sprite.grass),
								new Brick(x, y, Sprite.brick)));
						break;
					// add Bomber
					case 'p':
						_gameBoard.addCharacter(new Bomber(Coordinates.tileToPixel(x),
								Coordinates.tileToPixel(y) + Game.TILES_SIZE, _gameBoard));
						Screen.setOffset(0, 0);
						_gameBoard.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
					// add enemy 1
					case '1':
						_gameBoard.addCharacter(new Balloon(Coordinates.tileToPixel(x),
								Coordinates.tileToPixel(y) + Game.TILES_SIZE, _gameBoard));
						_gameBoard.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
					// add enemy 2
					case '2':
						_gameBoard.addCharacter(new Oneal(Coordinates.tileToPixel(x),
								Coordinates.tileToPixel(y) + Game.TILES_SIZE, _gameBoard));
						_gameBoard.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
					//add enemy 3
					case '3':
						_gameBoard.addCharacter(new Kondoria(Coordinates.tileToPixel(x),
								Coordinates.tileToPixel(y) + Game.TILES_SIZE, _gameBoard));
						_gameBoard.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
					// add portal: cong ket thuc game
					case 'x':
						_gameBoard.addEntity(pos,
								new LayeredEntity(x, y,
										new Grass(x, y, Sprite.grass),
										new Portal(x, y, Sprite.portal, _gameBoard),
										new Brick(x, y, Sprite.brick)));
						break;
					// add Bomb Item: vat pham tang so luong bom
					case 'b':
						_gameBoard.addEntity(pos,
								new LayeredEntity(x, y,
										new Grass(x, y, Sprite.grass),
										new BombItem(x, y, Sprite.powerup_bombs),
										new Brick(x, y, Sprite.brick)));
						break;
					// add Flame Item: vat pham tang suc cong pha
					case 'f':
						_gameBoard.addEntity(pos,
								new LayeredEntity(x, y,
										new Grass(x, y, Sprite.grass),
										new FlameItem(x, y, Sprite.powerup_flames),
										new Brick(x, y, Sprite.brick)));
						break;
					// add Speed Item: vat pham tang toc do
					case 's':
						_gameBoard.addEntity(pos,
								new LayeredEntity(x, y,
										new Grass(x, y, Sprite.grass),
										new SpeedItem(x, y, Sprite.powerup_speed),
										new Brick(x, y, Sprite.brick)));
						break;
					// con lai la grass
					default:
						_gameBoard.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
				}
			}
		}
	}

}
