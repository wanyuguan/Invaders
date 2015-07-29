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
    public static void run() {

        // Get a list of levels to play
        List<GameSettings> levelSettings = Levels.getLevels();

        // Hold on to all of the game's information
        GameState gameState = new GameState(1, 0, Constants.MAX_LIVES, 0, 0);

        // The current screen
        // Empty by default
        Screen currentScreen;

        // Start at the title screen
        ScreenType nextScreen = ScreenType.TitleScreen;

        while (nextScreen != ScreenType.EndGame) {

            if(nextScreen == ScreenType.TitleScreen) {
                currentScreen = new TitleScreen();
            }
            else if (nextScreen == ScreenType.GameScreen) {
                gameState.reset();
                currentScreen = new GameScreen(gameState, levelSettings);
            }
            else if(nextScreen == ScreenType.ScoreScreen) {
                currentScreen = new ScoreScreen(gameState);
            }
            else if (nextScreen == ScreenType.HighScroreScreen) {
                currentScreen = new HighScoreScreen();
            }
            else {
                throw new IllegalStateException("Screen not found!");
            }

            currentScreen.show();
            nextScreen = currentScreen.getNextScreen();
        }
    }
}
