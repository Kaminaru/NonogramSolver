class Cell:
    def __init__(self):
        self._set = False # cell is set eather black or x
        self._black = False
        self._x = False


    def setblack(self):
        self._set = True
        self._black = True

    def setx(self):
        self._set = True
        self._x = True
