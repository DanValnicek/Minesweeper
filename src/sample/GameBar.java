package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameBar {
    static Label mineCount = new Label();

    public GameBar(VBox root, int bombCount) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.gray(0.2), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(background);
        root.getChildren().add(anchorPane);
        mineCount.setText(Integer.toString(bombCount));
        mineCount.setTextFill(Color.YELLOW);
        AnchorPane.setLeftAnchor(mineCount, 10.0);
        AnchorPane.setTopAnchor(mineCount, 5.0);
        anchorPane.getChildren().add(mineCount);
    }

//TODO: finish gamebar
}
