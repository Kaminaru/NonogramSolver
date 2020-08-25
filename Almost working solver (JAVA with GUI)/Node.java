import javafx.scene.control.Button;

// had to make it abstract, because of mistake while compiling (because of button)
class Node extends Button{
  public int rowPos, columnPos;
  public Node[] neighbourList; // list with all neighbours for Node
  public Node north, east, south, west;
  public static Node[][] board; // to make GUI
  public boolean isBlack = false;
  public boolean isX = false;
  public boolean decided = false;
  public Row inRow; // hold info about which row this node is in
  public Column inColumn;

  public boolean used_for_number = false; // tells if node is already been used in some of the numbers
  public Number used_in_number = null; // tells which number uses this node

  // things that will be changed and returned to normal each time algorithms go throw column or row
  public boolean maybe_black = false;
  public Number maybe_black_number = null;
  public boolean maybe_x = false;

  public Node(int c, int r, Node[][] board){
    columnPos = c;
    rowPos = r;
    this.board = board; // for GUI
    isBlack = false;
    isX = false;
    setMaxWidth(30); setMinWidth(30);
    setMinHeight(30); setMaxHeight(30);
  }

  public void putInAllNeighbours(Node n, Node e, Node s, Node w){
    north = n; east = e; south = s; west = w;
    neighbourList = new Node[]{n,e,s,w};
  }

  public void restart(){
    maybe_black = false;
    maybe_x = false;
    maybe_black_number = null;
  }

  @Override
  public String toString(){
    return "(ROW: " + rowPos + " COL: " + columnPos + ")";
  }

}
