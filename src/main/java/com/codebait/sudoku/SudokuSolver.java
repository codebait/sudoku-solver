package com.codebait.sudoku;

import java.util.Arrays;
import java.util.stream.Stream;

class SudokuSolver {


  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final int EMPTY_FIELD = 0;
  private final int[][] originBoard;
  private final int[][] workingCopy;

  public SudokuSolver(int[][] board) {
    this.originBoard = copy(board);
    this.workingCopy = copy(board);
  }

  private int[][] copy(int[][] board) {
    return Stream.of(board).map(array -> Arrays.copyOf(array, array.length)).toArray(int[][]::new);
  }

  public int[][] solve() {
    if (!solveBoard()) {
      throw new IllegalArgumentException("not possible to solve");
    }
    return copy(workingCopy);
  }

  private boolean solveBoard() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (notFilled(i, j)) {
          return solveForField(i, j);
        }
      }
    }
    return true;
  }

  private boolean solveForField(int i, int j) {
    for (int k = 1; k < 10; k++) {
      if (isPossibleToFill(i, j, k)) {
        workingCopy[i][j] = k;
        if (solveBoard()) {
          return true;
        }
        workingCopy[i][j] = EMPTY_FIELD;
      }
    }
    return false;
  }

  private boolean isPossibleToFill(int x, int y, int value) {

    return notExistInLines(x, y, value) && notExistInBox(x, y, value);
  }

  private boolean notExistInBox(int x, int y, int value) {
    int startX = x - x % 3;
    int startY = y - y % 3;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (workingCopy[startX + i][startY + j] == value) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean notExistInLines(int x, int y, int value) {
    for (int i = 0; i < 9; i++) {
      if (workingCopy[i][y] == value || workingCopy[x][i] == value) {
        return false;
      }
    }
    return true;
  }

  private boolean notFilled(int x, int y) {
    return workingCopy[x][y] == EMPTY_FIELD;
  }


}
