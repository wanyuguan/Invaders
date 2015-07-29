package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import engine.Main;
import engine.Score;
import engine.ScreenType;

/**
 * Implements the high scores screen, it shows player records.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class HighScoreScreen extends Screen {

	/** List of past high scores. */
	private List<Score> highScores;

	/**
	 * Constructor, establishes the properties of the screen.
	 */
	public HighScoreScreen() {
		super();

		this.nextScreen = ScreenType.TitleScreen;

		try {
			this.highScores = Main.getFileManager().loadHighScores();
		} catch (NumberFormatException | IOException e) {
			logger.warning("Couldn't load high scores!");
		}
	}

	/**
	 * Gets the screen's type
	 * @return The screen's type
	 */
	public ScreenType getScreenType() { return ScreenType.HighScroreScreen; }

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final ScreenType run() {
		super.run();

		return this.nextScreen;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		draw();
		if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
				&& this.inputDelay.checkFinished())
			this.isRunning = false;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawHighScoreMenu(this);
		drawManager.drawHighScores(this, this.highScores);

		drawManager.completeDrawing(this);
	}
}
