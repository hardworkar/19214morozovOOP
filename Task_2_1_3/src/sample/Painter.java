package sample;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

public class Painter {

    private final double cellSize;
    public Painter(double cellSize){
        this.cellSize = cellSize;
    }

    /**
     * не самая красивая функция, но тут чистая математика с расчетом нужных позиций точек
     * я не знаю, зачем я сделал змейку в шестиугольниках
     * рисует шестиугольник для объекта obj
     * @param obj должен выдать индексовые координаты и два цвета
     */
    public void paint(@NotNull Pane pane, @NotNull Paintable obj) {

        double size = cellSize, v = Math.sqrt(3)/2.0;

        double dy = -size + obj.getY() * (size * Math.sqrt(3));
        if(obj.getX() % 2 == 0){
            dy += size*v;
        }
        double x = -size/2 + obj.getX() * ((3.0/2.0) * size);
        Polygon tile = new Polygon();
        tile.getPoints().addAll(
                x,dy,
                x+size,dy,
                x+size*(3.0/2.0),dy+size*v,
                x+size,dy+size*Math.sqrt(3),
                x,dy+size*Math.sqrt(3),
                x-(size/2.0),dy+size*v);

        tile.setFill(obj.getFillColor());
        tile.setStrokeWidth(2);
        tile.setStroke(obj.getStrokeColor());
        pane.getChildren().add(tile);
    }
}
