import static java.lang.Math.sqrt;

public class Point2D {
    /* оно все паблик по той причине, что при вызове моих деревьев придется работать с моим собственным типом точки */
    public double x, y;
    Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point2D add(Point2D b) {
        return new Point2D(this.x + b.x, this.y + b.y);
    }
    public Point2D sub(Point2D b) {
        return new Point2D(this.x - b.x, this.y - b.y);
    }
    public Point2D scale(double s) {
        return new Point2D(this.x * s, this.y * s);
    }
    public boolean lessThan(Point2D b) {
        return this.x < b.x && this.y < b.y;
    }
    public boolean greaterThan(Point2D b) {
        return this.x > b.x && this.y > b.y;
    }
    public double lengthTo(Point2D b) {
        return sqrt((b.x-x) * (b.x-x) + (b.y-y) * (b.y-y));
    }
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
