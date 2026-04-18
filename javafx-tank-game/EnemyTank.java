import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;

/**
 * EnemyTank class represents an enemy tank that moves randomly
 * and shoots bullets.
 */
public class EnemyTank {

    private ImageView imageView; // Image shown for the tank
    private String direction;    // Current direction of movement
    private double x, y;         // Position of the tank
    private static final double MOVE_SPEED = 2;

    private boolean alive = true;       // True if the tank is active
    private boolean isSpawned;          // True if the tank is added to the game
    private boolean frameToggle = false; // For simple tank animation

    private Timeline moveTimeline;     // Controls tank movement
    private Timeline shootTimeline;    // Controls when tank shoots

    private List<EnemyTank> enemies;   // List of other enemy tanks
    private List<Wall> walls;          // List of walls on the map
    private Pane root;                 // Game screen

    /**
     * Creates an enemy tank and optionally spawns it.
     */
    public EnemyTank(Pane root, List<EnemyTank> enemies, List<Wall> walls, double startX, double startY, boolean isSpawned) {
        this.root = root;
        this.enemies = enemies;
        this.walls = walls;
        this.x = startX;
        this.y = startY;
        this.isSpawned = isSpawned;

        // Load tank image
        imageView = new ImageView(Assets.whiteTank1);
        imageView.setFitWidth(36);
        imageView.setFitHeight(36);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setRotate(270); // Default to facing up

        // Add tank to the game and start movement/shooting
        if (isSpawned) {
            root.getChildren().add(imageView);
            startAI();
        }
    }

    // Starts tank AI: moves, shoots, and changes direction
    private void startAI() {
        Random random = new Random();
        changeDirection();

        moveTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> move()));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();

        shootTimeline = new Timeline(new KeyFrame(Duration.seconds(1 + random.nextDouble() * 2), e -> shoot()));
        shootTimeline.setCycleCount(Timeline.INDEFINITE);
        shootTimeline.play();

        Timeline changeDir = new Timeline(new KeyFrame(Duration.seconds(2), e -> changeDirection()));
        changeDir.setCycleCount(Timeline.INDEFINITE);
        changeDir.play();
    }

    // Picks a new random direction
    private void changeDirection() {
        String[] dirs = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        direction = dirs[new Random().nextInt(dirs.length)];
    }

    // Moves the tank in its current direction
    private void move() {
        if (!alive) return;

        double newX = x;
        double newY = y;

        // Change position based on direction
        if (direction.equals(Direction.UP)) {
            newY -= MOVE_SPEED;
            imageView.setRotate(270);
        } else if (direction.equals(Direction.DOWN)) {
            newY += MOVE_SPEED;
            imageView.setRotate(90);
        } else if (direction.equals(Direction.LEFT)) {
            newX -= MOVE_SPEED;
            imageView.setRotate(180);
        } else if (direction.equals(Direction.RIGHT)) {
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

        // Check collision with other enemies
        for (EnemyTank other : enemies) {
            if (other != this && other.isAlive() &&
                    imageView.getBoundsInParent().intersects(other.getImageView().getBoundsInParent())) {
                collision = true;
                break;
            }
        }

        // Check collision with player
        if (imageView.getBoundsInParent().intersects(Main.player.getImageView().getBoundsInParent())) {
            collision = true;
        }

        // Cancel movement if collision detected
        if (collision) {
            imageView.setX(x);
            imageView.setY(y);
            changeDirection();
        } else {
            x = newX;
            y = newY;
            frameToggle = !frameToggle;
            imageView.setImage(frameToggle ? Assets.whiteTank1 : Assets.whiteTank2); // Switch image for animation
        }
    }

    // Shoots a bullet toward the player
    private void shoot() {
        if (!alive) return;
        double bulletX = x + 13;
        double bulletY = y + 13;
        new Bullet(root, bulletX, bulletY, direction, enemies, walls, false, Main.player);
    }

    // Destroys the tank and shows explosion
    public void destroy(Pane root) {
        if (!alive) return;
        alive = false;
        root.getChildren().remove(imageView);
        moveTimeline.stop();
        shootTimeline.stop();
        GameStats.increaseScore();
        new Explosion(root, x, y, Explosion.Type.BIG);
        Main.spawnEnemies(2); // Replace with 2 new enemies
    }

    public boolean isAlive() {
        return alive;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Pause the tank (used in pause menu)
    public void pause() {
        if (moveTimeline != null) moveTimeline.pause();
        if (shootTimeline != null) shootTimeline.pause();
    }

    // Resume the tank after pause
    public void resume() {
        if (moveTimeline != null) moveTimeline.play();
        if (shootTimeline != null) shootTimeline.play();
    }
}
