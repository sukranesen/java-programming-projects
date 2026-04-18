import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.List;

/**
 * PlayerTank class controls the player's tank.
 * It can move, shoot bullets, and detect collisions.
 */
public class PlayerTank {

    private ImageView imageView;      // Tank image
    private String direction;         // Current direction (UP, DOWN, etc.)
    private double x, y;              // Tank position
    private static final double MOVE_SPEED = 5;

    private Pane root;                // Game screen
    private List<EnemyTank> enemies; // List of enemy tanks
    private List<Wall> walls;        // List of walls
    private boolean frameToggle = false; // Used to switch tank image for animation

    /**
     * Creates the player's tank at a starting position.
     */
    public PlayerTank(Pane root, double startX, double startY, List<EnemyTank> enemies, List<Wall> walls) {
        this.root = root;
        this.x = startX;
        this.y = startY;
        this.enemies = enemies;
        this.walls = walls;
        this.direction = Direction.UP;

        // Load tank image and set properties
        imageView = new ImageView(Assets.yellowTank1);
        imageView.setFitWidth(34);
        imageView.setFitHeight(34);
        imageView.setPreserveRatio(true);
        imageView.setRotate(270); // Facing up

        imageView.setX(x);
        imageView.setY(y);
        root.getChildren().add(imageView);
    }

    /**
     * Moves the tank in the given direction if there's no collision.
     */
    public void move(String dir) {
        this.direction = dir;

        double newX = x;
        double newY = y;

        // Update position and rotation based on direction
        if (dir.equals(Direction.UP)) {
            newY -= MOVE_SPEED;
            imageView.setRotate(270);
        } else if (dir.equals(Direction.DOWN)) {
            newY += MOVE_SPEED;
            imageView.setRotate(90);
        } else if (dir.equals(Direction.LEFT)) {
            newX -= MOVE_SPEED;
            imageView.setRotate(180);
        } else if (dir.equals(Direction.RIGHT)) {
            newX += MOVE_SPEED;
            imageView.setRotate(0);
        }

        imageView.setX(newX);
        imageView.setY(newY);

        boolean collision = false;

        // Check collision with walls
        for (Wall wall : walls) {
            if (imageView.getBoundsInParent().intersects(wall.getImageView().getBoundsInParent())) {
                collision = true;
                break;
            }
        }

        // Check collision with enemies
        for (EnemyTank enemy : enemies) {
            if (enemy.isAlive() && imageView.getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent())) {
                collision = true;
                break;
            }
        }

        // If collision, cancel movement
        if (collision) {
            imageView.setX(x);
            imageView.setY(y);
        } else {
            // Update position and switch image for animation
            x = newX;
            y = newY;
            frameToggle = !frameToggle;
            imageView.setImage(frameToggle ? Assets.yellowTank1 : Assets.yellowTank2);
        }
    }

    /**
     * Shoots a bullet in the current direction.
     */
    public void shoot() {
        double bulletX = x + 13;
        double bulletY = y + 13;
        new Bullet(root, bulletX, bulletY, direction, enemies, walls, true, this);
    }

    /**
     * Resets the tank's position to the given coordinates.
     */
    public void resetPosition(double newX, double newY) {
        this.x = newX;
        this.y = newY;
        imageView.setX(x);
        imageView.setY(y);
    }

    // Returns the tank image for drawing or collision checks
    public ImageView getImageView() {
        return imageView;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
