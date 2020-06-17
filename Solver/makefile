CFLAGS = -g -std=gnu11 -Wall -Wextra
VFLAGS = --track-origins=yes --leak-check=full --show-leak-kinds=all
start:
	@clear
	gcc $(CFLAGS) nonoSolver.c -o nonoSolver
	valgrind $(VFLAGS) ./nonoSolver Key.txt

run:
	@clear
	valgrind $(VFLAGS) ./nonoSolver Key.txt

clean:
	rm -f nanoSolver
