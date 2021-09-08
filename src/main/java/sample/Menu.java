package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Menu {
    public void playOnClickEvent(MouseEvent event) {
        Stage game = (Stage) ((Node)event.getSource()).getScene().getWindow();
        game.setScene(Game.getScene());
    }


}
