class Cell:

    def __init__(self):
        self.set = False # cell is set eather black or x
        self.black = False
        self.x = False


    def setblack(self):
        self.set = True
        self.black = True

    def setx(self):
        self.set = True
        self.x = True

    def isSet(self):
        return self.set # True is set

    def isX(self):
        return self.x

    def __repr__(self): # write out when i print cell class
        if self.set == False:
            return "?"
        else:
            if self.black == True:
                return "#"
            return "x"
