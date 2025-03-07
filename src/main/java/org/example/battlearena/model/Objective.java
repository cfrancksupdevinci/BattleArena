package org.example.battlearena.model;

public class Objective {
  private int row;
  private int col;
  private String name;

  public Objective(String name, int row, int col) {
    this.name = name;
    this.row = row;
    this.col = col;
  }

  public String getName() {
    return name;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
