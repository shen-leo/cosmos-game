package ca.bcit.comp2522.termprojec.olu;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Enemy {
    private final Player player;
    private final StackPane pane;
    private ImageView imageView;
    private final UI ui;
    private double x;
    private double y;

    public Enemy(Player player, StackPane pane, UI ui) {
        this.player = player;
        this.pane = pane;
        this.ui = ui;
    }
    public void displayEnemy() throws IOException {
        InputStream is = Files.newInputStream(Paths.get("src/main/resources/images/enemy.png"));
        imageView = HelloApplication.displaySprite(is);
        is.close();
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
        x = imageView.getTranslateX();
        y = imageView.getTranslateY();
        pane.getChildren().addAll(imageView);
    }
    public static class Point {
        public double x;
        public double y;
        public Point previous;

        public Point(double x, double y, Point previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }

        @Override
        public String toString() { return String.format("(%f, %f)", x, y); }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, previous);
        }

        public Point offset(int ox, int oy) { return new Point(x + ox, y + oy, this);  }
    }
    public static boolean IsWalkable(int[][] map, Point point) {
        if (point.y < 0 || point.y > map.length - 1) return false;
        if (point.x < 0 || point.x > map[0].length - 1) return false;
        return map[(int) point.y][(int) point.x] == 0;
    }

    public static List<Point> FindNeighbors(int[][] map, Point point) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
        if (IsWalkable(map, up)) neighbors.add(up);
        if (IsWalkable(map, down)) neighbors.add(down);
        if (IsWalkable(map, left)) neighbors.add(left);
        if (IsWalkable(map, right)) neighbors.add(right);
        return neighbors;
    }

    public static List<Point> FindPath(int[][] map, Point start, Point end) {
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Point point = used.get(i);
                for (Point neighbor : FindNeighbors(map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for(Point point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty())
                return null;
        }

        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }

    public void startPathFind() {
        int[][] map = new int[9][9];
        double tempX = x/64;
        double playerTempX = player.getX()/64;
        double tempY = y/64;
        double playerTempY = player.getY()/64;
        boolean xIsNeg =false;
        boolean yIsNeg =false;
        boolean playerXIsNeg =false;
        boolean playerYIsNeg =false;
        for (int[] ints : map) {
            Arrays.fill(ints, 0);
        }
        if (y/64 <= -1) {
            tempY = y/64 * -1;
            yIsNeg = true;
        }
        if (x/64 <= -1) {
            tempX = x/64 * -1;
            xIsNeg = true;
        }
        if (player.getX()/64 <= -1) {
            playerTempX =  player.getX()/64 * -1;
            playerXIsNeg = true;
        }
        if (player.getY()/64 <= -1) {
            playerTempY = player.getY()/64 * -1;
            playerYIsNeg = true;
        }
        Point start = new Point(tempX, tempY, null);
        Point end = new Point(playerTempX, playerTempY, null);
        List<Point> path = FindPath(map, start, end);
        if (path != null) {
            if (xIsNeg || playerXIsNeg) {
                imageView.setTranslateX(path.get(0).x * 64 * -1);
            } else {
                imageView.setTranslateX(path.get(0).x * 64);
            }
            if (yIsNeg || playerYIsNeg) {
                imageView.setTranslateY(path.get(0).y * 64 * -1);

            } else  {
                imageView.setTranslateY(path.get(0).y * 64);

            }

        }
        else {
            System.out.println("No path found");
        }
        this.x = imageView.getTranslateX();
        this.y = imageView.getTranslateY();
    }
    @Override
    public String toString() { return String.format("(%f, %f)", this.x, this.y); }
    public Enemy checkEnemyState(final Player player) {

        if (player.toString().equals(toString())) {
            if (!player.playerHasSword) {
                ui.removeHeart();
                System.out.println("Player hit");
            } else {
                player.playerHasSword = false;
                ui.removeSword();
            }
            return new Enemy(player, pane, ui);
        }
        return null;
    }
}
// https://gamedev.stackexchange.com/questions/197165/java-simple-2d-grid-pathfinding