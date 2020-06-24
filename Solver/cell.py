class Cell:
    def __init__(self):
        self.set = False # cell is set eather black or x
        self.black = False
        self.x = False

        # maybe add:
        #            numberIamIn
        #            numbers from row array
        #            numbers from col array

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

    def isBlack(self):
        return self.black

    def __repr__(self): # write out when i print cell class
        if self.set == False:
            return " "
        else:
            if self.black == True:
                return "#"
            return "x"
