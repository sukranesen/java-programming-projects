import javafx.scene.image.Image;

/**
 * Stores all images used in the game (tanks, bullets, walls, explosions).
 * These are used to draw game objects on the screen.
 */
public class Assets {

    public static final Image yellowTank1 = new Image("file:yellowTank1.png");
    public static final Image yellowTank2 = new Image("file:yellowTank2.png");

    public static final Image whiteTank1 = new Image("file:whiteTank1.png");
    public static final Image whiteTank2 = new Image("file:whiteTank2.png");

    public static final Image bullet = new Image("file:bullet.png");

    public static final Image wall = new Image("file:wall.png");

    // Big explosion effect image
    public static final Image explosion = new Image("file:explosion.png");

    // Small explosion effect image (for bullets hitting walls)
    public static final Image smallExplosion = new Image("file:smallExplosion.png");
}
