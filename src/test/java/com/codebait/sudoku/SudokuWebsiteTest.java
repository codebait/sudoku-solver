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

class SudokuWebsiteTest {


  private ChromeDriver chromeDriver;
  private String url;
  private Actions actions;
  private WebDriverWait webDriverWait;

  @BeforeEach
  void setUp() {
    chromeDriver = new ChromeDriver();
    url = "https://sudoku.com/";
    actions = new Actions(chromeDriver);
    webDriverWait = new WebDriverWait(chromeDriver, 5);
  }

  @Test
  void runTest() throws InterruptedException {
    chromeDriver.get(url);
    chooseLevel("Expert");

    String clearGrid = getGrid();
    int[][] board = mapToBoard(clearGrid);

    SudokuSolver sudokuSolver = new SudokuSolver(board);
    int[][] solve = sudokuSolver.solve();

    insertOnPage(solve);


  }

  private void chooseLevel(String level) {
    webDriverWait.until(a -> a.findElement(cssSelector(".difficulty-label-text"))).click();
    webDriverWait.until(a -> a.findElement(linkText(level))).click();
    webDriverWait.until(a -> a.findElement(
        cssSelector(".game-row:nth-child(1) > .game-cell:nth-child(1) .pencil-grid-cell-3")
    )).click();
  }

  private void insertOnPage(int[][] board) {
    for (int[] ints : board) {
      for (int anInt : ints) {
        actions.sendKeys(String.valueOf(anInt), Keys.RIGHT);
      }
      for (int i = 0; i < 9; i++) {
        actions.sendKeys(Keys.LEFT);
      }
      actions.sendKeys(Keys.DOWN);
    }
    actions.perform();
  }


  private String getGrid() {
    goThrowFirstLine();
    return chromeDriver.getLocalStorage().getItem("clearGrid");
  }

  private void goThrowFirstLine() {
    var actionArray = IntStream.range(0, 10)
        .mapToObj(o -> Keys.chord("0"))
        .flatMap(s -> Stream.<CharSequence>of(s, Keys.RIGHT))
        .toArray(CharSequence[]::new);

    actions
        .sendKeys(actionArray)
        .perform();
    webDriverWait
        .until(a ->
            a.findElement(
                cssSelector(".game-row:nth-child(1) > .game-cell:nth-child(1) .pencil-grid-cell-3")
            )
        )
        .click();
  }


  private int[][] mapToBoard(String clearGrid) {
    int[][] board = new int[9][9];
    Pattern linePattern = Pattern.compile("(\\d,?){9}");
    Matcher lineMatcher = linePattern.matcher(clearGrid);
    Pattern digitPattern = Pattern.compile("(\\d),?");
    for (int i = 0; lineMatcher.find() && i < 9; i++) {
      Matcher digitMatcher = digitPattern.matcher(lineMatcher.group(0));
      for (int j = 0; digitMatcher.find() && j < 9; j++) {
        int num = Integer.parseInt(digitMatcher.group(1));
        board[i][j] = num;
      }

    }
    return board;
  }

  @AfterEach
  void tearDown() throws InterruptedException {
    Thread.sleep(5000);
    //chromeDriver.close();
  }
}
