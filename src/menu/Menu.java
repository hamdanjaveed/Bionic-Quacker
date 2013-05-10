package menu;

import main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	private static int STATE_ID;

	private Image mainMenuBackgroundNotSelected;
	private Image mainMenuBackgroundSelected;
	private Rectangle mainMenuSelectRectangle;
	private boolean mainMenuSelected;

	public Menu(int assignedID) {
		STATE_ID = assignedID;
	}

	public int getID() {
		return STATE_ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		mainMenuBackgroundNotSelected = new Image("menu/Main Menu Not Selected.png");
		mainMenuBackgroundSelected = new Image("menu/Main Menu Selected.png");
		mainMenuSelectRectangle = new Rectangle(500, 360, 290, 170);
		mainMenuSelected = false;
	}

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		Main.theme.playAsMusic(1.0f, 1.0f, true);
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		if (mainMenuSelected) {
			mainMenuBackgroundSelected.draw();
		} else {
			mainMenuBackgroundNotSelected.draw();
		}
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		Input input = gameContainer.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gameContainer.exit();
		}

		int mx = input.getMouseX();
		int my = input.getMouseY();
		mainMenuSelected = mainMenuSelectRectangle.contains(mx, my);

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && mainMenuSelected) {
			stateBasedGame.enterState(Main.getGameplayStateId());
		}
	}

}
