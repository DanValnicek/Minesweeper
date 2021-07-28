package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Arrays;


public class Square {

    ImageView flag = new ImageView(Main.flag);
    ImageView mine = new ImageView(Main.mine);
    //    Image[] images = new Image[9];
    boolean marked = false;
    boolean popped = false;
    int value;
    Label tileLabel;
    Rectangle rectangle;
    //    Rectangle transparentRectangle;


    public Square(int value) {
        flag.setFitHeight(20);
        flag.setFitWidth(20);
        mine.setFitHeight(20);
        mine.setFitWidth(20);

        flag.setMouseTransparent(true);

        this.value = value;
    }

    public void setPopped(boolean popped, int x, int y) {
        this.popped = popped;
        if (!this.marked) {
            if (value == 9) {
                if (!rectangle.getFill().equals(Color.RED)) {
                    rectangle.setFill(Color.gray(0.4));
                }
                Main.game.getGridPane().add(this.tileLabel, x, y);
                Main.game.timeline.stop();

            } else if (!rectangle.getFill().equals(Color.gray(0.4))) {
                this.rectangle.setFill(Color.gray(0.4));
                if (value != 0) {
                    Main.game.getGridPane().add(this.tileLabel, x, y);
                }
                if (value == 0) {
                    popNeighbours(y, x);
                }
            }
        }
    }

    private int getNumOfMarked(int x, int y) {
        int numOfMarked = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + j < Main.game.getSquares()[y].length && x + j > -1 && y + i < Main.game.getSquares().length && y + i > -1 && Main.game.getSquares()[y + i][x + j].marked) {
                    numOfMarked++;
                }
            }
        }
        return numOfMarked;
    }

    public void generateRectangle(int x, int y) {
        rectangle = new Rectangle(0, 0, 25, 25);
        rectangle.arcHeightProperty().set(4.5d);
        rectangle.arcWidthProperty().set(4.5d);

//        rectangle.setPickOnBounds(true);
//        System.out.println(rectangle.getX());
        rectangle.setFill(Color.rgb(0, 128, 255));
        rectangle.fillProperty();
        generateText(value);


//        text.setTextFill(Color.gray(0.5));
//        transparentRectangle = new Rectangle(0, 0, 20, 20);
//        transparentRectangle.setOpacity(0.0);
        rectangle.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (Main.game.startTime == 0) {
                            Main.game.startGame();
                        }

                        if (!popped && !marked) {
                            if (value == 9) {
                                rectangle.setFill(Color.RED);
                                showAllMines(Main.game.squares, Main.game.numOfRows, Main.game.numOfColumns);
                            } else {
                                Square.this.setPopped(true, x, y);
                            }
                        } else if (popped) {
                            int numOfMarked = Square.this.getNumOfMarked(x, y);
                            if (value - numOfMarked == 0) {
                                Square.this.popNeighbours(y, x);
                                rectangle.removeEventHandler(MouseEvent.MOUSE_CLICKED, rectangle.getOnMouseClicked());
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY && !Main.game.getSquares()[y][x].popped) {
                        if (!Main.game.getGridPane().getChildren().contains(flag) && Main.game.numOfMines - Main.game.numOfMarked > 0) {
                            Main.game.getGridPane().add(flag, x, y);
                            marked = true;
                            Main.game.setNumOfMarked(true);
                        } else if (Main.game.getGridPane().getChildren().contains(flag)) {
                            Main.game.getGridPane().getChildren().remove(flag);
                            Main.game.stage.show();
                            marked = false;
                            Main.game.setNumOfMarked(false);

                        }
                    }
                    event.consume();
                }
        );
    }

    private void popNeighbours(int y, int x) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + j < Main.game.getSquares()[y].length && x + j > -1 && y + i < Main.game.getSquares().length && y + i > -1 && !Main.game.getSquares()[y + i][x + j].marked) {
                    Main.game.getSquares()[y + i][x + j].setPopped(true, x + j, y + i);
                }
            }
        }
    }

    private void generateText(int value) {
        tileLabel = new Label();
        GridPane.setHalignment(tileLabel, HPos.CENTER);
        Color[] colors = {null, Color.BLUE, Color.DARKGREEN, Color.RED, Color.DARKBLUE, Color.BROWN, Color.CYAN, Color.BLACK, Color.GREY};
        tileLabel.setMouseTransparent(true);
        if (value == 9) {
            tileLabel.setGraphic(mine);
        } else {
            tileLabel.setText(Integer.toString(value));
            tileLabel.setTextFill(colors[value]);
            tileLabel.setFont(Font.font("Arial Black", 13));

        }
    }

    public void showAllMines(Square[][] squares, int numOfRows, int numOfColumns) {

        for (Square row[] : squares) {
            for (Square mine : row) {
                if (mine.value == 9) {
                    mine.setPopped(true, Arrays.asList(row).indexOf(mine), Arrays.asList(squares).indexOf(row));
                }
            }
        }
    }
}
