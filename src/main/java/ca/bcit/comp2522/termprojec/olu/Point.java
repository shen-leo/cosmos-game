package ca.bcit.comp2522.termprojec.olu;

import java.util.Objects;
/**
 * X and Y point.
 * @author Urjit, Leo
 * @version 2022
 */
public class Point {
    /**
     * Current X coordinate.
     */
    public double x;
    /**
     * Current Y coordinate.
     */
    public double y;
    /**
     * Previous Point.
     */
    public Point previous;

    /**
     * Constructs a new point.
     * @param x X Coordinate
     * @param y Y Coordinate
     * @param previous Previous point
     */
    public Point(final double x, final double y, final Point previous) {
        this.x = x;
        this.y = y;
        this.previous = previous;
    }

    /**
     * To String method.
     * @return Current Coordinates
     */
    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    /**
     * Equals method.
     * @param o Object to compare
     * @return True if objects are same
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    /**
     * Hashcode method.
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, previous);
    }

    /**
     * Offsets Point.
     * @param ox Offset X
     * @param oy Offset Y
     * @return Offset Point
     */
    public Point offset(final int ox, final int oy) {
        return new Point(x + ox, y + oy, this);
    }
}
