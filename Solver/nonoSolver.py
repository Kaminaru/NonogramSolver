from board import Board

def main():
    # filename = input("What is the filename? ")
    gameBoard = Board("Mage.txt")
    gameBoard1 = Board("Key.txt")

    # start solutions (merge functions together after)
    gameBoard.throughRowColumnZero()
    gameBoard.throughRowColumnOneNumber()

    gameBoard.paint()


    gameBoard1.throughRowColumnZero()
    gameBoard1.throughRowColumnOneNumber()

    gameBoard1.paint()

main()
