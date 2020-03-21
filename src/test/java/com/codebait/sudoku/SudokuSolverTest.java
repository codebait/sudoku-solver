package com.codebait.sudoku;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.linkText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

class SudokuSolverTest {

  private ChromeDriver chromeDriver;
  private String url;

  @BeforeEach
  void setUp() {
    chromeDriver = new ChromeDriver();
    url = "https://sudoku.com/";
  }

  @Test
  void shouldName() throws InterruptedException {
    chromeDriver.get(url);
    WebDriverWait wait = new WebDriverWait(chromeDriver, 5);
    wait.until(a -> a.findElement(cssSelector(".difficulty-label-text"))).click();
    wait.until(a -> a.findElement(linkText("Expert"))).click();
    wait.until(a -> a.findElement(
        cssSelector(".game-row:nth-child(1) > .game-cell:nth-child(1) .pencil-grid-cell-3")
    )).click();

    String clearGrid = getGrid();

    SudokuSolver sudokuSolver = new SudokuSolver(getBoard(clearGrid));
    int[][] solve = sudokuSolver.solve();

    insertOnPage(solve);


  }

  private void insertOnPage(int[][] board) {
    Actions make = new Actions(chromeDriver);
    for (int[] ints : board) {
      for (int anInt : ints) {
        make.sendKeys(String.valueOf(anInt), Keys.RIGHT);
      }
      for (int i = 0; i < 9; i++) {
        make.sendKeys(Keys.LEFT);
      }
      make.sendKeys(Keys.DOWN);
    }
    make.perform();
  }


  private String getGrid() {
    WebDriverWait wait = new WebDriverWait(chromeDriver, 5);
    Actions make = new Actions(chromeDriver);
    var stream = IntStream.range(0, 10).mapToObj(o -> Keys.chord("0"))
        .flatMap(s -> Stream.<CharSequence>of(s, Keys.RIGHT));

    make.sendKeys(stream.toArray(CharSequence[]::new)).perform();
    wait.until(a -> a.findElement(
        cssSelector(".game-row:nth-child(1) > .game-cell:nth-child(1) .pencil-grid-cell-3")
    )).click();
    return chromeDriver.getLocalStorage().getItem("clearGrid");
  }


  private int[][] getBoard(String clearGrid) {
    int[][] board = new int[9][9];
    Pattern compile = Pattern.compile("(\\d,?){9}");
    Matcher matcher = compile.matcher(clearGrid);
    for (int i = 0; matcher.find(); i++) {
      Pattern digit = Pattern.compile("(\\d),?");
      Matcher matcher1 = digit.matcher(matcher.group(0));
      for (int j = 0; matcher1.find(); j++) {
        int num = Integer.parseInt(matcher1.group(1));
        board[i][j] = num;
      }

    }
    return board;
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    Thread.sleep(5000);
    chromeDriver.close();
  }
}
