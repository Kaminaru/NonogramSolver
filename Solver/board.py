from cell import Cell

# FILE FORMAT: size of row / size of column
# size of row * line with numbers /  size of col * line with numbers
class Board:
    def __init__(self,filename):
        self.numberOfRows = 0
        self.numberOfColumns = 0
        self.cellBoardArr = []

        self.rowNumArray = []
        self.colNumArray = []

        file = open(filename,"r")
        line = file.readline()
        parts = line.split()
        numberOfRows = int(parts[0])
        numberOfColumns = int(parts[0])
        # adds all needed cell objects in 2D array
        for i in range(numberOfRows):
            cellRow = []
            for j in range(numberOfColumns):
                cellRow.append(Cell()) # adds cell to row
            self.cellBoardArr.append(cellRow) # adds row with cells  to 2D array

        # adds all needed row numbers in array
        for i in range(numberOfRows):
            line = file.readline()
            parts = line.split()
            self.rowNumArray.append(parts)

        # adds all needed column numbers in array
        for i in range(numberOfColumns):
            line = file.readline()
            parts = line.split()
            self.rowNumArray.append(parts)

        file.close()


    def throwRow(self):
        for rowNum in self.rowNumArray:
            if rowNum[0] == 0: # if there is no black cells in this row
                for cellBoardArr []







    # writes the board in terminal
    def paint(self):
        for row in self.cellBoardArr:
            for cell in row:
                print(cell, end =" ")
            print()
