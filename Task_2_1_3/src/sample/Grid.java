package sample;

import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class Grid {
    private final ArrayList<GridCell> gridCells = new ArrayList<>();

    /**
     * Простой конструктор
     * @param width ширина поля в клетках
     * @param height высота поля в клетках
     */
    public Grid(int width, int height){
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                gridCells.add(new GridCell(i, j, Paint.valueOf("#00ffff"),Paint.valueOf("#000000")));
            }
        }
    }

    public ArrayList<GridCell> getGridCells() {
        return gridCells;
    }

    public static class GridCell implements Paintable{
        public GridCell(int xIdx, int yIdx, Paint fillColor, Paint strokeColor){
            this.xIdx = xIdx;
            this.yIdx = yIdx;
            this.fillColor = fillColor;
            this.strokeColor = strokeColor;
        }
        private final int xIdx;
        private final int yIdx;
        Paint fillColor, strokeColor;

        @Override
        public int getX() {
            return xIdx;
        }

        @Override
        public int getY() {
            return yIdx;
        }

        @Override
        public Paint getFillColor() {
            return fillColor;
        }
        public Paint getStrokeColor() {
            return strokeColor;
        }

    }
}
