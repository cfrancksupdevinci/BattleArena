package org.example.battlearena;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Chest {
  private int row;
  private int col;
  private Rectangle chestRectangle;

  public Chest(int row, int col) {
    this.row = row;
    this.col = col;
    this.chestRectangle = createChestRectangle(); // Crée le visuel du coffre
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Rectangle getChestRectangle() {
    return chestRectangle;
  }

  // Créer un rectangle doré pour représenter le coffre
  private Rectangle createChestRectangle() {
    Rectangle rectangle = new Rectangle(30, 30); // Taille du coffre
    rectangle.setFill(Color.GOLD); // Couleur dorée
    rectangle.setStroke(Color.BLACK); // Bordure noire
    return rectangle;
  }
}
