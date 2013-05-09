package gameplay;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {

	private static int STATE_ID;

	private Image ground;
	private Image gridImage;
	private Image bionicQuacker;
	private Image house;

	private int[][] grid;
	private int housesPlaced;

	public Game(int assignedID) {
		STATE_ID = assignedID;
	}

	public int getID() {
		return STATE_ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		ground = new Image("gameplay/Ground.jpg");
		gridImage = new Image("gameplay/Grid.png");
		bionicQuacker = new Image("gameplay/Bionic Quacker.png");
		house = new Image("gameplay/House.png");

		grid = new int[32][18];
		housesPlaced = 0;
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		ground.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());
		gridImage.draw(0, 0);

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] == 1) {
					house.draw(x * 40, y * 40);
				}
			}
		}
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		Input input = gameContainer.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			int mx = input.getMouseX();
			int my = input.getMouseY();
			int tx = mx / 40;
			int ty = my / 40;
			if (grid[tx][ty] == 0) {
				grid[tx][ty] = 1;
				housesPlaced++;
			}
		}
	}

}
