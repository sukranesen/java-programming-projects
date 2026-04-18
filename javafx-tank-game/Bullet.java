import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.List;

/**
 * Bullet class creates a bullet that moves in a direction
 * and checks for collisions with walls, enemy tanks, or player tank.
 */
public class Bullet {

    private ImageView imageView; // Bullet image on screen
    private String direction;    // Bullet direction (UP, DOWN, LEFT, RIGHT)
    private Timeline timeline;   // Controls bullet movement timing
    private static final double SPEED = 6;
    private boolean isPlayerBullet; // True if fired by player, false if by enemy

    /**
     * This constructor creates a bullet and starts its movement.
     * It checks for collisions while moving.
     */
    public Bullet(Pane root, double startX, double startY, String direction,
                  List<EnemyTank> enemies, List<Wall> walls, boolean isPlayerBullet, PlayerTank player) {

        this.direction = direction;
        this.isPlayerBullet = isPlayerBullet;

        // Create bullet image
        imageView = new ImageView(Assets.bullet);
        imageView.setFitWidth(14);
        imageView.setFitHeight(14);
        imageView.setX(startX);
        imageView.setY(startY);
        root.getChildren().add(imageView);

        // Set up bullet movement every 20ms
        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            double dx = 0, dy = 0;

            // Set movement based on direction
            if (direction.equals(Direction.UP)) {
                dy = -SPEED;
            } else if (direction.equals(Direction.DOWN)) {
                dy = SPEED;
            } else if (direction.equals(Direction.LEFT)) {
                dx = -SPEED;
            } else if (direction.equals(Direction.RIGHT)) {
                dx = SPEED;
            }

            // Move bullet
            imageView.setX(imageView.getX() + dx);
            imageView.setY(imageView.getY() + dy);

            // If bullet hits a wall, show small explosion and remove bullet
            for (Wall wall : walls) {
                if (imageView.getBoundsInParent().intersects(wall.getImageView().getBoundsInParent())) {
                    new Explosion(root, imageView.getX(), imageView.getY(), Explosion.Type.SMALL);
                    stop();
                    root.getChildren().remove(imageView);
                    return;
                }
            }

            if (isPlayerBullet) {
                // If player bullet hits an enemy tank
                for (EnemyTank enemy : enemies) {
                    if (enemy.isAlive() &&
                            imageView.getBoundsInParent().intersects(enemy.getImageView().getBoundsInParent())) {
                        new Explosion(root, enemy.getX(), enemy.getY(), Explosion.Type.BIG);
                        enemy.destroy(root);
                        stop();
                        root.getChildren().remove(imageView);
                        return;
                    }
                }
            } else {
                // If enemy bullet hits the player
                if (player != null && imageView.getBoundsInParent().intersects(player.getImageView().getBoundsInParent())) {
                    new Explosion(root, player.getX(), player.getY(), Explosion.Type.BIG);
                    GameStats.loseLife();
                    root.getChildren().remove(player.getImageView());

                    // If player still has lives, respawn after delay
                    if (GameStats.getLives() > 0) {
                        PauseTransition delay = new PauseTransition(Duration.seconds(1));
                        delay.setOnFinished(event -> {
                            player.resetPosition(400, 500);
                            root.getChildren().add(player.getImageView());
                        });
                        delay.play();
                    }

                    stop();
                    root.getChildren().remove(imageView);
                }
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE); // Bullet moves forever unless stopped
        timeline.play(); // Start bullet movement
    }

    // Stops the bullet's timeline (used when it hits something)
    private void stop() {
        if (timeline != null) timeline.stop();
    }
}
