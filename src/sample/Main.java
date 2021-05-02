package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    VBox root = new VBox();
    public Scene scene = new Scene(root, Color.RED);

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        final int numOfRows = 25, numOfColumns = 20;
        primaryStage.setResizable(true);
        primaryStage.setWidth(numOfColumns * 20 + numOfColumns - 4);
        primaryStage.setHeight(numOfRows * 20 + numOfRows + 14);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);

        primaryStage.show();
//        root.getChildren().add(GameBar.settings);

        primaryStage.show();

    }
}
//TODO: game options menu


