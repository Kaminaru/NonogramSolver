from cell import Cell
class Column:
    def __init__(self, left, id):
        self.ID = id # from 0
        self.numbersArr = []
        self.cellsArr = []
        self.numLeft = left # number of cells that is not yet set

    def printOut(self):
        for cell in self.cellsArr:
            print(cell)
