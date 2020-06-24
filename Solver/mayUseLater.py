    # Class variables, same for each object of the class
    animal_type = "fish"
    location = "ocean"


    if int(self.colNumArray[i][0]) == 0: # if there is no black cells in this row
        for row in self.cellBoardArr:
            row[i].setx()
            self.colLeft[i] -= 1


self.colLeft[i]




        # self.cellBoardArr = [] # use that only for printing out, possible to delete it after.

        # # adds all needed cell objects in 2D array
        # for i in range(self.numberOfRows):
        #     cellRow = []
        #     for j in range(self.numberOfColumns):
        #         cellRow.append(Cell()) # adds cell to row
        #     self.cellBoardArr.append(cellRow) # adds row with cells  to 2D array






if len(self.rowNumArray[i]) == 1:  # if only one number
    # number of spaces between each side the rest is black on the middle
    eachSide = self.rowLeft[i] - int(self.rowNumArray[i][0])
    if eachSide == 0: # if all free cells is black
        for cell in self.cellBoardArr[i]:
            if cell.isSet() == False: # for all x set
                cell.setblack()
                self.rowLeft[i] -= 1
    elif self.rowLeft[i] - 2*eachSide > 0: # if there is new black spaces we can set
        #probles here is that it is possible to have something like:
        # 5 |x| |x|x|x| | | | | |x|x|
        # and algorithm will think that ther first empty position is
        # part of the "line", in other words 6, so to prevent it:
        num = 0
        start = 0 # to know where the last x's ends
        for k in range(len(self.cellBoardArr[i])):
            if self.cellBoardArr[i][k].isX() == True:
                if num > 0: # if we found the situation that i mentioned before
                    # we will set previous not needed spaces to X
                    while num != 0:
                        self.cellBoardArr[i][k-num].setx()
                        self.rowLeft[i] -= 1
                        num -= 1
            else:
                if num == 0: # change start position if needed
                    start = k
                num += 1

        # adds needed ammount of black cell
        nBlacksToPaint = self.rowLeft[i] - 2*eachSide
        for k in range(start+eachSide, start+eachSide+nBlacksToPaint):
            self.cellBoardArr[i][k].setblack()




# TESTING
    print("-------", row.ID, "-------")
    # Check if dictionary is empty
    res = not bool(fromleft)
    # print result
    print("Is dictionary empty ? : " + str(res))
    for x in fromleft: # for each key
        print("KEY: ", x, end = " ")
        print("VALUE: ", fromleft[x])
    print()



                # elif row.cellsArr[i].isBlack():
                #     # if black so go to next one
                #     pass


        for row in self.rowsArray:
            print("ID:", row.ID)
            for number in row.numbersArr:
                print(number)




            for key in fromLeft:
                print("KEY: ", key, "VALUE: ", fromLeft[key])
