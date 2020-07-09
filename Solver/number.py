class Number:
    def __init__(self, number):
        self.ID = number
        self.found = False
        self.start = -1 # start index
        self.end = -1 # end index

    def setFound(self, start, end):
        self.found = True
        self.start = start
        self.end = end
