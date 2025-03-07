package org.example.battlearena;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle {
    private int row;
    private int col;
    private Rectangle shape;

    public Obstacle(int row, int col) {
        this.row = row;
        this.col = col;

        // Représentation graphique de l'obstacle
        this.shape = new Rectangle(50, 50); // Taille de la cellule
        this.shape.setFill(Color.DARKGRAY); // Couleur de l'obstacle
        this.shape.setStroke(Color.BLACK); // Bordure noire
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Rectangle getShape() {
        return shape;
    }
}

