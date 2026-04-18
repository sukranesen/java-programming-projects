import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class starts the game. It handles user input,
 * manages game states (pause, game over), and controls the scene.
 */
public class Main extends Application {

    public static Pane gameRoot;                   // Game screen
    public static List<EnemyTank> enemies = new ArrayList<>(); // All enemy tanks
    public static List<Wall> walls = new ArrayList<>();         // All walls
    public static PlayerTank player;               // Player tank

    private Scene scene;

    private Label gameOverLabel; // Text shown when game ends
    private Label pauseLabel;    // Text shown when game is paused

    private boolean isPaused = false;
    private boolean isGameOver = false;

    /**
     * This method starts the game window and sets up the game.
     */
    @Override
    public void start(Stage primaryStage) {
        gameRoot = new Pane();
        gameRoot.setStyle("-fx-background-color: black;");
        scene = new Scene(gameRoot, 800, 600);

        createWalls(); // Add walls to the map
        player = new PlayerTank(gameRoot, 400, 500, enemies, walls); // Create player tank
        spawnEnemies(1); // Spawn the first enemy

        GameStats.init(gameRoot, this::showGameOverMenu); // Score and lives

        // Game Over text setup
        gameOverLabel = new Label("GAME OVER\nPress R to Restart or ESC to Exit");
        gameOverLabel.setFont(new javafx.scene.text.Font(28));
        gameOverLabel.setTextFill(Color.ORANGERED);
        gameOverLabel.setVisible(false);
        gameOverLabel.setLayoutX(250);
        gameOverLabel.setLayoutY(250);
        gameRoot.getChildren().add(gameOverLabel);

        // Pause text setup
        pauseLabel = new Label("PAUSED\nPress R to Restart  ESC to Exit");
        pauseLabel.setFont(new javafx.scene.text.Font(28));
        pauseLabel.setTextFill(Color.LIMEGREEN);
        pauseLabel.setVisible(false);
        pauseLabel.setLayoutX(250);
        pauseLabel.setLayoutY(250);
        gameRoot.getChildren().add(pauseLabel);

        // Show these labels above everything
        gameOverLabel.toFront();
        pauseLabel.toFront();

        // Handle key presses
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                togglePause();
                return;
            }

            if (isGameOver) {
                if (event.getCode() == KeyCode.R) restartGame();
                else if (event.getCode() == KeyCode.ESCAPE) System.exit(0);
                return;
            }

            if (isPaused) {
                if (event.getCode() == KeyCode.R) restartGame();
                else if (event.getCode() == KeyCode.ESCAPE) System.exit(0);
                return;
            }

            // Player controls
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W)
                player.move(Direction.UP);
            else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S)
                player.move(Direction.DOWN);
            else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A)
                player.move(Direction.LEFT);
            else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D)
                player.move(Direction.RIGHT);
            else if (event.getCode() == KeyCode.X)
                player.shoot();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank 2025 - BBM104");
        primaryStage.show();
    }

    /**
     * Adds walls to the map, avoiding player spawn area.
     */
    private void createWalls() {
        int tileSize = 40;
        int cols = 800 / tileSize;
        int rows = 600 / tileSize;

        int playerTileX = 400 / tileSize;
        int playerTileY = 500 / tileSize;

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                boolean inPlayerSpawnZone = Math.abs(i - playerTileX) <= 1 && Math.abs(j - playerTileY) <= 1;

                if (inPlayerSpawnZone) continue;

                // Add border walls and some random inner walls
                if (i == 0 || j == 0 || i == cols - 1 || j == rows - 1 || Math.random() < 0.08) {
                    Wall wall = new Wall(i * tileSize, j * tileSize);
                    walls.add(wall);
                    gameRoot.getChildren().add(wall.getImageView());
                }
            }
        }
    }

    /**
     * Pauses or resumes the game.
     */
    private void togglePause() {
        isPaused = !isPaused;
        pauseLabel.setVisible(isPaused);
        pauseLabel.toFront();
        for (EnemyTank enemy : enemies) {
            if (enemy.isAlive()) {
                if (isPaused) enemy.pause();
                else enemy.resume();
            }
        }
    }

    /**
     * Shows the game over screen and stops enemies.
     */
    private void showGameOverMenu() {
        isGameOver = true;
        gameOverLabel.setText("GAME OVER\nScore: " + GameStats.getScore() + "\nPress R to Restart or ESC to Exit");
        gameOverLabel.setVisible(true);
        gameOverLabel.toFront();
        for (EnemyTank enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.pause();
            }
        }
    }

    /**
     * Restarts the game from the beginning.
     */
    private void restartGame() {
        gameRoot.getChildren().clear();
        enemies.clear();
        walls.clear();
        isPaused = false;
        isGameOver = false;

        createWalls();
        player = new PlayerTank(gameRoot, 400, 500, enemies, walls);
        spawnEnemies(1);
        GameStats.reset();
        GameStats.init(gameRoot, this::showGameOverMenu);

        gameRoot.getChildren().addAll(pauseLabel, gameOverLabel);
        pauseLabel.setVisible(false);
        gameOverLabel.setVisible(false);

        pauseLabel.toFront();
        gameOverLabel.toFront();
    }

    /**
     * Spawns enemy tanks without overlapping walls or each other.
     */
    public static void spawnEnemies(int count) {
        int created = 0;
        Random random = new Random();

        long aliveCount = enemies.stream().filter(EnemyTank::isAlive).count();

        while (created < count && aliveCount + created < 8) {
            double x = 100 + random.nextInt(600);
            double y = 60 + random.nextInt(200);

            EnemyTank temp = new EnemyTank(gameRoot, enemies, walls, x, y, false);
            boolean overlaps = false;

            // Check overlap with other enemies
            for (EnemyTank e : enemies) {
                if (e.isAlive() && temp.getImageView().getBoundsInParent().intersects(e.getImageView().getBoundsInParent())) {
                    overlaps = true;
                    break;
                }
            }

            // Check overlap with walls
            for (Wall w : walls) {
                if (temp.getImageView().getBoundsInParent().intersects(w.getImageView().getBoundsInParent())) {
                    overlaps = true;
                    break;
                }
            }

            gameRoot.getChildren().remove(temp.getImageView());

            // If no overlap, spawn real enemy
            if (!overlaps) {
                EnemyTank real = new EnemyTank(gameRoot, enemies, walls, x, y, true);
                enemies.add(real);
                created++;
            }
        }
    }

    /**
     * Main method to start the game.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
