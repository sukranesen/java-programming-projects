import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * GameStats class keeps track of the player's score and lives.
 * It updates the display and checks for game over when lives reach 0.
 */
public class GameStats {

    private static int score = 0;      // Player's score
    private static int lives = 3;      // Number of lives left

    private static Label scoreLabel;   // Label to show score on screen
    private static Label livesLabel;   // Label to show lives on screen

    private static Pane root;          // Game screen pane
    private static Runnable gameOverCallback; // Function to call when lives reach 0

    /**
     * Sets up score and lives labels on the screen.
     */
    public static void init(Pane gameRoot, Runnable onGameOver) {
        root = gameRoot;
        gameOverCallback = onGameOver;

        // Create and style score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        scoreLabel.setTextFill(Color.BLUE);
        scoreLabel.setLayoutX(50);
        scoreLabel.setLayoutY(50);

        // Create and style lives label
        livesLabel = new Label("Lives: 3");
        livesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        livesLabel.setTextFill(Color.ORANGERED);
        livesLabel.setLayoutX(50);
        livesLabel.setLayoutY(70);

        // Add labels to the screen
        root.getChildren().addAll(scoreLabel, livesLabel);
    }

    /**
     * Adds 1 point to the score and updates the label.
     */
    public static void increaseScore() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Reduces player's lives by 1.
     * Triggers game over if lives become 0.
     */
    public static void loseLife() {
        lives--;
        livesLabel.setText("Lives: " + lives);
        if (lives <= 0 && gameOverCallback != null) {
            gameOverCallback.run();
        }
    }

    /**
     * Resets score and lives to default values.
     * Updates the labels on screen.
     */
    public static void reset() {
        score = 0;
        lives = 3;
        if (scoreLabel != null) scoreLabel.setText("Score: 0");
        if (livesLabel != null) livesLabel.setText("Lives: 3");
    }

    // Returns current score
    public static int getScore() {
        return score;
    }

    // Returns current number of lives
    public static int getLives() {
        return lives;
    }
}
