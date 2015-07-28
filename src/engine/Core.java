package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import screen.GameScreen;
import screen.HighScoreScreen;
import screen.ScoreScreen;
import screen.Screen;
import screen.TitleScreen;

/**
 * Implements core game logic.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class Core {

	/** Width of current screen. */
	public static final int WIDTH = 448;
	/** Height of current screen. */
	public static final int HEIGHT = 520;
	/** Max fps of current screen. */
	public static final int FPS = 60;

	/** Max lives. */
	private static final int MAX_LIVES = 3;
	/** Levels between extra life. */
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	/** Total number of levels. */
	private static final int NUM_LEVELS = 7;
	
	/** Frame to draw the screen on. */
	private static Frame frame;
	/** Screen currently shown. */
	private static Screen currentScreen;
	/** Difficulty settings list. */
	private static List<GameSettings> gameSettings;
	/** Application logger. */
	private static final Logger LOGGER = Logger.getLogger(Core.class
			.getSimpleName());
	/** Logger handler for printing to disk. */
	private static Handler fileHandler;
	/** Logger handler for printing to console. */
	private static ConsoleHandler consoleHandler;


	/**
	 * Test implementation.
	 * 
	 * @param args
	 *            Program args, ignored.
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.setUseParentHandlers(false);

			fileHandler = new FileHandler("log");
			fileHandler.setFormatter(new MinimalFormatter());

			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MinimalFormatter());

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

		} catch (Exception e) {
			// TODO handle exception
			e.printStackTrace();
		}

		frame = new Frame(WIDTH, HEIGHT);
		DrawManager.getInstance().setFrame(frame);
		int width = frame.getWidth();
		int height = frame.getHeight();

		// Run the game
		run(width, height);

		fileHandler.flush();
		fileHandler.close();
		System.exit(0);
	}

	/**
	 * Start running the game
	 * @param width
	 * @param height
	 */
	public static void run(int width, int height) {
		// Get a list of levels to play
		gameSettings = Levels.getLevels();

		// Hold on to all of the game's information
		GameState gameState;

		// Start at the title screen
		ScreenType nextScreen = ScreenType.TitleScreen;

		do {
			// Reset the game's state each time the game starts over
			gameState = new GameState(1, 0, MAX_LIVES, 0, 0);

			if(nextScreen == ScreenType.TitleScreen) {
				// Main menu.
				currentScreen = new TitleScreen(width, height, FPS);
				nextScreen = frame.setScreen(currentScreen);
			}
			else if (nextScreen == ScreenType.GameScreen) {
				// Game & score.
				do {
					// One extra life every few levels.
					boolean bonusLife = gameState.getLevel()
							% EXTRA_LIFE_FRECUENCY == 0
							&& gameState.getLivesRemaining() < MAX_LIVES;

					currentScreen = new GameScreen(gameState,
							gameSettings.get(gameState.getLevel() - 1),
							bonusLife, width, height, FPS);
					frame.setScreen(currentScreen);

					gameState = ((GameScreen) currentScreen).getGameState();

					gameState = new GameState(gameState.getLevel() + 1,
							gameState.getScore(),
							gameState.getLivesRemaining(),
							gameState.getBulletsShot(),
							gameState.getShipsDestroyed());

				} while (gameState.getLivesRemaining() > 0
						&& gameState.getLevel() <= NUM_LEVELS);

				currentScreen = new ScoreScreen(width, height, FPS, gameState);
				nextScreen = frame.setScreen(currentScreen);
			}
			else if (nextScreen == ScreenType.HighScroreScreen) {
				// High scores.
				currentScreen = new HighScoreScreen(width, height, FPS);
				nextScreen = frame.setScreen(currentScreen);
			}

		} while (nextScreen != ScreenType.EndGame);
	}

	/**
	 * Constructor, not called.
	 */
	private Core() {

	}

	/**
	 * Controls access to the logger.
	 * 
	 * @return Application logger.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Controls access to the drawing manager.
	 * 
	 * @return Application draw manager.
	 */
	public static DrawManager getDrawManager() {
		return DrawManager.getInstance();
	}

	/**
	 * Controls access to the input manager.
	 * 
	 * @return Application input manager.
	 */
	public static InputManager getInputManager() {
		return InputManager.getInstance();
	}

	/**
	 * Controls access to the file manager.
	 * 
	 * @return Application file manager.
	 */
	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	/**
	 * Controls creation of new cooldowns.
	 * 
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @return A new cooldown.
	 */
	public static Cooldown getCooldown(final int milliseconds) {
		return new Cooldown(milliseconds);
	}

	/**
	 * Controls creation of new cooldowns with variance.
	 * 
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @param variance
	 *            Variation in the cooldown duration.
	 * @return A new cooldown with variance.
	 */
	public static Cooldown getVariableCooldown(final int milliseconds,
			final int variance) {
		return new Cooldown(milliseconds, variance);
	}
}