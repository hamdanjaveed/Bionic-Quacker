package gameplay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {

	private static int STATE_ID;

	public Game(int assignedID) {
		STATE_ID = assignedID;
	}

	public int getID() {
		return STATE_ID;
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

	}

}
