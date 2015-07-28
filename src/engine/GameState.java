package engine;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/** Current game level. */
	private int level;
	/** Current score. */
	private int score;
	/** Lives currently remaining. */
	private int livesRemaining;
	/** Bullets shot until now. */
	private int bulletsShot;
	/** Ships destroyed until now. */
	private int shipsDestroyed;

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            Current game level.
	 * @param score
	 *            Current score.
	 * @param livesRemaining
	 *            Lives currently remaining.
	 * @param bulletsShot
	 *            Bullets shot until now.
	 * @param shipsDestroyed
	 *            Ships destroyed until now.
	 */
	public GameState(final int level, final int score,
			final int livesRemaining, final int bulletsShot,
			final int shipsDestroyed) {
		this.level = level;
		this.score = score;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
	}

	/**
	 * @return the level
	 */
	public final int getLevel() {return level; }

	/**
	 * Sets a new level
	 * @param newLevel
	 */
	public final void setLevel(int newLevel) { this.level = newLevel; }

	/**
	 * @return the score
	 */
	public final int getScore() { return score;	}

	/**
	 * Sets a new score
	 * @param newScore
	 * @return
	 */
	public final void setScore(int newScore) { this.score = newScore; }

	/**
	 * @return the livesRemaining
	 */
	public final int getLivesRemaining() {
		return livesRemaining;
	}

	/**
	 * Set new lives remaining
	 * @param newLivesRemaining
	 */
	public final void setLivesRemaining(int newLivesRemaining) { this.livesRemaining = newLivesRemaining; }

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() { return bulletsShot; }

	/**
	 * Set bullets shot
	 * @param bulletsShot
	 */
	public void setBulletsShot(int bulletsShot) { this.bulletsShot = bulletsShot; }

	/**
	 * @return the shipsDestroyed
	 */
	public final int getShipsDestroyed() {
		return shipsDestroyed;
	}

	/**
	 * Set ships destroyed
	 * @param shipsDestroyed
	 */
	public void setShipsDestroyed(int shipsDestroyed) { this.shipsDestroyed = shipsDestroyed; }

}
