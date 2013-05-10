package score;

import main.Main;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Score extends BasicGameState {


	private static int STATE_ID;

	private Image bg;
	private Image qn;
	private Image qy;
	private Rectangle r;
	private long time;

	private String h;
	private String w;
	private String p;

	public Score(int assignedID) {
		STATE_ID = assignedID;
	}

	public int getID() {
		return STATE_ID;
	}

	public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		time = 0;
		h = "Houses standing: " + Main.housesStanding;
		w = "You " + ((Main.housesStanding < 12) ? (Main.housesStanding < 9) ? "lost!" : "won+lost!" : "won!");
		p = "Net profit: " + ((Main.housesStanding < 12) ? (Main.housesStanding < 9) ? "-$1.00" : "$0.00" : "$1.00");
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		bg = new Image("score/BG.png");
		qn = new Image("score/QuitN.png");
		qy = new Image("score/QuitY.png");
		r = new Rectangle(200, 400, 300, 100);
	}

	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		bg.draw();
		if (time > 1000)
			graphics.drawString(h, 200, 150);
		if (time > 2000)
			graphics.drawString(w, 200, 200);
		if (time > 3000)
			graphics.drawString(p, 200, 250);
		if (time > 4000)
			(r.contains(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY()) ? qy : qn).draw(200, 400);
	}

	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		time += i;
		if (gameContainer.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && time > 4000) {
			if (r.contains(gameContainer.getInput().getMouseX(), gameContainer.getInput().getMouseY())) {
				stateBasedGame.enterState(Main.getMenuStateId());
			}
		}
	}
}
