package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static Game game;
    public static Image flag = new Image("File:D:\\Pictures\\MonkaS.gif", true);
    public static Image mine = new Image("File:D:\\Pictures\\jebaited.png", true);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent menu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        Scene menuScene = new Scene(menu);
        String css = this.getClass().getResource("style.css").toExternalForm();
        menuScene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Minesweeper");
        game = new Game(50, 40, 50);
        primaryStage.setScene(menuScene);
//        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

    }
}
//TODO: game options menu
