package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Square {
    static Square[][] squares;
    ImageView flag = new ImageView(new Image("File:D:\\Pictures\\MonkaS.gif", true));
    ImageView mine = new ImageView(new Image("File:D:\\Pictures\\jebaited.png", true));

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

    public static void generateSquares(Stage primaryStage, VBox root, int numOfColumns, int numOfRows, int[][] map) {
        GridPane gridPane = new GridPane();
        root.getChildren().add(gridPane);
        Square.squares = new Square[numOfRows][numOfColumns];
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                squares[i][j] = new Square(map[i][j]);
                squares[i][j].generateRectangle(primaryStage, gridPane, i, j);
                gridPane.add(squares[i][j].rectangle, j, i);
            }
        }
    }

    public void setPopped(boolean popped, GridPane gridPane, int x, int y) {
        if (popped && rectangle.getFill() != Color.DARKGRAY) {
            this.rectangle.setFill(Color.DARKGRAY);
            if (value != 0) {
                gridPane.add(this.tileLabel, x, y);
                if (value == 9) System.out.println("boom");
            }

            System.out.println(value);
            if (value == 0) {
                popNeighbours(gridPane, y, x);
            }
        }
        this.popped = popped;
    }

    private int getNumOfMarked(int x, int y) {
        int numOfMarked = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + j < squares[y].length && x + j > -1 && y + i < squares.length && y + i > -1 && squares[y + i][x + j].marked) {
                    numOfMarked++;
                }
            }
        }
        return numOfMarked;
    }

    public void generateRectangle(Stage primaryStage, GridPane gridPane, int y, int x) {
        rectangle = new Rectangle(0, 0, 20, 20);
        rectangle.arcHeightProperty().set(9.0d);
        rectangle.arcWidthProperty().set(9.0d);

//        rectangle.setPickOnBounds(true);
//        System.out.println(rectangle.getX());
        rectangle.setFill(Color.gray(0.5));
        rectangle.fillProperty();
        generateText(value);


//        text.setTextFill(Color.gray(0.5));
//        transparentRectangle = new Rectangle(0, 0, 20, 20);
//        transparentRectangle.setOpacity(0.0);
        rectangle.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (!popped && !marked) {
                            setPopped(true, gridPane, x, y);
                        } else if (popped) {
                            int numOfMarked = getNumOfMarked(x, y);
                            if (value - numOfMarked == 0) {
                                popNeighbours(gridPane, y, x);
                            }
                        }
                    } else if (event.getButton() == MouseButton.SECONDARY && !squares[y][x].popped) {
                        if (!gridPane.getChildren().contains(flag)) {
                            gridPane.add(flag, x, y);
                            marked = true;

                        } else {
                            gridPane.getChildren().remove(flag);
                            primaryStage.show();
                            marked = false;
                        }
                    }
                }
        );
    }

    private void popNeighbours(GridPane root, int y, int x) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + j < squares[y].length && x + j > -1 && y + i < squares.length && y + i > -1 && !squares[y + i][x + j].marked) {
                    squares[y + i][x + j].setPopped(true, root, x + j, y + i);
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
}
