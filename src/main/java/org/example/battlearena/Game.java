package org.example.battlearena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille de jeu
        GameGrid gameGrid = new GameGrid();

        // Création et placement des joueurs
        Player player = new Player("Joueur 1", 100, 0, 0, 0);
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());

        // Création et placement des obstacles
        Obstacle obstacle1 = new Obstacle(2, 3);
        Obstacle obstacle2 = new Obstacle(4, 5);
        gameGrid.addObstacle(obstacle1);
        gameGrid.addObstacle(obstacle2);

        // Création de la scène avec la grille
        Scene scene = new Scene(gameGrid.getGrid(), 600, 600);
        primaryStage.setTitle("Battle Arena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
