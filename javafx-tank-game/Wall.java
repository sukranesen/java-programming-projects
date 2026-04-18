import javafx.scene.image.ImageView;

/**
 * Wall class creates a solid, unbreakable wall on the map.
 * Bullets disappear when they hit it.
 */
public class Wall {

    private ImageView imageView;  // Wall image
    private double x;             // X position
    private double y;             // Y position
    private static final int SIZE = 40;  // Wall width and height

    /**
     * Makes a wall at the given position on the screen.
     */
    public Wall(double x, double y) {
        this.x = x;
        this.y = y;

        // Load wall image and set position
        imageView = new ImageView(Assets.wall);
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Returns the image of this wall.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Returns X position of the wall.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns Y position of the wall.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the size (width and height) of the wall.
     */
    public static int getSize() {
        return SIZE;
    }
}
