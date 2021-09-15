package sample;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static Image flag = new Image("File:D:\\Pictures\\MonkaS.gif", true);
    public static Image mine = new Image("File:D:\\Pictures\\jebaited.png", true);
    public static Stage firstStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getFirstStage() {
        return firstStage;
    }

    public static void setFirstStage(Stage firstStage) {
        Main.firstStage = firstStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        firstStage = primaryStage;
        Menu.init();
        primaryStage.sizeToScene();
        primaryStage.show();

    }
}
//TODO: game options menu
