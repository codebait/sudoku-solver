package com.codebait.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class SudokuApp {

  public static void main(String[] args) throws IOException {

    var systemResourceAsStream = ClassLoader.getSystemResource("sudoku.txt");

    try (Stream<String> lines = Files.lines(Paths.get(systemResourceAsStream.getPath()))) {
      int[][] ints = lines
          .map(s -> s.split(""))
          .map(s -> Stream.of(s).mapToInt(Integer::parseInt).toArray())
          .toArray(int[][]::new);
      SudokuSolver sudokuSolver = new SudokuSolver(ints);
      int[][] solve = sudokuSolver.solve();
      SudokuPrinter sudokuPrinter = new SudokuPrinter();
      sudokuPrinter.print(solve);
    }

  }
}
