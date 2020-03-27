package com.codebait.sudoku;

class SudokuPrinter {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";

  private static final int EMPTY_FIELD = 0;

  void print(int[][] board) {
    for (int[] row : board) {
      for (int value : row) {
        printNumber(value);
      }
      System.out.println();
    }

  }

  private void printNumber(int anInt) {
    if (anInt == EMPTY_FIELD) {
      System.out.print(ANSI_RED + "*  " + ANSI_RESET);
    } else {
      System.out.print(ANSI_GREEN + anInt + "  " + ANSI_RESET);
    }
  }

}
