package org.example.battlearena.model;

public class Position {
    private int GRID_SIZE = 10;
    private String ERROR_MESSAGE = "X cannot be greater than " + GRID_SIZE + " or less than 0";

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (x >= GRID_SIZE || x < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.x = x;
    }

    public void setY(int y) {
        if (y >= GRID_SIZE || y < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.y = y;
    }
}
