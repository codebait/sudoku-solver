package com.codebait.sudoku;

import java.util.Arrays;
import java.util.stream.Stream;

class SudokuSolver {


  public static final String ANSI_RESET = "\u001B[0m";
  private final int[][] board;
  private final int[][] boardCopy;

  public SudokuSolver(int[][] board) {
    this.board = copy(board);
    this.boardCopy = copy(board);
  }

  private int[][] copy(int[][] board) {
    return Stream.of(board).map(array -> Arrays.copyOf(array, array.length)).toArray(int[][]::new);
  }

  public int[][] solve() {
    printOriginBoard();
    solveRec();
    printSolvedBoard();
    return copy(boardCopy);
  }

  private boolean solveRec() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (boardCopy[i][j] == 0) {
          for (int k = 1; k < 10; k++) {
            if (checkPossible(i, j, k)) {
              boardCopy[i][j] = k;
              if (solveRec()) {
                return true;
              }
              boardCopy[i][j] = 0;

            }
          }
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkPossible(int x, int y, int value) {
    if (boardCopy[x][y] != 0) {
      return false;
    }
    for (int i = 0; i < 9; i++) {
      if (boardCopy[i][y] == value) {
        return false;
      }
      if (boardCopy[x][i] == value) {
        return false;
      }
    }

    int startX = x - x % 3;
    int startY = y - y % 3;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (boardCopy[startX + i][startY + j] == value) {
          return false;
        }
      }
    }
    return true;
  }

  private void printOriginBoard() {
    System.out.println("--------- ORIGIN -----------");
    for (int[] ints : board) {
      for (int anInt : ints) {
        printNumber(anInt);
      }
      System.out.println();
    }
  }

  private void printSolvedBoard() {
    System.out.println("--------- SOLVED -----------");
    for (int[] ints : boardCopy) {
      for (int anInt : ints) {
        printNumber(anInt);
      }
      System.out.println();
    }
  }

  private void printNumber(int anInt) {
    if (anInt == 0) {
      printInColor("*  ", Color.RED);
    } else {
      printInColor(anInt + "  ", Color.GREEN);
    }
  }

  private void printInColor(String text, Color color) {
    System.out.print(color.getAnsiColor() + text + ANSI_RESET);
  }
}
