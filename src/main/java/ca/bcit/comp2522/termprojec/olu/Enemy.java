package ca.bcit.comp2522.termprojec.olu;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Spawn and Manage Enemies.
 * @author Urjit, Leo
 * @version 2022
 */
public class Enemy {
    private final Player player;
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private double x;
    private double y;

    /**
     * Constructor for Enemy.
     * @param player Player
     * @param pane Pane to add enemy to
     * @param ui UI to manage enemy visuals
     */
    public Enemy(final Player player, final StackPane pane, final UI ui) {
        this.player = player;
        this.pane = pane;
        this.ui = ui;
    }

    /**
     * Displays Enemy.
     * @throws IOException If enemy graphic not found
     */
    public void displayEnemy() throws IOException {

        InputStream is;
        if (HelloApplication.getMapManager().getEnemy(HelloApplication.getLevelManager().getLevel()) != null) {
            is = Files.newInputStream(
                    Paths.get(HelloApplication.getMapManager().getEnemy(
                            HelloApplication.getLevelManager().getLevel())));
        } else {
            is = Files.newInputStream(Paths.get("src/main/resources/images/enemy/archfiend.gif"));
        }

        imageView = HelloApplication.displaySprite(is);
        is.close();
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        x = imageView.getTranslateX();
        y = imageView.getTranslateY();
        pane.getChildren().addAll(imageView);
    }

    /**
     * Finds if the surrounding coordinates are walkable.
     * @param map Map of coordinates
     * @param point Current point
     * @return True if surrounding is walkable
     */
    public static boolean isWalkable(final int[][] map, final Point point) {
        if (point.y < 0 || point.y > map.length - 1) {
            return false;
        }
        if (point.x < 0 || point.x > map[0].length - 1) {
            return false;
        }
        return map[(int) point.y][(int) point.x] == 0;
    }

    /**
     * Finds the neighbors of this point.
     * @param map Map of coordinates
     * @param point Current point
     * @return List of neighbors
     */
    public static List<Point> findNeighbors(final int[][] map, final Point point) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
        if (isWalkable(map, up)) {
            neighbors.add(up);
        }
        if (isWalkable(map, down)) {
            neighbors.add(down);
        }
        if (isWalkable(map, left)) {
            neighbors.add(left);
        }
        if (isWalkable(map, right)) {
            neighbors.add(right);
        }
        return neighbors;
    }

    /**
     * Finds the best path.
     * @param map Map of coordinates
     * @param start Start point
     * @param end End point
     * @return List of best points to travel
     */
    public static List<Point> findPath(final int[][] map, final Point start, final Point end) {
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for (int i = 0; i < used.size(); ++i) {
                Point point = used.get(i);
                for (Point neighbor : findNeighbors(map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for (Point point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty()) {
                return null;
            }
        }

        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while (point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }

    /**
     * Starts path find from these enemies coordinates.
     */
    public void startPathFind() {
        final int mapSize = 9;
        final int pixelCount = 64;
        int[][] map = new int[mapSize][mapSize];
        double tempX = x / pixelCount;
        double playerTempX = player.getX() / pixelCount;
        double tempY = y / pixelCount;
        double playerTempY = player.getY() / pixelCount;
        boolean xIsNeg = false;
        boolean yIsNeg = false;
        boolean playerXIsNeg = false;
        boolean playerYIsNeg = false;
        for (int[] ints : map) {
            Arrays.fill(ints, 0);
        }
        if (y / pixelCount <= -1) {
            tempY = y / pixelCount * -1;
            yIsNeg = true;
        }
        if (x / pixelCount <= -1) {
            tempX = x / pixelCount * -1;
            xIsNeg = true;
        }
        if (player.getX() / pixelCount <= -1) {
            playerTempX =  player.getX() / pixelCount * -1;
            playerXIsNeg = true;
        }
        if (player.getY() / pixelCount <= -1) {
            playerTempY = player.getY() / pixelCount * -1;
            playerYIsNeg = true;
        }
        Point start = new Point(tempX, tempY, null);
        Point end = new Point(playerTempX, playerTempY, null);
        List<Point> path = findPath(map, start, end);
        if (path != null) {
            double newX;
            if (xIsNeg || playerXIsNeg) {
                newX = path.get(0).x * pixelCount * -1;
            } else {
                newX = path.get(0).x * pixelCount;
            }
            if (this.imageView != null) {
                imageView.setTranslateX(newX);
            }
            this.x = newX;
            double newY;
            if (yIsNeg || playerYIsNeg) {
                newY = path.get(0).y * pixelCount * -1;
            } else  {
                newY = path.get(0).y * pixelCount;
            }
            if (this.imageView != null) {
                imageView.setTranslateY(newY);
            }
            this.y = newY;

        }
    }

    /**
     * To string method.
     * @return String of this enemy coordinates
     */
    @Override
    public String toString() {
        return String.format("(%f, %f)", this.x, this.y);
    }

    /**
     * Get coordinates of this enemy.
     * @return Hashmap of these enemies coordinates
     */
    public HashMap<String, Double> getCoordinates() {
        HashMap<String, Double> coordinates = new HashMap<>();
        coordinates.put("x", this.x);
        coordinates.put("y", this.y);
        return coordinates;
    }

    /**
     * Check if player is colliding with enemy.
     * @param currentPlayer Player
     * @return True of player is colliding with enemy
     */
    public boolean checkEnemyState(final Player currentPlayer) {

        if (currentPlayer.getCoordinates().equals(getCoordinates()) && this.imageView != null) {

            if (!currentPlayer.getPlayerHasSword()) {
                ui.removeHeart();
            } else {
                currentPlayer.setPlayerHasSword(false);
                ui.removeSword();
            }
            this.imageView.setVisible(false);
            animateDeath();
            return true;
        }
        return false;
    }

    private void animateDeath() {
        final int pixelCount = 64;
        try {
            Image image = new Image(new FileInputStream("src/main/resources/images/effects/explosion.gif"));

            ImageView deathAnimation = new ImageView(image);

            deathAnimation.setTranslateX(this.player.getX());
            deathAnimation.setTranslateY(this.player.getY());

            deathAnimation.setFitHeight(pixelCount);
            deathAnimation.setFitWidth(pixelCount);

            pane.getChildren().addAll(deathAnimation);
            String musicFile = "src/main/resources/sfx/knife_stab.mp3";

            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> deathAnimation.setVisible(false)));

            timeline.play();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set image to null.
     */
    public void nullImage() {
        this.imageView.setImage(null);
    }

    /**
     * Removes the item from the playable board.
     */
    public void consume() {
        final int outOfBounds = -1000;
        this.x = outOfBounds;
        this.y = outOfBounds;
    }
}
