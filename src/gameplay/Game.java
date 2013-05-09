package gameplay;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {

	private static int STATE_ID;

	private Image ground;
	private Image gridImage;
	private Image bionicQuacker;
	private Image house;
	private Image ghostHouse;
	private Image continueImage;
	private Image continueImageGrey;

	private Rectangle continueRect;

	private boolean canContinue;

	private int[][] grid;
	private int housesPlaced;

	private int mouseTileX;
	private int mouseTileY;

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
		ghostHouse = new Image("gameplay/House.png");
		ghostHouse.setAlpha(0.3f);
		continueImage = new Image("gameplay/Continue.png");
		continueImageGrey = new Image("gameplay/Continue.png");
		continueImageGrey.setAlpha(0.3f);
		continueRect = new Rectangle(1050, 10, 300, 100);
	}

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
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

		graphics.drawString("Houses placed: " + housesPlaced + "/20", 10, 10);

		if (housesPlaced < 20) {
			ghostHouse.draw(mouseTileX * 40, mouseTileY * 40);
		}

		if (canContinue) {
			continueImage.draw(1050, 10);
		} else {
			continueImageGrey.draw(1050, 10);
		}
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		Input input = gameContainer.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}

		int mx = input.getMouseX();
		int my = input.getMouseY();
		mouseTileX = mx / 40;
		mouseTileY = my / 40;

		canContinue = housesPlaced == 20;

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (grid[mouseTileX][mouseTileY] == 0 && housesPlaced < 20) {
				grid[mouseTileX][mouseTileY] = 1;
				housesPlaced++;
			} else if (grid[mouseTileX][mouseTileY] == 1) {
				grid[mouseTileX][mouseTileY] = 0;
				housesPlaced--;
			}

			if (canContinue && continueRect.contains(mx, my)) {
				// stomp
			}
		}
	}

}
