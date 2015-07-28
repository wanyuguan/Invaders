package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Main;
import engine.ScreenType;

/**
 * Implements the title screen.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class TitleScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;
	
	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public TitleScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		// Defaults to play.
		this.nextScreen = ScreenType.GameScreen;
		this.selectionCooldown = Main.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
	}

	/**
	 * Gets the screen's type
	 * @return The screen's type
	 */
	public ScreenType getScreenType() { return ScreenType.TitleScreen; }

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
		if (this.selectionCooldown.checkFinished()
				&& this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_UP)
					|| inputManager.isKeyDown(KeyEvent.VK_W)) {
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
				this.isRunning = false;
		}
	}

	/**
	 * Shifts the focus to the next menu item.
	 */
	private void nextMenuItem() {
		if (this.nextScreen == ScreenType.HighScroreScreen)
			this.nextScreen = ScreenType.EndGame;
		else if (this.nextScreen == ScreenType.EndGame)
			this.nextScreen = ScreenType.GameScreen;
		else
			this.nextScreen = ScreenType.HighScroreScreen;
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private void previousMenuItem() {
		if (this.nextScreen == ScreenType.EndGame)
			this.nextScreen = ScreenType.HighScroreScreen;
		else if (this.nextScreen == ScreenType.GameScreen)
			this.nextScreen = ScreenType.EndGame;
		else
			this.nextScreen = ScreenType.GameScreen;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawTitle(this);
		drawManager.drawMenu(this, this.nextScreen);

		drawManager.completeDrawing(this);
	}
}
