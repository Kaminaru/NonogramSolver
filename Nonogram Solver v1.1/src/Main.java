import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Nonogram Solver");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }



    // precoded variables. I have to add args later
    private static String[][] board = new String[5][5];; // # - solved   ' ' - (empty) nothing
    private static Integer[][] row = new Integer[5][5];
    private static Integer[][] column;


    public static void main(String[] args) {
        // launch();
        board[0][0] = "#";
        System.out.println(board[0][0]);

        // This time I am trying recursion solution with backtracking
        // (It will be slower than old OOP version, but it will solve almost any nonogram by brute force)
    }
}
