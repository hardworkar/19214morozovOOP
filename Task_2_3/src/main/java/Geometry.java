import java.awt.*;

public class Geometry {
    abstract static class GeometryObject {
        protected Point2D center;
        public abstract boolean contains(Point2D point);
        public abstract Rectangle bound();
    }

    static class Sphere extends GeometryObject {
        private final double radius;
        public Sphere(Point2D center, double radius) {
            this.center = center;
            this.radius = radius;
        }
        public boolean contains(Point2D point) {
            return center.lengthTo(point) <= radius;
        }

        @Override
        public Rectangle bound() {
            return new Rectangle(center.sub(new Point2D(radius, radius)), center.add(new Point2D(radius, radius)));
        }

        @Override
        public String toString() {
            return "Sphere{" + "radius=" + radius + ", " + center.toString() + '}';
        }
    }
    static class Rectangle extends GeometryObject {
        private final Point2D leftDown;
        private final Point2D rightUp;
        public Rectangle(Point2D leftDown, Point2D rightUp) {
            center = rightUp.add(leftDown).scale(0.5);
            this.leftDown = leftDown;
            this.rightUp = rightUp;
        }
        public boolean contains(Rectangle b) {
            return (this.leftDown.lessThan(b.leftDown) && this.rightUp.greaterThan(b.rightUp));
        }

        @Override
        public boolean contains(Point2D point) {
            return leftDown.lessThan(point) && rightUp.greaterThan(point);
        }

        @Override
        public Rectangle bound() {
            return this;
        }
        @Override
        public String toString() {
            return "Rectangle{" + leftDown.toString() + "^" + rightUp.toString() + '}';
        }
    }
    static class Square extends GeometryObject {
        protected Point2D leftDown, rightUp, left, right, up, down;
        Square(Point2D leftDown, Point2D rightUp) {
            if(rightUp.x - leftDown.x != rightUp.y - leftDown.y) {
                throw new IllegalArgumentException("Квадрату хотелось бы иметь равные стороны");
            }
            center = rightUp.add(leftDown).scale(0.5);
            this.leftDown = leftDown;
            this.rightUp = rightUp;
            this.down = new Point2D(center.x, leftDown.y);
            this.up = new Point2D(center.x, rightUp.y);
            this.left = new Point2D(leftDown.x, center.y);
            this.right = new Point2D(rightUp.x, center.y);
        }
        public boolean contains(Rectangle b) {
            return (this.leftDown.lessThan(b.leftDown) && this.rightUp.greaterThan(b.rightUp));
        }

        @Override
        public boolean contains(Point2D point) {
            return false;
        }

        @Override
        public Rectangle bound() {
            return null;
        }

        public boolean intersect(Rectangle rectangle) {
            return rightUp.greaterThan(rectangle.leftDown) && leftDown.lessThan(rectangle.rightUp) ||
                    rectangle.rightUp.greaterThan(leftDown) && rectangle.leftDown.lessThan(rightUp);
        }
    }
}
