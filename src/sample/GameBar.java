package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameBar {
    public static Label timer = new Label();
    static Label mineCount = new Label();
    AnchorPane anchorPane = new AnchorPane();

    public GameBar(VBox root, int bombCount) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.2), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        anchorPane.setBackground(background);
        mineCount.setFont(Font.font("Impact", 15));
        mineCount.setText(Integer.toString(bombCount));
        mineCount.setTextFill(Color.YELLOW);
        AnchorPane.setLeftAnchor(mineCount, 5.0);
        AnchorPane.setTopAnchor(mineCount, 5.0);
        timer.setText("0:00");
        timer.setFont(Font.font("Impact", 15));
        timer.setTextFill(Color.YELLOW);
        AnchorPane.setRightAnchor(timer, 5.0);
        AnchorPane.setTopAnchor(timer, 5.0);
        AnchorPane.setBottomAnchor(timer, 3.0);
        anchorPane.getChildren().addAll(mineCount, timer);
    }

    //TODO: finish gamebar

    public static void setTimer(long currentTime) {
        timer.setText(currentTime / 60 + ":" + currentTime % 60);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
