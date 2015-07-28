package engine;

import screen.*;

import java.util.List;

/**
 * Created by Ryan on 7/28/2015.
 */
public class Invaders {

    /**
     * Start running the game
     */
    public static void run(Frame frame) {
        // Get the size of the frame
        int width = frame.getWidth();
        int height = frame.getHeight();

        // Get a list of levels to play
        List<GameSettings> levelSettings = Levels.getLevels();

        // Hold on to all of the game's information
        GameState gameState;

        // The current screen
        // Empty by default
        Screen currentScreen;

        // Start at the title screen
        ScreenType nextScreen = ScreenType.TitleScreen;

        while (nextScreen != ScreenType.EndGame) {
            // Reset the game's state each time the game starts over
            gameState = new GameState(1, 0, Constants.MAX_LIVES, 0, 0);

            if(nextScreen == ScreenType.TitleScreen) {
                // Main menu.
                currentScreen = new TitleScreen(width, height, Main.FPS);
                nextScreen = frame.setScreen(currentScreen);
            }
            else if (nextScreen == ScreenType.GameScreen) {
                currentScreen = new GameScreen(gameState, levelSettings, width, height, Main.FPS);
                frame.setScreen(currentScreen);

                currentScreen = new ScoreScreen(width, height, Main.FPS, gameState);
                nextScreen = frame.setScreen(currentScreen);
            }
            else if (nextScreen == ScreenType.HighScroreScreen) {
                // High scores.
                currentScreen = new HighScoreScreen(width, height, Main.FPS);
                nextScreen = frame.setScreen(currentScreen);
            }

        }
    }
}
