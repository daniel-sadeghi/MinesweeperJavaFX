package com.dsadeghi.minesweeper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button startButton;

    @FXML
    private ChoiceBox difficultyChoiceBox;

    MineGrid mineGrid;

    @FXML
    public void initialize() {
        gridPane.setPrefSize(27,27);
        difficultyChoiceBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));

        //Start button logic
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Get the value of the choice box and initialize the mine grid based on the difficulty chosen
                String difficulty = difficultyChoiceBox.getValue().toString();
                switch (difficulty) {
                    case "Easy":
                        mineGrid = new MineGrid(35);
                        break;
                    case "Medium":
                        mineGrid = new MineGrid(45);
                        break;
                    case "Hard":
                        mineGrid = new MineGrid(55);
                        break;
                    default:
                        throw new InvalidChoiceException("No valid difficulty selection detected");
                }
                //Make grid available after starting
                gridPane.setDisable(false);
            }
        });

        //Create our grid of button objects, defining their behavior
        for (int i = 0; i < MineGrid.HEIGHT; i++) {
            for (int j = 0; j < MineGrid.WIDTH; j++) {

                //Create a new button
                Button button = new Button();
                button.setMinSize(25,25);
                button.setPadding(new Insets(0,0,0,0));
                button.setStyle("-fx-base: #8DC15E;");

                //Logic for handling left or right clicks on buttons
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        //Get the mouse button used to click the button
                        MouseButton mouseButton = mouseEvent.getButton();
                        Square square = mineGrid.getSquare(GridPane.getColumnIndex(button), GridPane.getRowIndex(button));

                        //If a left click was used, reveal the square if it is hidden
                        if (mouseButton == MouseButton.PRIMARY) {
                            if (square.isFlagged() || square.isRevealed()) {
                                return;
                            }

                            //Pass the coordinates of the button to reveal
                            mineGrid.reveal(square);

                            //Game over logic
                            if (square.isMine()) {
                                gridPane.setDisable(true);
                                welcomeText.setText("Game Over!");
                            } else if (square.getValue() == 0) {
                                clickAdjacentButtons(button, mouseEvent);
                            }
                            if (mineGrid.checkWin()) {
                                welcomeText.setText("You win!");
                                gridPane.setDisable(true);
                            }
                        }

                        //Logic for flagging a square
                        if (mouseButton == MouseButton.SECONDARY) {
                            if (square.isRevealed()) {
                                return;
                            }
                            square.setFlag(!square.isFlagged());
                        }

                        //Get the image for the button by passing in the value of its corresponding square
                        button.setGraphic(getSquareImageView(square));
                    }
                });

                //Add our button to the grid
                gridPane.add(button, j, i);
            }
        }
    }

    /**
     * Gets the corresponding image for the square's state
     * @return
     */
    public ImageView getSquareImageView(Square square) {
        if (square.isFlagged()) {
            return new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/flag.png"));
        }

        if (!square.isRevealed()) {
            return null;
        }

        if (square.isMine()) {
            return new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/mine.png"));
        }

        return switch (square.getValue()) {
            case 0 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_0.png"));
            case 1 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_1.png"));
            case 2 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_2.png"));
            case 3 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_3.png"));
            case 4 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_4.png"));
            case 5 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_5.png"));
            case 6 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_6.png"));
            case 7 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_7.png"));
            case 8 -> new ImageView(new Image("file:/Users/dsadeghi/IdeaProjects/Minesweeper/src/main/resources/com/dsadeghi/minesweeper/number_8.png"));
            default -> null;
        };


    }

    /**
     * This method takes in a button, finds all adjacent buttons in the grid, and "fires"
     * @param button the button in the center of all adjacent buttons
     * @param mouseEvent the "left click" mouse event
     */
    public void clickAdjacentButtons(Button button, MouseEvent mouseEvent) {
        int buttonX = GridPane.getColumnIndex(button);
        int buttonY = GridPane.getRowIndex(button);
        for (Node n : gridPane.getChildren()) {
            if ((Math.abs(buttonX - GridPane.getColumnIndex(n))) <= 1) {
                if ((Math.abs(buttonY - GridPane.getRowIndex(n)) <= 1)) {
                    Button adjacentButton = (Button)n;
                    adjacentButton.fireEvent(mouseEvent);
                }
            }
        }
    }
}