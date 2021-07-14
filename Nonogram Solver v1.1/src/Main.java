import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application{
    private static int NUM_ROWS =  0;
    private static int NUM_COLS = 0;
    private static String[][] board = null;
    private static List<List<Integer>> rows = new ArrayList<List<Integer>>();
    private static List<List<Integer>> columns = new ArrayList<List<Integer>>();
    private static GridPane gridPaneMiddle = null;
    private static GridPane gridPaneLeft = null;
    private static GridPane gridPaneAbove = null;
    private static Text errorTextField = null;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Nonogram Solver");
//        stage.setWidth(1000);
//        stage.setHeight(1000);

        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.CENTER);

        gridPaneMiddle = new GridPane();
        gridPaneMiddle.setPadding(new Insets(5));
        changeGridPaneMiddle();

        gridPaneLeft = new GridPane();
        gridPaneLeft.setPadding(new Insets(5));
        changeGridPaneLeft();

        gridPaneAbove = new GridPane();
        gridPaneAbove.setPadding(new Insets(5));
        changeGridPaneAbove();

        HBox lowerButtons = makeLowerButtons(stage);
        HBox boardSizeSettings = makeBoardSettings();


        mainGrid.add(boardSizeSettings, 1, 0);
        mainGrid.add(gridPaneAbove, 1, 1);
        mainGrid.add(gridPaneMiddle, 1, 2);
        mainGrid.add(gridPaneLeft, 0, 2);
        mainGrid.add(lowerButtons, 1, 3);

        errorTextField = new Text("");
        mainGrid.add(errorTextField, 1, 4);

        Scene scene = new Scene(mainGrid, 800, 700);
        stage.setScene(scene);
        stage.show();
    }

    private HBox makeBoardSettings(){
        HBox currHBox = new HBox(1);
        Text t1 = new Text("Rows:");
        Text t2 = new Text("Columns:");
        TextField textField1 = new TextField();
        textField1.setPrefWidth(50);
        TextField textField2 = new TextField();
        textField2.setPrefWidth(50);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e->{changeBoardSize(textField1, textField2);});

        currHBox.getChildren().addAll(t1, textField1, t2, textField2, submitButton);
        return currHBox;
    }

    private void changeBoardSize(TextField textField1, TextField textField2){
        try {
            int numRow = Integer.parseInt(textField1.getText());
            int numCol = Integer.parseInt(textField2.getText());
            NUM_ROWS = numRow; // changes global variables to new row value
            NUM_COLS = numCol;
            resetBoard();
        }
        catch(Exception e) {
            errorTextField.setText("Wrong row or column input!");
        }
        textField1.setText("");
        textField2.setText("");
    }

    private HBox makeLowerButtons(Stage stage){
        HBox hboxButtons = new HBox(1); // spacing 1
        Button solveButton = new Button("Solve");
        solveButton.setStyle("-fx-background-color: #1fad26");
        solveButton.setOnAction(e->{solveFromUser();});

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #e52f1a");
        resetButton.setOnAction(e->{resetBoard();});

        Button checkIfRight = new Button("Check my solution");
        checkIfRight.setStyle("-fx-background-color: #d1f2c6");
        checkIfRight.setOnAction(e->{checkUserSolution();});

        Button readFromFileButton = new Button("Import from file");
        readFromFileButton.setStyle("-fx-background-color: #8a908a");
        readFromFileButton.setOnAction(e->{importFromFile(stage);});
        hboxButtons.getChildren().addAll(solveButton, resetButton, checkIfRight, readFromFileButton);
        return hboxButtons;
    }


    private void changeGridPaneLeft(){
        gridPaneLeft.getChildren().clear(); // clears the board, (in case there is old board)
        for(int i = 0; i < NUM_ROWS; i++){
            HBox hbox = new HBox();
            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-background-color: transparent;");
            plusButton.getProperties().put("TYPE",Integer.toString(i)); // saves the index of the row
            plusButton.setOnAction(e->{
                addNewNumberBox(plusButton, gridPaneLeft,1);
            });
            Button minusButton = new Button("-");
            minusButton.setStyle("-fx-background-color: transparent;");
            minusButton.getProperties().put("TYPE",Integer.toString(i)); // saves the index of the row
            minusButton.setOnAction(e->{
                deleteNewNumberBox(minusButton, gridPaneLeft,1);
            });
            minusButton.setPrefWidth(25);
            minusButton.setPrefHeight(25);
            hbox.getChildren().addAll(minusButton, plusButton, createTextField());
            gridPaneLeft.add(hbox, 0, i);
        }
    }

    private void changeGridPaneAbove(){
        gridPaneAbove.getChildren().clear(); // clears the board, (in case there is old board)
        for(int i = 0; i < NUM_COLS; i++){
            VBox vbox = new VBox();
            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-background-color: transparent;");
            plusButton.getProperties().put("TYPE",Integer.toString(i)); // saves the index of the row
            plusButton.setOnAction(e->{
                addNewNumberBox(plusButton, gridPaneAbove, 2);
            });
            plusButton.setPrefWidth(25);
            plusButton.setPrefHeight(25);

            Button minusButton = new Button("-");
            minusButton.setStyle("-fx-background-color: transparent;");
            minusButton.getProperties().put("TYPE",Integer.toString(i)); // saves the index of the row
            minusButton.setOnAction(e->{
                deleteNewNumberBox(minusButton, gridPaneAbove,2);
            });
            minusButton.setPrefWidth(25);
            minusButton.setPrefHeight(25);

            vbox.getChildren().addAll(minusButton, plusButton, createTextField());
            gridPaneAbove.add(vbox, i, 0);
        }
    }

    private void changeGridPaneMiddle(){
        gridPaneMiddle.getChildren().clear(); // clears the board, (in case there is old board)
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
                gridPaneMiddle.add(button, j, i, 1, 1);
            }
        }
    }


    public void printOutSolution(){
        changeGridPaneMiddle(); // reset mid pane
        int i = 0; // row number
        int j = 0; // column number
        for(Node node : gridPaneMiddle.getChildren()){
            Button buttonBox = (Button) node;
            if(board[i][j].equals("#")){
                leftButtonClick(buttonBox);
            }else{
                rightButtonClick(buttonBox);
            }
            j++;
            if(j == NUM_ROWS){ // at the edge need to go to the next row
                j = 0; // comes back to first column
                i++; // goes to next row
                // Don't need to check if we are on the max row, cuz gridPaneMiddle have no more children than
                // elements in the board list
            }
        }
    }

    private void numbersFromLeftAboveToLists(){
        // reads gridPaneLeft and gridPaneAbove and saves values in the rows and coluns lists
        for(int i = 0; i < NUM_ROWS; i++){
            ObservableList<Node> childrens = gridPaneLeft.getChildren();
            HBox hbox = (HBox) childrens.get(i);
            for(Node hboxNode : hbox.getChildren()){ // skip 0 because it is the '+' button
                if(hboxNode.getClass() == TextField.class){ // !!!!!!!! important to remember this comparison
                    TextField textF = (TextField) hboxNode;
                    // I don't check if textField is empty, because if it is empty I want to get exception
                    // So all textFields must be used by user.
                    int data = Integer.parseInt(textF.getText());
                    rows.get(i).add(data);
                }
            }
        }

        for(int i = 0; i < NUM_COLS; i++){
            ObservableList<Node> childrens = gridPaneAbove.getChildren();
            VBox vbox = (VBox) childrens.get(i);
            for(Node vboxNode : vbox.getChildren()){ // skip 0 because it is the '+' button
                if(vboxNode.getClass() == TextField.class){ // !!!!!!!! important to remember this comparison
                    TextField textF = (TextField) vboxNode;
                    int data = Integer.parseInt(textF.getText());
                    columns.get(i).add(data);
                }
            }
        }
    }

    private void solveFromUser(){
        // will also do small test while going throw all rows and columns, to check
        // if given input is fine to use.
        try {
            setUpBoard();
            numbersFromLeftAboveToLists();
            findSolution(0,0); // starts from upper left corner
            //printing solution in terminal too, for testing
            for (String[] rowArr : board) {
                for (String el : rowArr) {
                    System.out.print("|" + el);
                }
                System.out.println("|");
            }
            printOutSolution(); // print solution on board
        } catch(Exception e) {
            errorTextField.setText("There is no solution or there is a problem with given numbers");
        }
    }

    private void resetBoard(){
        errorTextField.setText(" ");
        changeGridPaneMiddle();
        changeGridPaneLeft();
        changeGridPaneAbove();
    }

    private void checkUserSolution(){
        // copy numbers from above and left pane to rows and columns list
        setUpBoard();
        numbersFromLeftAboveToLists();

        // Getting own solution
        try{
            findSolution(0,0);
        }catch(Exception e){
            errorTextField.setText("Something went wrong. Please make sure that all numbers are present");
        }
        int i = 0; // row number
        int j = 0; // column number
        for(Node node : gridPaneMiddle.getChildren()){
            Button buttonBox = (Button) node;
            // check if board[i][j] is the same as button type (#,X)
            // However I also need to consider that user won't use X on all places
            // so X on grid can be both X or empty space on board
            if(board[i][j].equals("#")){
                // button on grid must also have # else send error
                if(!buttonBox.getProperties().get("TYPE").equals("#")){
                    errorTextField.setText("Wrong user solution");
                    return;
                }
            }else{ // if something X or empty in solution, and # in user solution
                if(buttonBox.getProperties().get("TYPE").equals("#")){
                    errorTextField.setText("Wrong user solution");
                    return;
                }
            }
            j++;
            if(j == NUM_ROWS){ // at the edge need to go to the next row
                j = 0; // comes back to first column
                i++; // goes to next row
            }
        }
        errorTextField.setText("User solution is right!");
    }

    private void importFromFile(Stage stage){
        // Decided that it won't solve it immediately, but just add numbers from file
        // So user can try to solve it. Or else can just use "Solve" button.
        try{
            File file = new FileChooser().showOpenDialog(stage);
            Scanner scanner = new Scanner(file);

            String[] firstLine = scanner.nextLine().split(" ");
            int numRow = Integer.parseInt(firstLine[0]);
            int numCol = Integer.parseInt(firstLine[1]);
            NUM_ROWS = numRow; // changes global variables to new row value
            NUM_COLS = numCol; // changes global variables to new column value
            resetBoard();

            // file will work with both rows numbers first or column numbers first.
            // So file must look something like that:
            // 3 3                ~Numbers of rows and columns~
            // row                ~Tells what kind of numbers will go next, can be both column or row
            // 1                  ~Number row 0~
            // 0                  ~Number row 1~
            // 1 1                ~Number row 2~
            // column
            // 0
            // 0
            // 2
            int row_or_column = 0; // 1 for row, 2 for column
            int rowColNumber = 0; // index of needed VBox or HBox in left or above GridPane
            while(scanner.hasNextLine()){
                String[] numbers = scanner.nextLine().split(" ");
                if(numbers[0].equals("row")){
                    row_or_column = 1;
                    rowColNumber = 0; // resets it to 0
                }else if(numbers[0].equals("column")){
                    row_or_column = 2;
                    rowColNumber = 0; // resets it to 0
                }else if(row_or_column != 0){ // we have numbers
                    if(row_or_column == 1){
                        HBox rowHbox = (HBox) gridPaneLeft.getChildren().get(rowColNumber);
                        // adds needed number of textFields for numbers (1 because there is one textfield from the start)
                        for(int k = 1; k < numbers.length; k++){
                            rowHbox.getChildren().add(createTextField());
                        }

                        // put all numbers in needed places
                        for(int k = 0; k < numbers.length; k++){
                            TextField textField = (TextField) rowHbox.getChildren().get(k+2);
                            textField.setText(numbers[k]);
                        }
                        rowColNumber++;
                    }else if(row_or_column == 2){
                        VBox columnVbox = (VBox) gridPaneAbove.getChildren().get(rowColNumber);
                        // adds needed number of textFields for numbers (1 because there is one textfield from the start)
                        System.out.println(numbers.length);
                        for(int k = 1; k < numbers.length; k++){
                            columnVbox.getChildren().add(createTextField());
                        }

                        // put all numbers in needed places
                        for(int k = 0; k < numbers.length; k++){
                            TextField textField = (TextField) columnVbox.getChildren().get(k+2);
                            textField.setText(numbers[k]);
                        }
                        rowColNumber++;
                    }
                }else{
                    errorTextField.setText("Wrong file type!");
                }
            }


        }catch(Exception e){
            errorTextField.setText("Something went wrong while reading a file!");
        }
    }

    private TextField createTextField(){
        TextField textField = new TextField("");
        textField.setPrefWidth(30);
        textField.setPrefHeight(30);
        return textField;
    }

    private void addNewNumberBox(Button button, GridPane gridPane, int side){ // side: 1 left, 2 above
        int index = Integer.parseInt(String.valueOf(button.getProperties().get("TYPE")));

        ObservableList<Node> childrens = gridPane.getChildren();
        if(side == 1){
            for(Node node : childrens){
                if(GridPane.getRowIndex(node) == index){
                    HBox hbox = (HBox) node;
                    hbox.getChildren().add(createTextField()); // sets new box at the end from right side
                }
            }
        }else if(side == 2){
            for(Node node : childrens){
                if(GridPane.getColumnIndex(node) == index){
                    VBox vbox = (VBox) node;
                    vbox.getChildren().add(createTextField()); // sets new box at the end, under.
                }
            }
        }
    }

    private void deleteNewNumberBox(Button button, GridPane gridPane, int side) { // side: 1 left, 2 above
        int index = Integer.parseInt(String.valueOf(button.getProperties().get("TYPE")));

        ObservableList<Node> childrens = gridPane.getChildren();
        if(side == 1){
            for(Node node : childrens){ // node is HBox
                if(GridPane.getRowIndex(node) == index){
                    HBox hbox = (HBox) node;
                    ObservableList<Node> childrensHbox = hbox.getChildren();
                    // must have more than 3 elements (+ - empty box and at least something else)
                    if(childrensHbox.size() > 3){
                        Node lastNode = null; // will have value of the last node in the hbox (right side)
                        for(Node buttonNode : childrensHbox){
                            lastNode = buttonNode;
                        }
                        hbox.getChildren().remove(lastNode);
                    }
                }
            }
        }else if(side == 2){
            for(Node node : childrens){
                if(GridPane.getColumnIndex(node) == index){
                    VBox vbox = (VBox) node;
                    ObservableList<Node> childrensVbox = vbox.getChildren();
                    if(childrensVbox.size() > 3){
                        Node lastNode = null; // will have value of the last node in the vbox (under)
                        for(Node buttonNode : childrensVbox){
                            lastNode = buttonNode;
                        }
                        vbox.getChildren().remove(lastNode);
                    }
                }
            }
        }
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
        // clear up arrayLists in case we used them before
        rows.clear();
        columns.clear();
        for(int i = 0; i < NUM_ROWS; i++){
            rows.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < NUM_COLS; i++){
            columns.add(new ArrayList<Integer>());
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
                if(blackBlockNum >= rows.get(i).size()){ // if to many blocks of black
                    return false;
                }
                if(countedNumber > rows.get(i).get(blackBlockNum)) { // not enough black in the block
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
                if(blackBlockNum >= columns.get(j).size()){
                    return false;
                }
                if(countedNumber > columns.get(j).get(blackBlockNum)){ // not enough black in the block
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
            for(int num : rows.get(i)){
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

        // start values
        NUM_ROWS = 5;
        NUM_COLS = 5;

        launch();

        System.exit(0);
    }
}
