package main;

import gameplay.Game;
import menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	private static final String  GAME_NAME         = "Bionic Quacker";
	private static final int     GAME_WIDTH        = 1280;
	private static final int     GAME_HEIGHT       = 720;
	private static final boolean MOUSE_GRABBED     = false;
	private static final int     TARGET_FPS        = 60;
	private static final boolean FULL_SCREEN       = false;

	private static final int     MENU_STATE_ID     = 0;
	private static final int     GAMEPLAY_STATE_ID = 1;

	public static void main(String[] args) throws SlickException {
		AppGameContainer appGameContainer = new AppGameContainer(new ScalableGame(new Main(), GAME_WIDTH, GAME_HEIGHT));
		appGameContainer.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, FULL_SCREEN);
		appGameContainer.setMouseGrabbed(MOUSE_GRABBED);
		appGameContainer.setTargetFrameRate(TARGET_FPS);
		appGameContainer.setTitle(GAME_NAME);
		appGameContainer.start();
	}

	public Main() {
		super(GAME_NAME);
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		addState(new Menu(MENU_STATE_ID));
		addState(new Game(GAMEPLAY_STATE_ID));
	}

	public static int getGameplayStateId() {
		return GAMEPLAY_STATE_ID;
	}

}
