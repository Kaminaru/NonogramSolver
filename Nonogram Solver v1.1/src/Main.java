import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application{
    // precoded variables. I have to add args later
    private static int NUM_ROWS =  0;
    private static int NUM_COLS = 0;
    private static String[][] board = null;
    // private static Integer[][] rows = new ArrayList();
    private static final Integer[][] rows = {{6,2},{4,2},{3,2},{3,1},{2,1},{1,4},{5},{3},{3,2},{5,1}};
    private static final Integer[][] columns = {{1,1},{1,1},{2,2,1},{5,1},{4,2},{3,1,2},{2,1},{3},{3,5},{4,5}};

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Nonogram Solver");
//        stage.setWidth(500);
//        stage.setHeight(500);

        Pane gridPaneMiddle = makeGridPaneMiddle();
        Pane gridPaneLeft = makeGridPaneLeft();
        HBox hbox = new HBox(gridPaneLeft, gridPaneMiddle);

        Scene scene = new Scene(hbox, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private Pane makeGridPaneLeft(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        for(int i = 0; i < NUM_ROWS; i++){
            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-background-color: transparent;");
            plusButton.setOnAction(e->{
                System.out.println("COOL beans");
            });
            plusButton.setPrefWidth(25);
            plusButton.setPrefHeight(25);
            gridPane.add(plusButton, 0, i, 1, 1);


            TextField textField = new TextField();
            textField.setPrefWidth(30);
            textField.setPrefHeight(30);
            gridPane.add(textField, 1, i, 1, 1);
        }

        return gridPane;
    }


    private Pane makeGridPaneMiddle(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        for(int i = 0; i < NUM_ROWS; i++) {
            for(int j = 0; j < NUM_COLS; j++){
                Button button = new Button();
                button.setOnMouseClicked(e->{
                    if(e.getButton() == MouseButton.SECONDARY){ // right click
                        rightButtonClick(button);
                    }else{ // left click
                        leftButtonClick(button);
                    }
                });
                button.setPrefWidth(30);
                button.setPrefHeight(30);
                button.getProperties().put("TYPE"," "); // For empty button
                gridPane.add(button, i, j, 1, 1);
            }
        }
        return gridPane;
    }

    private void leftButtonClick(Button button){
        if(button.getProperties().get("TYPE").equals("#")){
            // reset button
            button.setGraphic(null);
            button.getProperties().put("TYPE"," "); //  For empty button
        }else{
            Image imageB = new Image("bSquare.png");
            ImageView imageViewB = new ImageView(imageB);
            imageViewB.setFitHeight(13);
            imageViewB.setPreserveRatio(true);
            button.setGraphic(imageViewB);
            button.getProperties().put("TYPE","#"); //  For # button
        }
    }

    private void rightButtonClick(Button button) {
        if(button.getProperties().get("TYPE").equals("X")){
            // reset button
            button.setGraphic(null);
            button.getProperties().put("TYPE"," "); //  For empty button
        }else{
            Image imageX = new Image("rX.png");
            ImageView imageViewX = new ImageView(imageX);
            imageViewX.setFitHeight(13);
            imageViewX.setPreserveRatio(true);
            button.setGraphic(imageViewX);
            button.getProperties().put("TYPE","X"); //  For X button
        }
    }

    private static void setUpBoard(){
        board = new String[NUM_ROWS][NUM_COLS]; // # - solved   ' ' - (empty) nothing
        for (String[] strings : board) {
            Arrays.fill(strings, " ");
        }
    }

    private static boolean checkRow(int i, int j){
        int blackBlockNum = 0; // number for the black block that I can look up in rows array
        int countedNumber = 0;
        boolean newBlock = false; // shows when we found new black block
        for(int iter = 0; iter < j+1; iter++) {
            if(board[i][iter].equals("#")){
                newBlock = true;
                countedNumber++;
                if(blackBlockNum >= rows[i].length){ // if to many blocks of black
                    return false;
                }
                if(countedNumber > rows[i][blackBlockNum]) { // not enough black in the block
                    return false;
                }
            }else{
                if(newBlock){
                    blackBlockNum++;
                    newBlock = false;
                    countedNumber = 0;
                }
            }
        }
        return true;
    }



    private static boolean checkColumn(int i, int j){
        int blackBlockNum = 0; // number for the black block that I can look up in columns array
        int countedNumber = 0;
        boolean newBlock = false; // shows when we found new black block
        for(int iter = 0; iter < i+1; iter++){
            if(board[iter][j].equals("#")){
                newBlock = true;
                countedNumber++;
                if(blackBlockNum >= columns[j].length){
                    return false;
                }
                if(countedNumber > columns[j][blackBlockNum]){ // not enough black in the block
                    return false;
                }
            }else{
                if(newBlock){
                    blackBlockNum ++;
                    newBlock = false;
                    countedNumber = 0;
                }
            }
        }
        return true;
    }

    private static boolean checkIfRight(int i, int j){
        // Goes through column of given i and j and also row, and check if
        // we have set black color in the right position
        if(!checkRow(i,j)){return false;}
        if(!checkColumn(i,j)){return false;}
        
        if(j == board[i].length-1){
            int numberOfBlacks = 0; // number of black we need
            for(int num : rows[i]){
                numberOfBlacks = numberOfBlacks + num;
            }
            int blackOnRowCount = 0; // number of black we have
            for(String symbol: board[i]){
                if(symbol.equals("#")){
                    blackOnRowCount++;
                }
            }

            if(numberOfBlacks > blackOnRowCount) {
                return false;
            }
        }
        return true;
    }


    private static boolean findSolution(int i, int j){
        if(i == board.length) return true; // at the last row
        // Finds next i and j
        int nextI = i; // index for row
        int nextJ = j; // index for column
        // Does nothing when i and j on the last positions (lower/right corner)
        if(j == board[0].length-1){
            nextI ++;
            nextJ = 0;
        }else{
            nextJ ++;
        }
        board[i][j] = "#"; // marks i and j position on board as black
        // Checks if this is good decision, recursively
        if(checkIfRight(i,j) && findSolution(nextI, nextJ)){
            return true;
        }

        board[i][j] = " "; // marks i and j position on board as white (possible to change to X later)
        // Checks if this is a good decision, recursively (most likely is, if there is solution for this Nonogram)
        return checkIfRight(i, j) && findSolution(nextI, nextJ);// if False: no possible solution for "this path/recursion branch"
    }


    public static void main(String[] args) {
        // This time I am trying recursion solution with backtracking
        // (It will be slower than old OOP version, but it will solve almost any nonogram by brute force)
        NUM_ROWS = 10;
        NUM_COLS = 10;

        setUpBoard();
        findSolution(0,0); // starts from upper left corner
        for (String[] rowArr : board) {
            for (String el : rowArr) {
                System.out.print("|" + el);
            }
            System.out.println("|");
        }

        launch();



        System.exit(0);
    }
}
