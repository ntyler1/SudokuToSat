# Project Description

> Java SudokuToSAT reducer that receives an input sudoku puzzle via file and writes out a SAT instance file in the .cnf format. The output the reducer creates can be used as input for my other java project which solves SAT problems. Read more about the SAT problem and my other project [here](https://github.com/ntyler1/SATsolver).

> A common Sudoku puzzle of size 9x9 with 3x3 boxes translates into a SAT instance with 729 variables and approximately 12000 boolean clauses.

# Project Features

  - reads from the command line the name of a file then reads the input sudoku puzzle within the file. 
    Input puzzle files have following format (see test txt file):
     - Optional comment lines at the beginning of the file start with the character ‘c’.
     - The first data line indicates the dimensions of each box in the puzzle. i.e '3 3' indicates 3x3 
     - a sequence of 81 integers in the range from 0 to 9 (for 9x9 sudoku puzzles). These integers are the values in Sudoku grid in row-          major order, with a 0 indicating a cell that does not have a preset value.
  - times the length of computation
  - advanced array looping
  - writes a .cnf SAT instance file based on input puzzle (see temp.cnf output file)
