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
    private static String[][] board = new String[15][15]; // # - solved   ' ' - (empty) nothing
    private static Integer[][] rows = {{5},{2,2},{4,1,2,2},{2,1,6},{4,7},{4,3,5},{2,1,10},
                                       {1,1,1,1,3},{2,1,1,4},{4,9},{4,4},{1,3,1,5},{1,4,7},{2,5},{1,1,1,2}};
    private static Integer[][] columns = {{2,2},{3,2},{4,3},{12},{3,2,5},{1,2,1,4},{1,2,2,1},{3,2,4,2},
                                          {2,4,2,2},{2,1,3,3},{6,1,3},{5,2,4},{7,2,1},{8,2},{8,2}};


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
