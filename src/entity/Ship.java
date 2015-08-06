package entity;

import java.awt.Color;
import java.util.Set;

import engine.Cooldown;
import engine.Main;
import engine.DrawManager.SpriteType;
import screen.Screen;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	/** Time between shots. */
	private static final int SHOOTING_INTERVAL = 750;
	/** Speed of the bullets shot by the ship. */
	private static final int BULLET_SPEED = -6;
	/** Movement of the ship for each unit of time. */
	private static final int SPEED = 2;
	
	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 */
	public Ship(final int positionX, final int positionY) {
		super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);

		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Main.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Main.getCooldown(1000);
	}

	/**
	 * Checks the ship's movement.
	 * Moves the ship if the right or left keys are pressed
	 * and the player is not against the left or right wall.
	 */
	public void checkMovement(Screen screen) {
		// Detects whether the ship is not touching the right border
		boolean isShipNotTouchingRightBorder = !(this.getPositionX() + this.getWidth() + this.getSpeed() > screen.getWidth() - 1);
		if (Main.getInputManager().isRightKeyDown() && isShipNotTouchingRightBorder) {
			this.moveRight();
		} else if (Main.getInputManager().isLeftKeyDown() &&isShipNotTouchingRightBorder ) {
			this.moveLeft();
		}

		// Detects whether the ship is not touching the left border
		boolean isShipNotTouchingLeftBorder = !(this.getPositionX() - this.getSpeed() < 1);
		if (Main.getInputManager().isLeftKeyDown() && isShipNotTouchingLeftBorder) {
			this.moveLeft();
		} else if (Main.getInputManager().isRightKeyDown()&&isShipNotTouchingLeftBorder ) {
			this.moveRight();
		}

	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() { this.positionX += SPEED; }

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= SPEED;
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets) {
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			bullets.add(BulletPool.getBullet(positionX + this.width / 2,
					positionY, BULLET_SPEED));
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
	}

	/**
	 * Checks if the ship is destroyed.
	 * 
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED;
	}
}
