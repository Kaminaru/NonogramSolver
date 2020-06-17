CFLAGS = -g -std=gnu11 -Wall -Wextra
VFLAGS = --track-origins=yes --leak-check=full --show-leak-kinds=all
start:
	@clear
	gcc $(CFLAGS) nanoSolver.c -o nanoSolver
	valgrind $(VFLAGS) ./nanoSolver fileName.txt

run:
	@clear
	valgrind $(VFLAGS) ./nanoSolver fileName.txt

clean:
	rm -f nanoSolver
