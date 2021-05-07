package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // конфигурация игры
        final int windowWidth = 500;
        final int windowHeight = 500;

        final int gridWidth = 15;
        final int gridHeight = 15;
        final long nsGameStepTime = 300000000L;


        Pane pane = new Pane();
        Scene scene = new Scene(pane, windowWidth, windowHeight);
        Painter painter = new Painter((float)windowWidth / gridWidth / 3 * 2);
        Grid grid = new Grid(gridWidth, gridHeight);


        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Food((int)(Math.random() * gridWidth), (int)(Math.random() * gridWidth)));

        Snake snake = new Snake(gridWidth, gridHeight, foods);

        // обработка клавиатуры средствами пакета
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch(key.getCode()) {
                case W -> snake.setDirection(Snake.DIRECTION.up);
                case S -> snake.setDirection(Snake.DIRECTION.down);
                case E -> snake.setDirection(Snake.DIRECTION.rightUp);
                case D -> snake.setDirection(Snake.DIRECTION.rightDown);
                case Q -> snake.setDirection(Snake.DIRECTION.leftUp);
                case A -> snake.setDirection(Snake.DIRECTION.leftDown);
            }
        });

        new AnimationTimer(){
            long lastUpdate;
            @Override
            public void handle(long arg0) {
                // пришло ли время нового фрейма?
                if(arg0 - lastUpdate > nsGameStepTime){
                    lastUpdate = arg0;

                    if(!snake.move()){
                        // если мы съели сами себя - закрываемся
                        Platform.exit();
                    }

                    // draw grid
                    grid.getGridCells().forEach(gridCell -> painter.paint(pane, gridCell));
                    // draw snake
                    snake.body.forEach(snakeCell -> painter.paint(pane, snakeCell));
                    // draw food
                    foods.forEach(food -> painter.paint(pane, food));
                }

            }
        }.start();


        primaryStage.setTitle("Strange Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
