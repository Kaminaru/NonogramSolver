import javafx.scene.control.Button;

class Row extends Button{
  public int rowNum;
  public Node[] rowNodes; // all the Nodes object that are in this row
  public Number[] rowNumbers; // all numbers on the side of board for this row
  public boolean done = false; // true if every Node in this row is eather x or black
  // so algorithm doesn't need to go throw same row over and over.
  public int numberOfNodes; // when = 0, means that every node is eather x or black

  public Row(int rowNum, Number[] rowNumbers, Node[] rowNodes){
    this.rowNum = rowNum;
    this.rowNumbers = rowNumbers;
    this.rowNodes = rowNodes;
    setMaxWidth(100); setMinWidth(30);
    setMinHeight(30); setMaxHeight(30);
    String s = "";
    for(Number number : rowNumbers){
      s += number.num + " ";
    }
    setText(s);
    numberOfNodes = rowNodes.length;
  }

  // using for "remove" used numbers
  public int numberOfNotFinishedNumbers(){
    int i = 0;
    for(Number number : rowNumbers){
      if(!number.ffound){
        i++;
      }
    }
    return i;
  }

}
