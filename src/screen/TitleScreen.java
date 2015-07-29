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
	 */
	public TitleScreen() {
		super();

		// Defaults to play.
		this.nextScreenTpe = ScreenType.GameScreen;
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

		return this.nextScreenTpe;
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
				this.nextScreenTpe = previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
					|| inputManager.isKeyDown(KeyEvent.VK_S)) {
				this.nextScreenTpe = nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isSpaceKeyDown())
				this.isRunning = false;
		}
	}

	/**
	 * Shifts the focus to the next menu item.
	 */
	private ScreenType nextMenuItem() {
		if (this.nextScreenTpe == ScreenType.HighScroreScreen)
			return ScreenType.EndGame;
		else if (this.nextScreenTpe == ScreenType.EndGame)
			return ScreenType.GameScreen;
		else
			return ScreenType.HighScroreScreen;
	}

	/**
	 * Shifts the focus to the previous menu item.
	 */
	private ScreenType previousMenuItem() {
		if (this.nextScreenTpe == ScreenType.EndGame)
			return ScreenType.HighScroreScreen;
		else if (this.nextScreenTpe == ScreenType.GameScreen)
			return ScreenType.EndGame;
		else
			return ScreenType.GameScreen;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawTitle(this);
		drawManager.drawMenu(this, this.nextScreenTpe);

		drawManager.completeDrawing(this);
	}
}
