import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.Arrays;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Nonogram Solver");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }

    // precoded variables. I have to add args later
    private static String[][] board = new String[10][10]; // # - solved   ' ' - (empty) nothing
    private static Integer[][] rows = {{6,2},{4,2},{3,2},{3,1},{2,1},{1,4},{5},{3},{3,2},{5,1}};
    private static Integer[][] columns = {{1,1},{1,1},{2,2,1},{5,1},{4,2},{3,1,2},{2,1},{3},{3,5},{4,5}};




    private static void setUpBoard(){
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
        // launch();

        // This time I am trying recursion solution with backtracking
        // (It will be slower than old OOP version, but it will solve almost any nonogram by brute force)
        setUpBoard();
        findSolution(0,0); // starts from upper left corner
        for (String[] rowArr : board) {
            for (String el : rowArr) {
                System.out.print("|" + el);
            }
            System.out.println("|");
        }
        System.exit(0);
    }
}

// maybe change # and " " to true and False




















//    private static boolean checkColumn(int j){
//        int numberOfBlacks = 0; // number of black we need
//        for(int num : columns[j]){
//            numberOfBlacks = numberOfBlacks + num;
//        }
//
//        int blackOnColumnCount = 0; // number of black we have
//        boolean newBlock = false; // shows when we found new black block
//        int blackInBlockCount = 0; // counts numbers of blacks in current block of blacks
//        int blackBlockNum = 0; // number for the block that I can look up in columns array
//        for (String[] row : board) {    // go through rows at same column number
//            if(row[j].equals("#")){
//                blackOnColumnCount++;
//                newBlock = true;
//                blackInBlockCount++;
//                if(blackInBlockCount > columns[j][blackBlockNum]){ // if we have more black than needed
//                    return false;
//                }
//            }else{ // if white
//                if(newBlock){ // if it was black last time (we are going out from the block)
//                    // time to check if block is longer than needed
//                    newBlock = false;
//                    blackInBlockCount = 0;
//                    blackBlockNum++;
//                    if(blackBlockNum >= columns[j].length){
//                        return false;
//                    }
//                }
//            }
//        }

//        if(numberOfBlacks == blackOnColumnCount){ // we need to check if order is right
//            blackBlockNum = 0; // number for the black block that I can look up in columns array
//            int countedNumber = 0;
//            newBlock = false; // shows when we found new black block
//            for(String[] row : board){
//                if(row[j].equals("#")){
//                    newBlock = true;
//                    countedNumber++;
//                    if(countedNumber < columns[j][blackBlockNum]){ // not enough black in the block
//                        return false;
//                    }
//                }else{
//                    if(newBlock){
//                        blackBlockNum ++;
//                        newBlock = false;
//                        countedNumber = 0;
//                        if(blackBlockNum >= columns[j].length){
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
//        return numberOfBlacks > blackOnColumnCount;
//        return numberOfBlacks >= blackOnColumnCount;
//    }