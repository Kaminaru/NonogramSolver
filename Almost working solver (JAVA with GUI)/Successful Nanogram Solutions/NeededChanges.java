class NeededChanges{
  // ---------------------------------------------------------------------
  //                 Checks if some numbers where found
  // will check only something like:
  //            1 4 1 |x|x|B| | |B| |  ->  1 4 1 |x|x|B|x| |B| |
  //
  // And not like: 1 4 1 |x| |B| | |B| |
  // Thats why it will do it once per one "BIG" for loop
  // ---------------------------------------------------------------------

  // for(Row row : board.rowList){
  //   if(row.done == false){ // so it won't check row that is done (saves time)
  //
  //
  //
  //
  //
  //   // -------------------------------------------------------------------
  //   // -------------------------------------------------------------------
  //   // CAN WAIT WITH THAT
  //   // -------------------------------------------------------------------
  //   // -------------------------------------------------------------------
  //   // checks if it is    1 2   | |B| | | |
  //   // so first block must be X
  //   // !!! or 2 2 3 | |B|B| | | |
  //   // if | |B|......
  //   if(!row.rowNodes[0].isBlack && !row.rowNodes[0].isX && row.rowNodes[1].isBlack){
  //
  //   }
  //   // -------------------------------------------------------------------
  //   // -------------------------------------------------------------------
  //   // -------------------------------------------------------------------
  //   // -------------------------------------------------------------------
  //
  //
  //
  //
  //
  //
  //
  //
  //   // from left to right
  //   boolean firstBlack = false;
  //   int firstBlackPosition = 0;
  //   for(int position = 0; position < row.rowNodes.length; position++){
  //     // if it finds only x's before first black node so everything is nice
  //     if((row.rowNodes[position].isX && !firstBlack) ||
  //        (row.rowNodes[position].isBlack && row.rowNodes[position].used_for_number)){ // do i really need firstBlack ????????
  //       continue;
  //     }else{
  //       if(row.rowNodes[position].isBlack){ // if black so everything went fine
  //         firstBlack = true;
  //         firstBlackPosition = position;
  //       }
  //       break; // goes out from for loop
  //     }
  //   }
  //   if(firstBlack){
  //     // position 1
  //     int number = row.rowNumbers[0].num; // 1
  //     boolean notFull = false;
  //     ArrayList<Node> maybeDone =  new ArrayList<Node>();
  //     for(int i = number - 1; i < number; i++){
  //       if(row.rowNodes[firstBlackPosition].isBlack){
  //         maybeDone.add(row.rowNodes[firstBlackPosition]);
  //         firstBlackPosition++;
  //       }else{
  //         notFull = true;
  //       }
  //     }
  //     if(!notFull){
  //       row.rowNodes[firstBlackPosition].isX = true;
  //       row.rowNodes[firstBlackPosition].setText("X");
  //       num_nodes_left--;
  //       // to do things like 1 1 1 |x|B| |B| | | | --> |x|B|x|B|x| | |
  //       // and not only first x --> |x|B|x|B| | | |
  //       for(Node n : maybeDone){
  //         n.used_for_number = true;
  //         n.used_in_number = row.rowNumbers[0];
  //         row.rowNumbers[0].ffound = true;
  //       }
  //     }
  //   }
  //
  //
  //   // from right to left
  //
  //   firstBlack = false;
  //   firstBlackPosition = 0;
  //   // reverse Numbers array:
  //   int length = row.rowNumbers.length;
  //   Number[] reverseNumberArray = new Number[length];
  //   for(int i = 0; i < length; i++) {
  //     reverseNumberArray[i] = row.rowNumbers[length - i - 1];
  //   }
  //   // reverse Nodes array:
  //   length = row.rowNodes.length;
  //   Node[] reverseNodeArray = new Node[length];
  //   for(int j = 0; j < row.rowNodes.length; j++){
  //     reverseNodeArray[j] = row.rowNodes[length - j - 1];
  //   }
  //
  //   // checks if it is    1 2   | |B| | | | from right side
  //   // if(reverse)
  //
  //
  //
  //   for(int position = 0; position < reverseNodeArray.length; position++){
  //     // if it finds only x's before first black node so everything is nice
  //     if((reverseNodeArray[position].isX && !firstBlack) ||
  //        (reverseNodeArray[position].isBlack && reverseNodeArray[position].used_for_number)){ // do i really need firstBlack ????????
  //       continue;
  //     }else{
  //       if(reverseNodeArray[position].isBlack){ // if black so everything went fine
  //         firstBlack = true;
  //         firstBlackPosition = position;
  //       }
  //       break; // goes out from for loop
  //     }
  //   }
  //   if(firstBlack){
  //     // position 1
  //     int number = reverseNumberArray[0].num; // 1
  //     boolean notFull = false;
  //     ArrayList<Node> maybeDone =  new ArrayList<Node>();
  //     for(int i = number - 1; i < number; i++){
  //       if(reverseNodeArray[firstBlackPosition].isBlack){
  //         maybeDone.add(reverseNodeArray[firstBlackPosition]);
  //         firstBlackPosition++;
  //       }else{
  //         notFull = true;
  //       }
  //     }
  //     if(!notFull){
  //       reverseNodeArray[firstBlackPosition].isX = true;
  //       reverseNodeArray[firstBlackPosition].setText("X");
  //       num_nodes_left--;
  //       // to do things like 1 1 1 |x|B| |B| | | | --> |x|B|x|B|x| | |
  //       // and not only first x --> |x|B|x|B| | | |
  //       for(Node n : maybeDone){
  //         n.used_for_number = true;
  //         n.used_in_number = reverseNumberArray[0];
  //         reverseNumberArray[0].ffound = true;
  //       }
  //     }
  //   }
  //
  //
  //
  //
  //   }
  // }
  //
  //
  // // !!! COLUMNS !!!
  //
  // for(Column column : board.columnList){
  //   if(column.done == false){ // so it won't check row that is done (saves time)
  //
  //
  //   // from up to down
  //   boolean firstBlack = false;
  //   int firstBlackPosition = 0; // position of the first black block
  //   int numberShift = 0; // makes changes for number we use later on
  //   int indxOfWorkingNum = 0; // holds info about which number this for loop will work later
  //   for(int position = 0; position < column.columnNodes.length; position++){
  //     // if it finds only x's before first black node so everything is nice
  //     if((column.columnNodes[position].isX && !firstBlack) ||
  //        (column.columnNodes[position].isBlack && column.columnNodes[position].used_for_number)){ // do i really need firstBlack ????????
  //
  //       if(column.columnNodes[position].used_for_number){
  //         int inx = findIndexOfNumber(column.columnNodes[position].used_in_number,column.columnNumbers);
  //         if(column.columnNum == 3){
  //           System.out.println("index" + inx);
  //         }
  //         if(inx != indxOfWorkingNum){
  //           numberShift++; // to got to the next number
  //           indxOfWorkingNum = inx;
  //         }
  //       }
  //       continue; // goes to next for loop
  //     }else{
  //       if(column.columnNodes[position].isBlack){ // if black so everything went fine
  //         firstBlack = true;
  //         firstBlackPosition = position;
  //       }
  //       break; // goes out from for loop
  //     }
  //   }
  //   if(firstBlack){
  //     // position 1
  //     int number = column.columnNumbers[numberShift].num;
  //     boolean notFull = false;
  //     ArrayList<Node> maybeDone =  new ArrayList<Node>();
  //     for(int i = number - 1; i < number; i++){
  //       if(column.columnNodes[firstBlackPosition].isBlack){
  //         maybeDone.add(column.columnNodes[firstBlackPosition]);
  //         firstBlackPosition++;
  //       }else{
  //         notFull = true;
  //       }
  //     }
  //     if(!notFull){
  //       column.columnNodes[firstBlackPosition].isX = true;
  //       column.columnNodes[firstBlackPosition].setText("X");
  //       num_nodes_left--;
  //       // to do things like 1 1 1 |x|B| |B| | | | --> |x|B|x|B|x| | |
  //       // and not only first x --> |x|B|x|B| | | |
  //       for(Node n : maybeDone){
  //         n.used_for_number = true;
  //         n.used_in_number = column.columnNumbers[0];
  //         column.columnNumbers[0].ffound = true;
  //       }
  //     }
  //   }
  //
  //


              //
              // // from down to up
              //
              // firstBlack = false;
              // firstBlackPosition = 0;
              // numberShift = 0;
              // // reverse Numbers array:
              // int length = column.columnNumbers.length;
              // Number[] reverseNumberArray = new Number[length];
              // for(int i = 0; i < length; i++) {
              //   reverseNumberArray[i] = column.columnNumbers[length - i - 1];
              // }
              // // reverse Nodes array:
              // length = column.columnNodes.length;
              // Node[] reverseNodeArray = new Node[length];
              // for(int j = 0; j < column.columnNodes.length; j++){
              //   reverseNodeArray[j] = column.columnNodes[length - j - 1];
              // }
              //
              // // -------------------------------------------------------------------
              // // -------------------------------------------------------------------
              // // checks if it is    1 2   | |B| | | | from right side
              // // if(reverse)
              //
              //
              //
              // for(int position = 0; position < reverseNodeArray.length; position++){
              //   // if it finds only x's before first black node so everything is nice
              //   if((reverseNodeArray[position].isX && !firstBlack) ||
              //      (reverseNodeArray[position].isBlack && reverseNodeArray[position].used_for_number)){ // do i really need firstBlack ????????
              //     continue;
              //   }else{
              //     if(reverseNodeArray[position].isBlack){ // if black so everything went fine
              //       firstBlack = true;
              //       firstBlackPosition = position;
              //     }
              //     break; // goes out from for loop
              //   }
              // }
              // if(firstBlack){
              //   // position 1
              //   int number = reverseNumberArray[0+numberShift].num; // 1
              //   numberShift++;
              //   boolean notFull = false;
              //   ArrayList<Node> maybeDone =  new ArrayList<Node>();
              //   for(int i = number - 1; i < number; i++){
              //     if(reverseNodeArray[firstBlackPosition].isBlack){
              //       maybeDone.add(reverseNodeArray[firstBlackPosition]);
              //       firstBlackPosition++;
              //     }else{
              //       notFull = true;
              //     }
              //   }
              //   if(!notFull){
              //     reverseNodeArray[firstBlackPosition].isX = true;
              //     reverseNodeArray[firstBlackPosition].setText("X");
              //     num_nodes_left--;
              //     // to do things like 1 1 1 |x|B| |B| | | | --> |x|B|x|B|x| | |
              //     // and not only first x --> |x|B|x|B| | | |
              //     for(Node n : maybeDone){
              //       n.used_for_number = true;
              //       n.used_in_number = reverseNumberArray[0];
              //       reverseNumberArray[0].ffound = true;
              //     }
              //   }
              // }


                      //   }
                      // }
                      //

}
