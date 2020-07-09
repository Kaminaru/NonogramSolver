from cell import Cell
from row import Row
from column import Column
from number import Number
# FILE FORMAT: size of row / size of column
# size of row * line with numbers /  size of col * line with numbers
class Board:
    def __init__(self,filename):
        self.numberOfRows = 0
        self.numberOfColumns = 0

        self.rowsArray = [] # array with Row objects
        self.columnsArray = [] # array with Column objects

        file = open(filename,"r")
        line = file.readline() # first line with row and col number
        parts = line.split()
        self.numberOfRows = int(parts[0])
        self.numberOfColumns = int(parts[1])

        # adds needed cells to each row object and also fixing row numbers
        for i in range(self.numberOfRows):
            row = Row(self.numberOfColumns, i) # because row from left to right
            for j in range(self.numberOfColumns):
                row.cellsArr.append(Cell()) # adds cell to row
            self.rowsArray.append(row)
            parts = file.readline().split() # array
            # makes array with number objects and sends it to array in Row object
            numArr = []
            for num in parts:
                numArr.append(Number(int(num)))
            row.numbersArr = numArr


        # adds needed cells to each column object and also fixing col numbers
        for i in range(self.numberOfColumns):
            column = Column(self.numberOfRows, i) # because col from up to down
            for j in range(self.numberOfRows):
                column.cellsArr.append(self.rowsArray[j].cellsArr[i])
            self.columnsArray.append(column)
            parts = file.readline().split() # array
            # makes array with number objects and sends it to array in Row object
            numArr = []
            for num in parts:
                numArr.append(Number(int(num)))
            column.numbersArr = numArr

        file.close()


    # check if there is 0, runs once at start (may delete it later or merge with smth else)
    def throughRowColumnZero(self):
        for row in self.rowsArray:
            if row.numbersArr[0].ID == 0:
                for col in self.columnsArray:
                    col.cellsArr[row.ID].setx() # put needed cell as 'x'
                    col.numLeft -= 1
                row.numLeft = 0 # because whole row is 0

        for col in self.columnsArray:
            if col.numbersArr[0].ID == 0:
                for row in self.rowsArray:
                    if not row.cellsArr[col.ID].isSet(): # if stil not set
                        row.cellsArr[col.ID].setx()
                        row.numLeft -= 1
                    row.numLeft = 0 # because whole column is 0




    # !!!!!!!! PROBLEM here is that i don't care about black cells from before
    # may need to fix it !!!!!!!!!!!!!!

    # First attempt for basic solving algorithm for each row and column
    def basicSolution(self):
        for row in self.rowsArray:
            # key is index(position); (position from 0 to lengthOfRow - 1)
            # value is number; because there can be same numbers so we cant use number as key
            # but we know that index can't be the same in our situation
            fromLeft = {}
            index = 0 # index in number array
            wNum = row.numbersArr[index] # number object we checking right now
            numCopy = wNum.ID # will change it while go through row
            if numCopy == 0: # if row have only number 0. But i already checked it before.
                continue  # goes to next row

            for i in range(self.numberOfColumns): # from left to right
                if numCopy == 0: # goes to next number if it can
                    index += 1
                    if len(row.numbersArr) < index+1: # if there is no more numbers
                        break # go out from for loop

                    wNum = row.numbersArr[index] # goes to next number
                    numCopy = wNum.ID
                elif row.cellsArr[i].isX():
                    # resets the number we working with
                    numCopy = wNum.ID
                else: # if black or white cell
                    numCopy -= 1
                    if numCopy == 0: # if we found enough not used cells in row for number of black cells
                        # put found position for number in row
                        startPos = i - wNum.ID + 1
                        for k in range(wNum.ID): # adds all indexes that is black (value is number)
                            fromLeft[startPos+k] = wNum


            fromRight = {} # Library
            index = len(row.numbersArr)-1 # start at last index in number array
            wNum = row.numbersArr[index] # number object we checking right now
            numCopy = wNum.ID # will change it while go through row
            for i in range(self.numberOfColumns-1, -1, -1): # from right to left
                if numCopy == 0: # goes to next number if it can
                    index -= 1
                    if index == -1: # if there is no more numbers
                        break # go out from for loop

                    wNum = row.numbersArr[index] # goes to next number
                    numCopy = wNum.ID
                elif row.cellsArr[i].isX():
                    # resets the number we working with
                    numCopy = wNum.ID
                else: # if black or white cell
                    numCopy -= 1
                    if numCopy == 0: # if we found enough not used cells in row for number of black cells
                        # put found position for number in row
                        startPos = i
                        for k in range(wNum.ID): # adds all indexes that is black (value is number)
                            fromRight[startPos+k] = wNum


            # compare two arrays and check if black from both sides is for the same number
            # so make it "black"
            for i in range(self.numberOfColumns): #index
                if i in fromLeft: # if index (key) in fromLeft
                    if i in fromRight:  # if same index (key) in fromRight
                        # now we need to check if number is the same or else do nothing
                        # (because same number must be black for left and right side)
                        if fromLeft[i] == fromRight[i]: # if both numbers is the same number object
                            row.cellsArr[i].setblack()





        for column in self.columnsArray:
            # key is index(position); (position from 0 to lengthOfRow - 1)
            # value is number; because there can be same numbers so we cant use number as key
            # but we know that index can't be the same in our situation
            fromTop = {}
            index = 0 # index in number array
            wNum = column.numbersArr[index] # number we checking right now
            numCopy = wNum.ID # will change it while go through column
            if numCopy == 0: # if column have only number 0. But i already checked it before.
                continue  # goes to next column

            for i in range(self.numberOfRows): # from top to bottom
                if numCopy == 0: # goes to next number if it can
                    index += 1
                    if len(column.numbersArr) < index+1: # if there is no more numbers
                        break # go out from for loop

                    wNum = column.numbersArr[index] # goes to next number
                    numCopy = wNum.ID
                elif column.cellsArr[i].isX():
                    # resets the number we working with
                    numCopy = wNum.ID
                else: # if black or white cell
                    numCopy -= 1
                    if numCopy == 0: # if we found enough not used cells in column for number of black cells
                        # put found position for number in column

                        startPos = i - wNum.ID + 1
                        for k in range(wNum.ID): # adds all indexes that is black (value is number)
                            fromTop[startPos+k] = wNum



            fromBottom = {} # Library
            index = len(column.numbersArr)-1 # start at last index in number array
            wNum = column.numbersArr[index] # number we checking right now
            numCopy = wNum.ID # will change it while go through column
            for i in range(self.numberOfColumns-1, -1, -1): # from bottom to top
                if numCopy == 0: # goes to next number if it can
                    index -= 1
                    if index == -1: # if there is no more numbers
                        break # go out from for loop

                    wNum = column.numbersArr[index] # goes to next number
                    numCopy = wNum.ID
                elif column.cellsArr[i].isX():
                    # resets the number we working with
                    numCopy = wNum.ID
                else: # if black or white cell
                    numCopy -= 1
                    if numCopy == 0: # if we found enough not used cells in column for number of black cells
                        # put found position for number in column
                        startPos = i
                        for k in range(wNum.ID): # adds all indexes that is black (value is number)
                            fromBottom[startPos+k] = wNum



            # compare two arrays and check if black from both sides is for the same number
            # so make it "black"
            for i in range(self.numberOfColumns): #index
                if i in fromTop: # if index (key) in fromTop
                    if i in fromBottom:  # if same index (key) in fromBottom
                        # now we need to check if number is the same or else do nothing
                        # (because same number must be black for top and bottom side)
                        if fromTop[i] == fromBottom[i]:
                            column.cellsArr[i].setblack()



    # function that checks if any 
    def checkIfNumberisFound(self):







    # if there is spaces where not enough space for any number in array
    # Ex.:  5  |x|_|x|x| | | | | |x|x|x|x|x|
    # make it x
    # def checkIfSetXpossible(self):
    #     print("HEI")





    # # check if col or row have only one number
    # def throughRowColumnOneNumber(self):
    #     for i in range(self.numberOfRows):
    #         if len(self.rowNumArray[i]) == 1:  # if only one number
    #             # number of spaces between each side the rest is black on the middle
    #             eachSide = self.rowLeft[i] - int(self.rowNumArray[i][0])
    #             if eachSide == 0: # if all free cells is black
    #                 for cell in self.cellBoardArr[i]:
    #                     if cell.isSet() == False: # for all x set
    #                         cell.setblack()
    #                         self.rowLeft[i] -= 1
    #             elif self.rowLeft[i] - 2*eachSide > 0: # if there is new black spaces we can set
    #                 #probles here is that it is possible to have something like:
    #                 # 5 |x| |x|x|x| | | | | |x|x|
    #                 # and algorithm will think that ther first empty position is
    #                 # part of the "line", in other words 6, so to prevent it:
    #                 num = 0
    #                 start = 0 # to know where the last x's ends
    #                 for k in range(len(self.cellBoardArr[i])):
    #                     if self.cellBoardArr[i][k].isX() == True:
    #                         if num > 0: # if we found the situation that i mentioned before
    #                             # we will set previous not needed spaces to X
    #                             while num != 0:
    #                                 self.cellBoardArr[i][k-num].setx()
    #                                 self.rowLeft[i] -= 1
    #                                 num -= 1
    #                     else:
    #                         if num == 0: # change start position if needed
    #                             start = k
    #                         num += 1
    #
    #                 # adds needed ammount of black cell
    #                 nBlacksToPaint = self.rowLeft[i] - 2*eachSide
    #                 for k in range(start+eachSide, start+eachSide+nBlacksToPaint):
    #                     self.cellBoardArr[i][k].setblack()
    #
    #
    #
    #
    #
    #
    #     for i in range(self.numberOfColumns):
    #         if len(self.colNumArray[i]) == 1:  # if only one number
    #             # number of spaces between each side the rest is black on the middle
    #             eachSide = self.colLeft[i] - int(self.colNumArray[i][0])
    #             if eachSide == 0: # if all free cells is black
    #                 for cell in self.cellBoardArr[i]:
    #                     if cell.isSet() == False: # for all x set
    #                         cell.setblack()
    #                         self.rowLeft[i] -= 1
    #             elif self.rowLeft[i] - 2*eachSide > 0: # if there is new black spaces we can set
    #                 #probles here is that it is possible to have something like:
    #                 # 5 |x| |x|x|x| | | | | |x|x|
    #                 # and algorithm will think that ther first empty position is
    #                 # part of the "line", in other words 6, so to prevent it:
    #                 num = 0
    #                 start = 0 # to know where the last x's ends
    #                 for k in range(len(self.cellBoardArr[i])):
    #                     if self.cellBoardArr[i][k].isX() == True:
    #                         if num > 0: # if we found the situation that i mentioned before
    #                             # we will set previous not needed spaces to X
    #                             while num != 0:
    #                                 self.cellBoardArr[i][k-num].setx()
    #                                 self.rowLeft[i] -= 1
    #                                 num -= 1
    #                     else:
    #                         if num == 0: # change start position if needed
    #                             start = k
    #                         num += 1
    #
    #                 # adds needed ammount of black cell
    #                 nBlacksToPaint = self.rowLeft[i] - 2*eachSide
    #                 for k in range(start+eachSide, start+eachSide+nBlacksToPaint):
    #                     self.cellBoardArr[i][k].setblack()
    #
    #
    #




    # writes the board in terminal
    def paint(self):
        print()
        for row in self.rowsArray:
            row.printOut()
