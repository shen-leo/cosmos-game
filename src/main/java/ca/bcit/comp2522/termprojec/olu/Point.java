package ca.bcit.comp2522.termprojec.olu;

import java.util.Objects;

public  class Point {
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