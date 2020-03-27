package com.codebait.sudoku;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SudokuSolverTest {

  @Test
  void shouldReturnSolvedBoard() {
    // given
    int[][] inputBoard =
        {
            {0, 1, 0, 0, 6, 0, 0, 7, 2},
            {0, 5, 0, 0, 9, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 8, 0, 1, 9, 0, 0, 0},
            {0, 0, 2, 0, 0, 4, 5, 0, 0},
            {4, 0, 0, 6, 8, 5, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 7},
            {8, 0, 0, 4, 0, 0, 0, 6, 0},
            {0, 0, 6, 0, 0, 0, 0, 0, 0}
        };
    SudokuSolver out = new SudokuSolver(inputBoard);
    // when
    int[][] solved = out.solve();
    // then
    int[][] outputBoard =
        {
            {9, 1, 3, 5, 6, 8, 4, 7, 2},
            {2, 5, 4, 1, 9, 7, 6, 3, 8},
            {6, 8, 7, 3, 4, 2, 9, 5, 1},
            {5, 3, 8, 2, 1, 9, 7, 4, 6},
            {1, 6, 2, 7, 3, 4, 5, 8, 9},
            {4, 7, 9, 6, 8, 5, 1, 2, 3},
            {3, 4, 5, 9, 2, 6, 8, 1, 7},
            {8, 9, 1, 4, 7, 3, 2, 6, 5},
            {7, 2, 6, 8, 5, 1, 3, 9, 4}
        };
    int[] expected = Arrays.stream(outputBoard).flatMapToInt(Arrays::stream).toArray();
    int[] actual = Arrays.stream(solved).flatMapToInt(Arrays::stream).toArray();
    Assertions.assertArrayEquals(expected, actual);

  }


}
