import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Board{
  public static Node[][] nodeList;
  public static int column, row;
  public static ArrayList<Column> columnList = new ArrayList<Column>();
  public static ArrayList<Row> rowList = new ArrayList<Row>();

  private Board(Node[][] list, int c, int r){
    nodeList = list;
    column = c;
    row = r;
  }

  // public static int getColumn(){return column;}
  // public static int getRow(){return rader;}
  // public static Node[][] getNodeList(){return nodeList;}

  public static Board lesFraFil(File fil){
    Scanner scanner = null;
    try{
      scanner = new Scanner(fil);
    }catch(FileNotFoundException e){
      System.out.println("Can not find file");
    }

    String firstLine = scanner.nextLine(); // first line must be row
    String[] infoAboutBoard = firstLine.split(" ");
    row = Integer.parseInt(infoAboutBoard[0]);
    column = Integer.parseInt(infoAboutBoard[1]);

    Node[][] board = new Node[row][column]; // makes 2D array
    // makes nodes for whole board
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[0].length; j++){
        board[i][j] = new Node(j, i, board);
      }
    }

    int i = 0;
    int row1_or_column2 = 0; // 1 if row, 2 if column
    int rowInt = 0;
    int columnInt = 0;
    while(scanner.hasNextLine()){
      String[] numbers = scanner.nextLine().split(" ");
      if(numbers[0].equals("row")){
        row1_or_column2 = 1;
      }else if(numbers[0].equals("column")){
        row1_or_column2 = 2;
      }else if(row1_or_column2 == 0){ // if something went wrong with file format
        return null;
      }else{
        // ROWs INFO FROM FILE
        if(row1_or_column2 == 1){
          Number[] rowNumbers = new Number[numbers.length];

          i = 0;
          for(String r : numbers){ // makes array with all numbers in this row
            rowNumbers[i++] = new Number(Integer.parseInt(r));
          }
          Node[] rowNodes = board[rowInt];
          Row row = new Row(rowInt, rowNumbers, rowNodes);
          // changes inRow variable in each node of this row
          for(Node n : rowNodes){
            n.inRow = row;
          }
          rowList.add(row);
          rowInt++;
        // COLUMNS INFO FROM FILE
        }else{
          Number[] columnNumbers = new Number[numbers.length];
          i = 0;
          for(String c : numbers){ // makes array with all numbers in this column
            columnNumbers[i++] = new Number(Integer.parseInt(c));
          }

          // puts all Nodes from this column in column list
          Node[] columnNodes = new Node[board[0].length];
          for(int a = 0; a < board[0].length; a++){
            columnNodes[a] = board[a][columnInt];
          }
          Column col = new Column(columnInt, columnNumbers, columnNodes);
          // changes inColumn variable in each node of this column
          for(Node n : columnNodes){
            n.inColumn = col;
          }
          columnList.add(col);
          columnInt++;
        }
      }
    }

    return new Board(board, column, row);
  }

}
