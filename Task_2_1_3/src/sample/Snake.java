package sample;

import javafx.scene.paint.Paint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Snake {

    ArrayDeque<SnakeCell> body = new ArrayDeque<>();
    public DIRECTION direction = DIRECTION.down;
    private final int gridWidth, gridHeight;
    private final ArrayList<Food> foods;

    public Snake(int gridWidth, int gridHeight, @NotNull ArrayList<Food> foods){
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.foods = foods;
        body.add(new SnakeCell(gridWidth / 2, gridHeight / 2, gridHeight, gridWidth));
    }

    public boolean move(){
        // move head and delete tail
        body.addFirst(body.getFirst().moveCell(direction));

        // проверка на самопересечение
        SnakeCell head = body.getFirst();
        int counter = 0;
        for(var cell : body){
            if(cell.x == head.x && cell.y == head.y){
                counter++;
            }
        }
        if(counter > 1){
            return false;
        }

        // проверка на еду
        if(!foods.isEmpty() &&
                foods.get(0).x == body.getFirst().getX() &&
                foods.get(0).y == body.getFirst().getY()) {
            foods.remove(0);
            foods.add(new Food((int)(Math.random() * gridWidth), (int)(Math.random() * gridHeight)));
        }
        else{
            // если еды не было, удаляем хвост
            body.removeLast();
        }
        return true;
    }

    public enum DIRECTION{
        up,
        rightUp, rightDown,
        leftUp, leftDown,
        down
    }

    public void setDirection(@NotNull DIRECTION direction) {
        this.direction = direction;
    }

    public static class SnakeCell implements Paintable{
        private final Paint fillColor = Paint.valueOf("#00FF00");
        private final Paint strokeColor = Paint.valueOf("#000000");
        private final int gridWidth, gridHeight;
        private int x, y;
        public SnakeCell(int x, int y, int gridHeight, int gridWidth){
            this.x = x;
            this.y = y;
            this.gridHeight = gridHeight;
            this.gridWidth = gridWidth;
        }
        public SnakeCell moveCell(@NotNull DIRECTION direction){
            // движ с правильным перемещением головы
            // такая паста, потому что координаты работают немного сложнее декартовых
            /* сурс - https://www.redblobgames.com/grids/hexagons/ */
            SnakeCell movedCell = new SnakeCell(x, y, gridHeight, gridWidth);
            switch(direction){
                case down -> movedCell.y = (y+1) % gridHeight;
                case up -> movedCell.y = y == 0 ? gridHeight - 1 : y - 1;
                case leftUp -> {
                    if(x % 2 == 1){
                        movedCell.y = y == 0 ? gridHeight - 1 : y - 1;
                    }
                    movedCell.x = x == 0 ? gridWidth - 1 : x - 1;
                }
                case rightDown -> {
                    if(x % 2 == 0){
                        movedCell.y = (y+1) % gridHeight;
                    }
                    movedCell.x = (x+1) % gridHeight;
                }
                case rightUp -> {
                    if(x % 2 == 1){
                        movedCell.y = y == 0 ? gridHeight - 1 : y - 1;
                    }
                    movedCell.x = (x+1) % gridHeight;
                }
                case leftDown -> {
                    if(x % 2 == 0){
                        movedCell.y = (y+1) % gridHeight;
                    }
                    movedCell.x = x == 0 ? gridWidth - 1 : x - 1;
                }
            }
            return movedCell;
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
}
