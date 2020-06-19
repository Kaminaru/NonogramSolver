from cell import Cell


# FILE FORMAT: size of row / size of column
# size of row * line with numbers /  size of col * line with numbers
class Board:
    def __init__(self,filename):
        self.numberOfRows = 0
        self.numberOfColumns = 0
        self.cellBoardArr = []

        # key is number for line -1 (first line = 0)
        # value is array with numbers
        # self.rowNumArray = { } # dictionary
        # self.colNumArray = { } # dictionary



        self.rowNumArray = []
        self.colNumArray = []

        # Lower them only when set x or complete full black number
        self.rowLeft = [] # number of cells that is still not set
        self.colLeft = [] # number of cells that is still not set




        file = open(filename,"r")
        line = file.readline()
        parts = line.split()
        self.numberOfRows = int(parts[0])
        self.numberOfColumns = int(parts[1])
        # adds all needed cell objects in 2D array
        for i in range(self.numberOfRows):
            cellRow = []
            for j in range(self.numberOfColumns):
                cellRow.append(Cell()) # adds cell to row
            self.cellBoardArr.append(cellRow) # adds row with cells  to 2D array


        # adds all needed row numbers in array
        for i in range(self.numberOfRows): # i from 0 to numberOfRows
            line = file.readline()
            parts = line.split()
            self.rowNumArray.append(parts)
            self.rowLeft.append(self.numberOfRows)
            # self.rowNumArray[i] = parts

        # adds all needed column numbers in array
        for i in range(self.numberOfColumns):
            line = file.readline()
            parts = line.split()
            self.colNumArray.append(parts)
            self.colLeft.append(self.numberOfColumns)
            # self.colNumArray[i] = parts


        file.close()


    # check if there is 0
    def throughRowColumnZero(self):
        for i in range(self.numberOfRows):
            # for rowNum in self.rowNumArray[i]:
            # if self.rowNumArray[i][0] == 0: # if there is no black cells in this row
            if int(self.rowNumArray[i][0]) == 0: # if there is no black cells in this row
                self.rowLeft[i] -= 1
                for cell in self.cellBoardArr[i]:
                    cell.setx()
                    self.rowLeft[i] -= 1

        for i in range(self.numberOfColumns):
            # for rowNum in self.colNumArray[i]:
            # if self.colNumArray[i][0] == 0: # if there is no black cells in this row
            if int(self.colNumArray[i][0]) == 0: # if there is no black cells in this row
                for row in self.cellBoardArr:
                    row[i].setx()
                    self.colLeft[i] -= 1


    # if there is spaces where not enough space for any number in array
    # Ex.:  5  |x|_|x|x| | | | | |x|x|x|x|x|
    # make it x
    # def checkIfSetXpossible(self):
    #     print("HEI")



    # check if col or row have only one number
    def throughRowColumnOneNumber(self):
        for i in range(self.numberOfRows):
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






        for i in range(self.numberOfColumns):
            if len(self.colNumArray[i]) == 1:  # if only one number
                # number of spaces between each side the rest is black on the middle
                eachSide = self.colLeft[i] - int(self.colNumArray[i][0])
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





    # writes the board in terminal
    def paint(self):
        for row in self.cellBoardArr:
            for cell in row:
                print(cell, end = " ")
            print()

        #
        # # writes the board in terminal
        # def paint(self):
        #     for i in range(self.numberOfColumns+1):
        #         if i == 0:
        #             print(" ", end = " ")
        #         else:
        #             print(i, end = " ")
        #     print()
        #     rowNumber = 1
        #     for row in self.cellBoardArr:
        #         print(rowNumber, end = "  ")
        #         rowNumber += 1
        #         for cell in row:
        #             print(cell, end = " ")
        #         print()
