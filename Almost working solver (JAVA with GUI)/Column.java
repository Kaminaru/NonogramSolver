import javafx.scene.control.Button;

class Column extends Button{
  public int columnNum;
  public Node[] columnNodes; // all the Nodes object that are in this column
  public Number[] columnNumbers; // all numbers on the side of board for this column
  public boolean done = false; // true if every Node in this column is eather x or black
  // so algorithm doesn't need to go throw same column over and over.
  public int numberOfNodes; // when = 0, means that every node is eather x or black

  public Column(int columnNum, Number[] columnNumbers, Node[] columnNodes){
    this.columnNum = columnNum;
    this.columnNodes = columnNodes;
    this.columnNumbers = columnNumbers;
    setMaxWidth(30); setMinWidth(30);
    setMinHeight(30); setMaxHeight(100);
    String s = "";
    for(Number number : columnNumbers){
      s += number.num + "\n";
    }
    setText(s);
    numberOfNodes = columnNodes.length;
  }

  public int numberOfNotFinishedNumbers(){
    int i = 0;
    for(Number number : columnNumbers){
      if(!number.ffound){
        i++;
      }
    }
    return i;
  }
}
