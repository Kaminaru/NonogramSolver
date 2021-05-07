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
    private static String[][] board = new String[5][5]; // # - solved   ' ' - (empty) nothing
    private static Integer[][] rows = {{3},{3},{2},{1,2},{2}};
    private static Integer[][] columns = {{2,1},{2},{2},{3},{3}};

    private static void setUpBoard(){
        for(int i = 0; i < board.length; i++){
            Arrays.fill(board[i], " ");
        }
    }

    private static int getNExtJ(int j){
        if(j == board[0].length-1){
            return 0;
        }else{
            return j+1;
        }
    }

    private static boolean checkFewInRow(int i, int j, int neededNum, int numberWeHave){
        // checks if there is needed amount of black in row, or if there is too many
        if(numberWeHave == neededNum){ // we found match, go to next number in row, if we have it
            if(j+1 < board[i].length && board[i][j+1].equals("#")){
                return false; // looks like there is one more extra black that we don't need
            }
            return true;
        }else if(j < board[i].length && board[i][j].equals("#")){
            return checkFewInRow(i, j+1, neededNum, numberWeHave + 1);
        }else if (board[i][j].equals(" ")){
            return true;
        }
        return false;
    }

    private static boolean checkIfRight(int i, int j){
        // Goes through column of given i and j and also row, and check if
        // we have set black color in the right position

        // Checking only if it overflows needed value for black points in a row
//        for(int number : rows[i]){
//            for(int n = 0; n < board[i].length; n++){
//                if(board[i][n].equals("#")){ // if we found black
//                    if(checkFewInRow(i, n, number, 0)){ // we found needed amount of black block
//                        n = n + number; //change n, (skips needed number of black blocks that we have checked)
//                    }else{
//                        return false;
//                    }
//                }
//            }
//        }

        System.out.println("Column time!");
        for(int number : columns[i]){
            System.out.println(number);
        }
        return true;
    }


    private static boolean findSolution(int i, int j){
        if(i == board.length) return true; // at the last row

        // Finds next i and j
        int nextI = i; // index for row
        int nextJ = j; // index for column
        // Does nothing when i and j on the last positions (lower/right corner)
        if(j == board[0].length-1 && i+1 <= board.length-1){
            nextI ++;
            nextJ = 0;
        }else if(j < board[0].length-1){
            nextJ ++;
        }

//        board[i][j] = "#" // marks i and j position on board as black
//        // Checks if this is good decision, recursivly
//        if()



//        if (verify(board, i, j) && findSolution(nextI, nextJ)) {
//            return true;
//        }

        System.out.println(nextI + " " + nextJ);
        return true;
    }
    public static void main(String[] args) {
        // launch();

        // This time I am trying recursion solution with backtracking
        // (It will be slower than old OOP version, but it will solve almost any nonogram by brute force)
        setUpBoard();
        //findSolution(0,0); // starts from upper left corner

//        board[0][0] = "#";
//        board[0][1] = "#";
//        board[0][2] = "#";
//        board[0][3] = "#";
        System.out.println(checkIfRight(0,0));


//        for(int i = 0; i < board.length; i++){
//            for(int j = 0; j < board[i].length; j++){
//                System.out.print(board[i][j]);
//            }
//            System.out.println();
//        }
        System.exit(0);
    }
}
