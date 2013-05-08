package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

	private static final String GAME_NAME;

	static {
		GAME_NAME = "Bionic Quacker";
	}

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		super(GAME_NAME);
	}

	public void initStatesList(GameContainer gameContainer) throws SlickException {
		// TODO
	}
}
