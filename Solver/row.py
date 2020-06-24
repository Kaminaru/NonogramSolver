from cell import Cell
class Row:
    def __init__(self, left, id):
        self.ID = id # from 0
        self.numbersArr = []
        self.cellsArr = [] # all cells that is in this row
        self.numLeft = left # number of cells that is not yet set

    def printOut(self):
        for cell in self.cellsArr:
            print(cell, end = " ")
        print()
