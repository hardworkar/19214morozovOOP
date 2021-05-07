package sample;

import javafx.scene.paint.Paint;

public class Food implements Paintable{
    public int x, y;
    private final Paint fillColor = Paint.valueOf("#ff0000");
    private final Paint strokeColor = Paint.valueOf("#ffffff");
    public Food(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Paint getFillColor() {
        return fillColor;
    }

    @Override
    public Paint getStrokeColor() {
        return strokeColor;
    }
}
