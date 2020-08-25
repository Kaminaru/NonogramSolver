import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.Scanner;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;



public class Main extends Application {
  private static Board board; // referance to board object
  private static Node[][] nodeList; // 2D list, takes care of rows and columns
  private static int columns, rows; // from 1 to ->
  private static Text statusinfo; // text with information
  private static ArrayList<Column> columnList;
  private static ArrayList<Row> rowList;

  private static Pane pane = new Pane();
  private static GridPane gridpane = new GridPane();



  @Override
  public void start(Stage stage) {

    HBox hpaneButtons = new HBox();


    statusinfo = new Text("Nanogram Test"); // stats tekst for utveier
    statusinfo.setFont(new Font(20));
    statusinfo.setX(0);  statusinfo.setY(20);

    Button startButton = new Button("Choose your file");
    Button solveBoard = new Button("Solve");
    hpaneButtons.getChildren().add(startButton);
    hpaneButtons.setTranslateX(0); hpaneButtons.setTranslateY(25);


    // choose file button action
    startButton.setOnAction((e) ->{
      File file = new FileChooser().showOpenDialog(stage);
      board = Board.lesFraFil(file);
      columns = board.column;
      rows = board.row;
      nodeList = board.nodeList;
      columnList = board.columnList;
      rowList = board.rowList;

      gridpane.getChildren().clear(); // clear old gridplane
      gridpane.setMinWidth(1000); gridpane.setMaxWidth(1000);
      gridpane.setMinHeight(900); gridpane.setMaxHeight(900);
      gridpane.setGridLinesVisible(true);
      gridpane.setLayoutX(170); gridpane.setLayoutY(80);

      boolean changedCol = false;
      for(int x = 0; x < rows; x++){
        gridpane.add(rowList.get(x),0,x+1);
        for(int y = 0; y < columns; y++){
          if(changedCol == false){ // adds numbers on top only first run throw first row
            gridpane.add(columnList.get(y),y+1,0);
          }
          gridpane.add(nodeList[x][y], y+1, x+1); // puts in Node in right position in gridpane
        }
        changedCol = true;
      }
      pane.getChildren().add(gridpane);
      hpaneButtons.getChildren().add(solveBoard);
    // end of startButton action
    });


    // Solve board button action
    solveBoard.setOnAction((e) ->{
      restartBoard();
      int num_nodes_left = columns * rows;
      int num_tries = 0;
      while(num_nodes_left != 0 && num_tries < 100){
        // ---------------------------------------------------------------------
        //                          working with every row
        // ---------------------------------------------------------------------
        for(Row row : board.rowList){

          //finds the sum of all numbers in this row + spaces beetween them
          int rowSum = 0;
          if(row.rowNumbers[0].num != 0){
            rowSum += row.rowNumbers.length-1;
          }
          for(Number rowN : row.rowNumbers){
            rowSum += rowN.num;
          }


          // System.out.println("------NEW ROW-------" + "Nr: " + row.rowNum + " NODES LEFT: " + num_nodes_left);
          if(row.done == false){
            if(row.rowNumbers[0].num == 0){ // checks if row have number 0 in it
              for(Node n : row.rowNodes){
                n.isX = true;
                n.setText("X");
                num_nodes_left--;
              }
              row.done = true;
            }else if(rowSum == nodeList[0].length){ // there is place for each black node in this row
              int position = 0; // from left to right
              for(Number number : row.rowNumbers){
                int n1 = number.num;
                for(int i = n1; i > 0; i--){
                  row.rowNodes[position].isBlack = true;
                  row.rowNodes[position].setStyle("-fx-background-color: #000000");
                  num_nodes_left--;
                  position++;
                }
                if(position <= row.rowNodes.length-1){
                  row.rowNodes[position].isX = true;
                  row.rowNodes[position].setText("X");
                  num_nodes_left--;
                  position++;
                }
              }
              row.done = true;
            }else{ // if there is no 0 in that row
              int position = 0; // from left to right
              for(Number number : row.rowNumbers){
                int n1 = number.num;
                for(int i = n1; i > 0; i--){
                  // -------------------------------------------------  BECAUSE position comes more than lenght (CHECK WHY)
                  if(position > row.rowNodes.length-1){
                    continue;
                  }
                  // -------------------------------------------------
                  if(row.rowNodes[position].isX && row.rowNodes[position].inColumn.done){ // if it X so go next
                    i++; // stays at the same "number index"
                  }else{ //// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CHECK IT
                    row.rowNodes[position].maybe_black = true;
                    row.rowNodes[position].maybe_black_number = number; // saves the number object that is used for this node
                  }
                  position++;
                }
                if(position <= row.rowNodes.length-1){
                  row.rowNodes[position].maybe_x = true;
                  position++;
                }
              }

              // from right to left
              if(row.done == false){ // if its still wasn't updated from first go throw
                position = row.rowNodes.length-1;
                // reverse array:
                int length = row.rowNumbers.length;
                Number[] reverseArray = new Number[length];
                for(int i = 0; i < length; i++) {
                  reverseArray[i] = row.rowNumbers[length - i - 1];
                }
                for(Number number : reverseArray){ // to go from right to left in rowNumbers
                  int n2 = number.num;
                  for(int i = n2; i > 0; i--){
                    // -------------------------------------------------  BECAUSE position comes more than lenght (CHECK WHY)
                    if(position > row.rowNodes.length-1){
                      continue;
                    }
                    // -------------------------------------------------
                    if(row.rowNodes[position].isX && row.rowNodes[position].inColumn.done){ // if it X so go next
                      i++; // stays at the same "number index"
                    }else{ //// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CHECK IT
                      if(row.rowNodes[position].maybe_black == true && row.rowNodes[position].isBlack != true
                      && row.rowNodes[position].maybe_black_number == number){ // found match
                        row.rowNodes[position].isBlack = true;
                        row.rowNodes[position].setStyle("-fx-background-color: #000000");
                        num_nodes_left--;
                      }
                    }
                    position--;
                  }
                  if(position >= 0){
                    row.rowNodes[position+1].maybe_x = true;
                    position--;
                  }
                }
              }
            }
            updateBoard(row);
          }
        }

        // ---------------------------------------------------------------------
        //                       working with every column
        // ---------------------------------------------------------------------
        for(Column column : board.columnList){
          if(column.done == false){
            if(column.columnNumbers[0].num == 0){ // checks if row have number 0 in it
              for(Node n : column.columnNodes){
                n.isX = true;
                n.setText("X");
                num_nodes_left--;
              }
              column.done = true;
            }else{ // if there is no 0 in that column
              int position = 0; // from up to down
              for(Number number : column.columnNumbers){
                int n1 = number.num;
                for(int i = n1; i > 0; i--){
                  if(column.columnNodes[position].isX && column.columnNodes[position].inRow.done){ // if it X so go next
                    i++; // stays at the same "number index"
                  }else{ //// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CHECK IT
                    column.columnNodes[position].maybe_black = true;
                    column.columnNodes[position].maybe_black_number = number;
                  }
                  position++;
                }
                if(position == column.columnNodes.length){ // all black nodes in this column is found
                  for(Node n : column.columnNodes){
                    if(n.maybe_black == true){
                      n.isBlack = true;
                      n.setStyle("-fx-background-color: #000000");
                      num_nodes_left--;
                    }else if(n.maybe_black == false && n.isBlack == false){
                      n.isX = true;
                      n.setText("X");
                      num_nodes_left--;
                    }
                  }
                  column.done = true;
                }else{ // if there is still place in the right side
                  column.columnNodes[position].maybe_x = true;
                  position++;
                }
              }

              // from right to left
              if(column.done == false){ // if its still wasn't updated from first go throw
                position = column.columnNodes.length-1;
                // reverse array:
                int length = column.columnNumbers.length;
                Number[] reverseArray = new Number[length];
                for(int i = 0; i < length; i++) {
                  reverseArray[i] = column.columnNumbers[length - i - 1];
                }
                for(Number number : reverseArray){ // to go from down to up in columnNumbers
                  int n2 = number.num;
                  for(int i = n2; i > 0; i--){
                    if(column.columnNodes[position].isX && column.columnNodes[position].inRow.done){ // if it X so go next
                      i++; // stays at the same "number index"
                    }else{ //// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CHECK IT
                      // System.out.println(column.columnNodes[position] + " " + column.columnNodes[position].maybe_black);
                      if(column.columnNodes[position].maybe_black == true && column.columnNodes[position].isBlack != true
                      && column.columnNodes[position].maybe_black_number == number){ // found match
                        column.columnNodes[position].isBlack = true;
                        column.columnNodes[position].setStyle("-fx-background-color: #000000");
                        num_nodes_left--;
                      }
                    }
                    position--;
                  }
                  if(position <= column.columnNodes.length-1){
                    column.columnNodes[position+1].maybe_x = true;
                    position--;
                  }
                }
              }
            }
            updateBoard(column);
          }
        }

        // ---------------------------------------------------------------------
        //        checking if some columns or rows is done + puts X's
        //   (TOOOOOOO SIMPLE SOLUTION, need to check if it works all the time)
        //    (Just checking amount of black nodes against sum of all numbers)
        // ---------------------------------------------------------------------
        for(Row row : board.rowList){
          if(row.done == false){ // so it won't check rows that is done (saves time)
            int sumOfNumbers = 0;
            for(Number number : row.rowNumbers){
              sumOfNumbers += number.num;
            }
            int numberOfBlackNodes = 0;
            for(Node n : row.rowNodes){
              if(n.isBlack) numberOfBlackNodes++;
            }

            if(sumOfNumbers == numberOfBlackNodes){ // if row is done, sets X's
              for(Node n : row.rowNodes){
                if(!n.isBlack){ // if not black
                  n.isX = true;
                  n.setText("X");
                  num_nodes_left--;
                }
              }
              row.done = true;
            }
          }
        }

        for(Column column : board.columnList){
          if(column.done == false){ // so it won't check column that is done (saves time)
            int sumOfNumbers = 0;
            for(Number number : column.columnNumbers){
              sumOfNumbers += number.num;
            }
            int numberOfBlackNodes = 0;
            for(Node n : column.columnNodes){
              if(n.isBlack) numberOfBlackNodes++;
            }

            if(sumOfNumbers == numberOfBlackNodes){ // if column is done, sets X's
              for(Node n : column.columnNodes){
                if(!n.isBlack){ // if not black
                  n.isX = true;
                  n.setText("X");
                  num_nodes_left--;
                }
              }
              column.done = true;
            }
          }
        }










        // ---------------------------------------------------------------------
        //            Puts x's if there is only one number:
        // 4 | |B|B| | | | | | |  --> 4 | |B|B| | | |x|x|x|
        // Becasue there is no need in three last place, 4 can't rich them
        // CHECKS ONLY FOR ROWS AND COLUMNS WITH one number
        // Also checks if   4 |x| |B| | | | |x|x| ( have x before blacks)
        // ---------------------------------------------------------------------

        // ---------------------------------------------------------------------
        //            Puts black's if there is only one number:
        // 4 | | |B| | | | | | |  --> 4 | | |B|B| | | | | |
        // ---------------------------------------------------------------------



        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // must do it also if there is only one number in NUMBER left
        // for that i need to delete used numbers from list

        for(Row row : board.rowList){
          if(row.done == false){ // so it won't check row that is done (saves time)
            if(row.rowNumbers.length == 1){ // only if there is only one number
              int firstFound = 0; // holds info about current process
              int firstBlackPosition = 0; // position of first black node in this row
              boolean start_count = false; // tells if algorithm can add new black nodes
              int number = row.rowNumbers[0].num; // the number program working with
              for(int position = 0; position < row.rowNodes.length; position++){
                if(!row.rowNodes[position].isX){
                  if(row.rowNodes[position].isBlack){
                    if(firstFound == 0){
                      firstFound = 1;
                      firstBlackPosition = position;
                    }
                    start_count = true;
                    number--;
                  }else if(start_count == true && number > 0){ // puts blacks Nodes after last black
                    row.rowNodes[position].isBlack = true;
                    row.rowNodes[position].setStyle("-fx-background-color: #000000");
                    num_nodes_left--;
                    number--;
                  }else if(firstFound == 1){ // sets x in nodes where black can't rich
                    // from up to down
                    for(int i = firstBlackPosition + row.rowNumbers[0].num; i < row.rowNodes.length; i++){
                      row.rowNodes[i].isX = true;
                      row.rowNodes[i].setText("X");
                      num_nodes_left--;
                    }
                    // from down to up
                    // --------------------
                    // check if it works !!!!!!!!!!!!!!!!
                    //-----------------
                    int test = firstBlackPosition - row.rowNumbers[0].num;
                    if(test > 0){
                      for(int i = test; i >= 0; i--){
                        row.rowNodes[i].isX = true;
                        row.rowNodes[i].setText("X");
                        num_nodes_left--;
                      }
                    }
                    //----------------------------------------------------------
                    //----------------------------------------------------------
                    firstFound = 2;
                  }else if(number != 0){
                    number--;
                  }
                }
              }
            }
          }
        }


        for(Column column : board.columnList){
          if(column.done == false){ // so it won't check column that is done (saves time)
            if(column.columnNumbers.length == 1){ // only if there is only one number
              int firstFound = 0; // holds info about current process
              int firstBlackPosition = 0; // position of first black node in this column
              boolean start_count = false; // tells if algorithm can add new black nodes
              int number = column.columnNumbers[0].num;
              for(int position = 0; position < column.columnNodes.length; position++){
                if(!column.columnNodes[position].isX){
                  if(column.columnNodes[position].isBlack){
                    if(firstFound == 0){
                      firstFound = 1;
                      firstBlackPosition = position;
                    }
                    start_count = true;
                    number--;
                  }
                  else if(start_count == true && number > 0){ // puts blacks Nodes after last black
                    column.columnNodes[position].isBlack = true;
                    column.columnNodes[position].setStyle("-fx-background-color: #000000");
                    num_nodes_left--;
                    number--;
                  }else if(firstFound == 1){ // sets x in nodes where black can't rich
                    // from up to down
                    for(int i = firstBlackPosition + column.columnNumbers[0].num; i < column.columnNodes.length; i++){
                      column.columnNodes[i].isX = true;
                      column.columnNodes[i].setText("X");
                      num_nodes_left--;
                    }
                    // from down to up
                    // --------------------
                    // check if it works !!!!!!!!!!!!!!!!
                    //-----------------
                    int test = firstBlackPosition - column.columnNumbers[0].num;
                    if(test > 0){
                      for(int i = test; i >= 0; i--){
                        column.columnNodes[i].isX = true;
                        column.columnNodes[i].setText("X");
                        num_nodes_left--;
                      }
                    }
                    //----------------------------------------------------------
                    //----------------------------------------------------------
                    firstFound = 2;
                  }else if(number != 0){
                    number--;
                  }
                }
              }
            }
          }
        }




      // ---------------------------------------------------------------------
      //            Puts x's if there is no place for black nodes:
      // 4 |x| | |x| | | | | --> 4 |x|x|x|x| | | | |
      // ---------------------------------------------------------------------
      for(Row row : board.rowList){
        if(row.done == false){ // so it won't check row that is done (saves time)
          if(row.rowNumbers.length == 1){ // only if there is only one number
            // from left to right
            boolean working_with_x = false;
            int numberlen = row.rowNumbers[0].num;
            int len = 0;
            for(int position = 0; position < row.rowNodes.length; position++){
              if(row.rowNodes[position].isX && !working_with_x){
                working_with_x = true;
              }else if(row.rowNodes[position].isX && working_with_x){
                // comes to another x ---> |x| | |x| | |
                // checks if there is enough place for black
                if(numberlen > len){
                  for(Node n : row.rowNodes){
                    if(n.maybe_x){
                      n.isX = true;
                      n.setText("X");
                      num_nodes_left--;
                    }
                  }
                }
                working_with_x = false;
                len = 0;
                restartBoard();
              }else if(!row.rowNodes[position].isBlack && working_with_x){
                len++;
                row.rowNodes[position].maybe_x = true;
              }else if(row.rowNodes[position].isBlack){
                working_with_x = false;
                len = 0;
                restartBoard();
              }
            }
          }
        }
      }

      restartBoard();
      for(Column column : board.columnList){
        if(column.done == false){ // so it won't check column that is done (saves time)
          if(column.columnNumbers.length == 1){ // only if there is only one number
            // from up to down
            boolean working_with_x = false;
            int numberlen = column.columnNumbers[0].num;
            int len = 0;
            for(int position = 0; position < column.columnNodes.length; position++){
              if(column.columnNodes[position].isX && !working_with_x){
                working_with_x = true;
              }else if(column.columnNodes[position].isX && working_with_x){
                // comes to another x ---> |x| | |x| | |
                // checks if there is enough place for black
                if(numberlen > len){
                  for(Node n : column.columnNodes){
                    if(n.maybe_x){
                      n.isX = true;
                      n.setText("X");
                      num_nodes_left--;
                    }
                  }
                }
                working_with_x = false;
                len = 0;
                restartBoard();
              }else if(!column.columnNodes[position].isBlack && working_with_x){
                len++;
                column.columnNodes[position].maybe_x = true;
              }else if(column.columnNodes[position].isBlack){
                working_with_x = false;
                len = 0;
                restartBoard();
              }
            }
          }
        }
      }

      // ---------------------------------------------------------------------
      //            Puts black's if there is no place from behind:
      // 4 10 | |#| | | | | | | --> 4 10 | |#|#|#| | | | |
      // we know that because there is only one place from the left side
      // ---------------------------------------------------------------------

      // for(Row row : board.rowList){
      //   if(row.done == false){ // so it won't check row that is done (saves time)
      //
      //   }
      // }

      for(Column column : board.columnList){
        if(column.done == false){ // so it won't check column that is done (saves time)
          boolean inProgress = false;
          boolean endOfProgressOnLine = false;
          int num = 0; // hold info about which number program working with
          int number = column.columnNumbers[num].num; // at the start it is a first element
          int staticNumber = column.columnNumbers[num].num; // same as number, but it won't be changed so often
          int current_position_of_number = 0; // ????
          for(int position = 0; position < column.columnNodes.length; position++){
            if(!endOfProgressOnLine){
              if(!column.columnNodes[position].isBlack && !column.columnNodes[position].isX && !inProgress){
                // not x and not black and still not started; starts whole process
                inProgress = true;
                current_position_of_number++;
              }else if(column.columnNodes[position].isX && !inProgress){
                // if the first elements is x, does nothing
                continue;

              }else if(column.columnNodes[position].isBlack && !inProgress){// to choose right number in column, program will work with
                // most likely won't happen ^^^^
                if(number > 1){
                  number--;
                }else{ // if number is 0, changes to next number in column
                  num++;

                  if(column.columnNumbers.length > num+1){
                    number = column.columnNumbers[num].num; // num will be changed, so number will be changed also
                    staticNumber = column.columnNumbers[num].num;
                  }else{
                    endOfProgressOnLine = true;
                  }
                }
              }else if(column.columnNodes[position].isBlack && inProgress){
                // if this is a black box, and work is started
                current_position_of_number++;
              }else if(!column.columnNodes[position].isX && !column.columnNodes[position].isBlack && inProgress){
                // if it comes to "white" space while still in progress, adds few black
                // it must decide if box will be colored to black one
                if(current_position_of_number < number){
                  column.columnNodes[position].isBlack = true;
                  column.columnNodes[position].setStyle("-fx-background-color: #000000");
                  num_nodes_left--;
                  current_position_of_number++;
                }else{
                  current_position_of_number = 0;
                  inProgress = false;
                  endOfProgressOnLine = true;
                }
              }
            }
          }
        }
      }


        restartBoard();
        num_tries++;
      }
      if(num_tries >= 20){
        statusinfo.setText("No solution were found");
        return;
      }

      hpaneButtons.getChildren().remove(solveBoard); // ?????????????????????? not working ??????????????????????
      statusinfo.setText("Solution were found!");
      // end of solveBoard action
    });

    pane.getChildren().addAll(hpaneButtons,statusinfo);

    Scene scene = new Scene(pane);
    stage.setMinHeight(1000);
    stage.setMinWidth(1000);
    stage.setTitle("Nanogram Solver");
    stage.setScene(scene);
    stage.show();
  }

  // update grid
  public void updateBoard(Row row){
    for(Node n : row.rowNodes){
      n.restart();
    }
  }
  public void updateBoard(Column col){
    for(Node n : col.columnNodes){
      n.restart();
    }
  }

  public void restartBoard(){
    // update it later
    for(Node[] nodeListPart : nodeList){
      for(Node node : nodeListPart){
        node.maybe_black = false;
        node.maybe_black_number = null;
        node.maybe_x = false;
      }
    }
  }

  public int findIndexOfNumber(Number target, Number[] elementData) {
    if (target == null) {
      System.out.println("TARGET IS NULL in findIndexOfNumber");
        // for(int i = 0; i < elementData.length; i++){
        //   if (elementData[i]==null){
        //     return i;
        //   }
        // }
    } else {
        for (int i = 0; i < elementData.length; i++){
          if (elementData[i] == target){
            return i;
          }
        }
    }
    return -1;
}

  public static void main(String[] args) {
    launch(args);
  }
}
