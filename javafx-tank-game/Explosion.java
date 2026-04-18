import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Explosion class creates a visual explosion effect
 * and removes it automatically after a short time.
 */
public class Explosion {

    // Explosion type: small or big
    public enum Type {
        SMALL,
        BIG
    }

    private ImageView imageView; // Image shown for explosion

    /**
     * Shows an explosion effect at a given position.
     * It disappears after 0.4 seconds.
     */
    public Explosion(Pane root, double x, double y, Type type) {
        // Choose correct explosion image
        imageView = new ImageView(type == Type.BIG ? Assets.explosion : Assets.smallExplosion);

        // Set image size based on explosion type
        if (type == Type.BIG) {
            imageView.setFitWidth(36);
            imageView.setFitHeight(36);
        } else {
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
        }

        // Set position and add to screen
        imageView.setX(x);
        imageView.setY(y);
        root.getChildren().add(imageView);

        // Remove explosion after 0.4 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(0.4));
        pause.setOnFinished(event -> root.getChildren().remove(imageView));
        pause.play();
    }
}
