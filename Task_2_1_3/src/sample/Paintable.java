package sample;

import javafx.scene.paint.Paint;

public interface Paintable {
    // return x-idx of the cell
    int getX();

    // return y-idx of the cell
    int getY();

    // return fill color
    Paint getFillColor();

    // return stroke color
    Paint getStrokeColor();
}
