from board import Board

def main():
    # filename = input("What is the filename? ")
    gameBoard = Board("Key.txt")

    # start solutions
    gameBoard.throwRow()

    gameBoard.paint()

main()
