package org.example.battlearena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {

    private GameGrid gameGrid;
    private Player player;

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille de jeu
        gameGrid = new GameGrid();

        // Création et placement des joueurs
        player = new Player("Joueur 1", 100, 0, 0, 0);
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());

        // Création et placement des obstacles
        Obstacle obstacle1 = new Obstacle(2, 3);
        Obstacle obstacle2 = new Obstacle(4, 5);
        gameGrid.addObstacle(obstacle1);
        gameGrid.addObstacle(obstacle2);

        // Gestion des déplacements avec les flèches du clavier
        Scene scene = new Scene(gameGrid.getGrid(), 600, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                movePlayer(0, -1); // Déplacer vers le haut
            } else if (event.getCode() == KeyCode.RIGHT) {
                movePlayer(1, 0); // Déplacer vers la droite
            } else if (event.getCode() == KeyCode.DOWN) {
                movePlayer(0, 1); // Déplacer vers le bas
            } else if (event.getCode() == KeyCode.LEFT) {
                movePlayer(-1, 0); // Déplacer vers la gauche
            }
        });

        primaryStage.setTitle("Battle Arena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Déplace le joueur si la nouvelle position est valide (dans les limites et
     * sans obstacle).
     */
    public void movePlayer(int deltaX, int deltaY) {
        int newRow = player.getRow() + deltaY;
        int newCol = player.getCol() + deltaX;

        // Vérifier que la nouvelle position est dans les limites de la grille
        if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
            // Vérifier qu'il n'y a pas d'obstacle à la nouvelle position
            boolean isBlocked = false;
            for (Obstacle obstacle : getObstacles()) {
                if (obstacle.getRow() == newRow && obstacle.getCol() == newCol) {
                    isBlocked = true;
                    break;
                }
            }

            // Si la position n'est pas bloquée par un obstacle, déplacer le joueur
            if (!isBlocked) {
                // Mettre à jour la position du joueur
                player.setRow(newRow);
                player.setCol(newCol);

                // Mettre à jour l'affichage de la grille (déplacer le joueur sur la grille)
                updatePlayerPosition();
            }
        }
    }

    /**
     * Met à jour la position graphique du joueur sur la grille.
     */
    private void updatePlayerPosition() {
        // Supprimer l'ancien avatar du joueur
        gameGrid.getGrid().getChildren().remove(player.getAvatar());

        // Ajouter l'avatar à la nouvelle position
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());
    }

    /**
     * Retourne la liste des obstacles (facilement accessible pour vérification).
     */
    private Obstacle[] getObstacles() {
        return new Obstacle[] {
                new Obstacle(2, 3),
                new Obstacle(4, 5)
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
