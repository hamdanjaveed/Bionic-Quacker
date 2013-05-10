package main;

import gameplay.Game;
import menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import score.Score;

import java.io.File;
import java.io.IOException;

public class Main extends StateBasedGame {

	private static final String  GAME_NAME     = "Bionic Quacker";
	private static final int     GAME_WIDTH    = 1280;
	private static final int     GAME_HEIGHT   = 720;
	private static final boolean MOUSE_GRABBED = false;
	private static final int     TARGET_FPS    = 60;
	private static final boolean FULL_SCREEN   = false;

	private static final int MENU_STATE_ID     = 0;
	private static final int GAMEPLAY_STATE_ID = 1;
	private static final int SCORE_STATE_ID = 2;

	public static Audio stomp;
	public static Audio theme;

	public static int housesStanding = 0;

	public static void main(String[] args) throws SlickException, IOException {
		buildDir();
		AppGameContainer appGameContainer = new AppGameContainer(new ScalableGame(new Main(), GAME_WIDTH, GAME_HEIGHT));
		appGameContainer.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, FULL_SCREEN);
		appGameContainer.setMouseGrabbed(MOUSE_GRABBED);
		appGameContainer.setTargetFrameRate(TARGET_FPS);
		appGameContainer.setTitle(GAME_NAME);
		appGameContainer.setShowFPS(false);
		appGameContainer.setVSync(true);
		appGameContainer.start();
	}

	private static void buildDir() throws IOException {
		File numberOfHousesStanding = new File("file/numberOfHousesStanding.txt");
		numberOfHousesStanding.getParentFile().mkdirs();
		numberOfHousesStanding.createNewFile();

		File netProfit = new File("file/netProfit.txt");
		netProfit.createNewFile();

		File winLoss = new File("file/winLoss.txt");
		winLoss.createNewFile();

		File profit = new File("file/profit.txt");
		profit.createNewFile();

		File plotPlaced = new File("file/plotPlaced.txt");
		plotPlaced.createNewFile();

		File plotMostHit = new File("file/plotMostHit.txt");
		plotMostHit.createNewFile();

		File plotMostHitWithHouse = new File("file/plotMostHitWithHouse.txt");
		plotMostHitWithHouse.createNewFile();
	}

	public Main() {
		super(GAME_NAME);
		try {
			stomp = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Stomp.wav"));
			theme = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Theme.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		addState(new Menu(MENU_STATE_ID));
		addState(new Game(GAMEPLAY_STATE_ID));
		addState(new Score(SCORE_STATE_ID));
	}

	public static int getGameplayStateId() {
		return GAMEPLAY_STATE_ID;
	}

	public static int getMenuStateId() {
		return MENU_STATE_ID;
	}

	public static int getScoreStateId() {
		return SCORE_STATE_ID;
	}

}
