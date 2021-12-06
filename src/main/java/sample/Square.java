package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Arrays;

import static sample.GameSettings.game;


public class Square {

	ImageView flag = new ImageView(Main.flag);
	ImageView mine = new ImageView(Main.mine);
	boolean marked = false;
	boolean popped = false;
	int value;
	Label tileLabel;
	Rectangle rectangle;


	public Square(int value) {
		flag.setFitHeight(20);
		flag.setFitWidth(20);
		mine.setFitHeight(20);
		mine.setFitWidth(20);
		flag.setMouseTransparent(true);

		this.value = value;
	}


	public void setPopped(int x, int y) {

		this.popped = true;
		if (!this.marked) {
			if (value == 9) {
				game.getGridPane().add(generateText(this.value), x, y);
				if (Game.isRunning) {
					rectangle.setFill(Color.RED);
					Game.isRunning = false;
					showAllMines(game.getSquares(), true);
					Game.gameOver();
				}
				if (!rectangle.getFill().equals(Color.RED)) {
					game.getGridPane().getChildren().remove(this.rectangle);
				}
			} else if (rectangle != null) {
				this.rectangle.setFill(Color.gray(0.4));
				game.emptySquares--;
				if (value != 0) {
					game.getGridPane().add(generateText(this.value), x, y);
				}
				this.rectangle = null;
				if (value == 0) {
					this.popNeighbours(y, x);
					if (this.rectangle != null) {
						this.rectangle.setOnMouseClicked(null);
						game.getGridPane().getChildren().remove(this.rectangle);
					}
				}
			}
		}
	}


	private int getNumOfMarked(int x, int y) {
		int numOfMarked = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x + j < game.getSquares()[y].length && x + j > -1 && y + i < game.getSquares().length && y + i > -1 && game.getSquares()[y + i][x + j].marked) {
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

		rectangle.setFill(Color.TOMATO);
		rectangle.fillProperty();
		rectangle.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
						if (!Game.isRunning && value == 0) {
							game.startGame();
						} else if (!Game.isRunning) {
							System.out.println("x:" + x + "y:" + y);
							game.reGenerateSquares(x, y);
							game.startGame();
						}

						if (!popped && !marked) {
							if (value == 9) {
								rectangle.setFill(Color.RED);
								showAllMines(Game.squares, true);
								Game.gameOver();
							} else {
								this.setPopped(x, y);
							}
						} else if (popped) {
							int numOfMarked = this.getNumOfMarked(x, y);
							if (value - numOfMarked == 0) {
								this.popNeighbours(y, x);
							}
						}
					} else if (event.getButton() == MouseButton.SECONDARY && !game.getSquares()[y][x].popped) {
						if (!game.getGridPane().getChildren().contains(flag) && Game.numOfMines - game.numOfMarked > 0) {
							game.getGridPane().add(flag, x, y);
							marked = true;
							game.setNumOfMarked(true);
						} else if (game.getGridPane().getChildren().contains(flag)) {
							game.getGridPane().getChildren().remove(flag);
							Launcher.getFirstStage().show();
							marked = false;
							game.setNumOfMarked(false);

						}
					}
					if (game.emptySquares == 0) {
						Game.gameOver();
					}
					event.consume();

				}
		);
	}

	private void popNeighbours(int y, int x) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (game.getSquares() != null && y + i < game.getSquares().length && y + i > -1 && x + j < game.getSquares()[y].length && x + j > -1 && !game.getSquares()[y + i][x + j].marked) {
					game.getSquares()[y + i][x + j].setPopped(x + j, y + i);
				}
			}
		}

	}

	private Label generateText(int value) {
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
		return tileLabel;
	}

	public void showAllMines(Square[][] squares, boolean show) {
		for (Square[] row : squares) {
			for (Square mine : row) {
				if (show && mine.value == 9) {
					mine.setPopped(Arrays.asList(row).indexOf(mine), Arrays.asList(squares).indexOf(row));
					Launcher.getFirstStage().show();
				}
			}
		}
		//Delete all event handlers
		game.getGridPane().getChildren().forEach((square) -> {
			square.setOnMouseClicked(null);
		});
	}

}
