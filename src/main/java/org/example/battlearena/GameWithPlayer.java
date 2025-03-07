package org.example.battlearena;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameWithPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille de jeu
        GameGrid gameGrid = new GameGrid();

        // Création d'un joueur
        Player player = new Player("Joueur 1", 100, 0, 0, 0);

        // Création d'un conteneur pour superposer le joueur sur une case
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());

        // Ajout du joueur à la position (0, 0)
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());

        // Création de la scène avec la grille
        Scene scene = new Scene(gameGrid.getGrid(), 600, 600);
        primaryStage.setTitle("Grille avec Joueur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
