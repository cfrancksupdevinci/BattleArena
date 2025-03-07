package org.example.battlearena;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameGrid {
    private static final int GRID_SIZE = 10;
    private final GridPane grid;

    public GameGrid() {
        grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        // Création des cellules de la grille
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label cell = new Label();
                cell.setMinSize(50, 50);
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
                grid.add(cell, col, row);
            }
        }
    }

    /**
     * Retourne la grille pour l'intégrer dans une interface.
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Ajoute un élément (par ex. un joueur) sur la grille à une position donnée.
     */
    public void addToGrid(StackPane content, int row, int col) {
        grid.add(content, col, row); // Les colonnes et lignes dans GridPane
    }

    /**
     * Supprime un élément de la grille (facultatif si besoin).
     */
    public void removeFromGrid(StackPane content) {
        grid.getChildren().remove(content);
    }

    /**
     * Ajoute un obstacle sur la grille à une position donnée.
     */
    public void addObstacle(Obstacle obstacle) {
        StackPane obstacleContainer = new StackPane();
        obstacleContainer.getChildren().add(obstacle.getShape());
        addToGrid(obstacleContainer, obstacle.getRow(), obstacle.getCol());
    }

}
