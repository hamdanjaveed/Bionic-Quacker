package gameplay;

import main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public class Game extends BasicGameState {

	private static int STATE_ID;

	private Image ground;
	private Image gridImage;
	private Image bionicQuacker;
	private Image house;
	private Image ghostHouse;
	private Image continueImage;
	private Image continueImageGrey;
	private Image destroyed;

	private int bqx;
	private int bqy;

	private Rectangle continueRect;

	private boolean canContinue;
	private boolean simulating;

	private int[][] grid;
	private int     housesPlaced;

	private int mouseTileX;
	private int mouseTileY;

	private long time;

	private boolean leftCheck  = false;
	private boolean rightCheck = false;
	private boolean upCheck    = false;
	private boolean downCheck = false;

	private int placeTHIS = 20;

	private long houseTime = 0;

	private int stompX, stompY;

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
		destroyed = new Image("gameplay/Destroyed.png");
	}

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		grid = new int[32][18];
		housesPlaced = 0;
		canContinue = false;
		simulating = false;
		time = -1;
		bqx = -200;
		bqy = 600;
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		if (time != -1) {
			if (time > 12800 && time < 15500) {
				graphics.rotate((float) gameContainer.getWidth() / 2 + (float) Math.random() * 1000 - 500, (float) gameContainer.getHeight() / 2 + (float) Math.random() * 1000 - 500, (float) Math.random() * 0.5f);
			}
		}

		ground.draw(0, 0, gameContainer.getWidth(), gameContainer.getHeight());

		if (!simulating)
			gridImage.draw(0, 0);

		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] != 0) {
					house.draw(x * 40, y * 40);
				}
				if (grid[x][y] == 2) {
					destroyed.draw(x * 40, y * 40);
				}
			}
		}

		graphics.drawString("Houses placed: " + housesPlaced + "/" + placeTHIS, 10, 10);

		if (housesPlaced < placeTHIS) {
			ghostHouse.draw(mouseTileX * 40, mouseTileY * 40);
		}

		if (canContinue) {
			continueImage.draw(1050, 10);
		} else {
			continueImageGrey.draw(1050, 10);
		}

		if (simulating) {
			bionicQuacker.draw(bqx, bqy);
		}
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		Input input = gameContainer.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}

		int mx = input.getMouseX();
		int my = input.getMouseY();
		mouseTileX = mx / 40;
		mouseTileY = my / 40;

		canContinue = housesPlaced == placeTHIS;

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !simulating) {
			if (grid[mouseTileX][mouseTileY] == 0 && !canContinue) {
				grid[mouseTileX][mouseTileY] = 1;
				housesPlaced++;
			} else if (grid[mouseTileX][mouseTileY] == 1) {
				grid[mouseTileX][mouseTileY] = 0;
				housesPlaced--;
			}

			if (canContinue && continueRect.contains(mx, my)) {
				simulating = true;
				stompX = (new Random()).nextInt(16);
				stompY = (new Random()).nextInt(2);
				for (int x = stompX; x < stompX + 16; x++) {
					for (int y = stompY; y < stompY + 16; y++) {
						if (grid[x][y] == 1) {
							grid[x][y] = 3;
						}
					}
				}
			}
		}

		if (simulating) {
			if (time == -1) {
				time = 0;
				Main.stomp.playAsMusic(1.0f, 1.0f, false);
			}
			if (time < 4000) {
				if (!leftCheck) {
					bqx = -200;
					leftCheck = true;
				}
				bqx += delta * 0.4f;
			} else if (time < 8000) {
				if (!rightCheck) {
					bqx = 1280;
					rightCheck = true;
				}
				bqx -= delta * 0.4f;
			} else if (time < 9000 && time > 8300) {
				if (!upCheck) {
					bqx = gameContainer.getWidth() / 2 - bionicQuacker.getWidth() / 2;
					bqy = gameContainer.getHeight();
					upCheck = true;
				}
				bqy -= delta * 4.0f;
			} else if (time > 12500 && time < 15000 && bqy < (stompY + 8) * 40 - bionicQuacker.getHeight()) {
				if (!downCheck) {
					downCheck = true;
					bqy = -bionicQuacker.getHeight();
					bqx = (stompX + 8) * 40 - bionicQuacker.getWidth() / 2 + 10;
				}
				bqy += delta * 4.0f;
			} else if (time > 16800) {
				if (bqx > gameContainer.getWidth() / 2)
					bqx += delta * 0.2f;
				else
					bqx -= delta * 0.2f;
			}
			if (time > 20600) {
				houseTime += delta;
				if (houseTime > 300) {
					here:
					for (int y = 0; y < grid[0].length; y++) {
						for (int x = 0; x < grid.length; x++) {
							if (grid[x][y] == 3) {
								grid[x][y] = 2;
								break here;
							}
						}
					}
					houseTime = 0;
				}
			}

			if (time > 26000) {
				stateBasedGame.enterState(Main.getScoreStateId());
			}
			time += delta;
		}

		SoundStore.get().poll(delta);
	}

}
