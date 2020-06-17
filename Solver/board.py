from cell import Cell

# FILE FORMAT: size of row / size of column
# size of row * line with numbers /  size of col * line with numbers
class Board:
    def __init__(self,filename):
        numberOfRows = 0
        numberOfColumns = 0
        cellBoardArr = []

        file = open(filename,"r")
        line = file.readline()
        parts = line.split()
        numberOfRows = parts[0]
        numberOfColumns = parts[0]

        positionLine = 0;
        for i in range(numberOfRows):
            line = file.readline()
            parts = line.split()



        file.close()
