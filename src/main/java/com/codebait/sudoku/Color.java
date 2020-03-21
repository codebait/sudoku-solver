package com.codebait.sudoku;

enum Color {
  BLACK("\u001B[30m"),
  RED("\u001B[31m"),
  GREEN("\u001B[32m"),
  YELLOW("\u001B[33m"),
  BLUE("\u001B[34m"),
  PURPLE("\u001B[35m"),
  CYAN("\u001B[36m"),
  WHITE("\u001B[37m"),
  ;

  private final String ansiColor;

  Color(String ansiColor) {
    this.ansiColor = ansiColor;
  }

  public String getAnsiColor() {
    return ansiColor;
  }
}
