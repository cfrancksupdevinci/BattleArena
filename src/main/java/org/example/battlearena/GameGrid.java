package org.example.battlearena;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameGrid extends Application {

    private static final int GRID_SIZE = 10; // Taille de la grille (10x10)

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); // Marges autour de la grille
        grid.setHgap(5); // Espacement horizontal entre les cases
        grid.setVgap(5); // Espacement vertical entre les cases

        // Remplissage de la grille avec des cellules
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label cell = new Label();
                cell.setMinSize(50, 50); // Taille minimale des cellules
                cell.setStyle("-fx-border-color: black; -fx-background-color: lightgray;"); // Style CSS
                grid.add(cell, col, row); // Ajouter la cellule à la position (col, row)
            }
        }

        // Configuration de la scène
        Scene scene = new Scene(grid, 600, 600); // Taille de la fenêtre
        primaryStage.setTitle("Grille de Jeu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
