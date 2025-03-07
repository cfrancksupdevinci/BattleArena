package org.example.battlearena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {

    private GameGrid gameGrid;
    private Player player1;
    private Player player2;
    private Player player3;

    private int currentPlayerIndex = 0; // Indice du joueur actuel (tour par tour)
    private GameDisplay gameDisplay;

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille de jeu
        gameGrid = new GameGrid();

        // Création et placement des joueurs
        player1 = new Player("Joueur 1", 100, 0, 9, 9);
        StackPane player1Container = new StackPane();
        player1Container.getChildren().add(player1.getAvatar());
        gameGrid.addToGrid(player1Container, player1.getRow(), player1.getCol());

        player2 = new Player("Joueur 2", 100, 0, 0, 0); // Position initiale du joueur 2
        StackPane player2Container = new StackPane();
        player2Container.getChildren().add(player2.getAvatar());
        gameGrid.addToGrid(player2Container, player2.getRow(), player2.getCol());

        player3 = new Player("Joueur 3", 100, 0, 5, 5); // Position initiale du joueur 3
        StackPane player3Container = new StackPane();
        player3Container.getChildren().add(player3.getAvatar());
        gameGrid.addToGrid(player3Container, player3.getRow(), player3.getCol());

        // Création et placement des obstacles
        Obstacle obstacle1 = new Obstacle(2, 3);
        Obstacle obstacle2 = new Obstacle(4, 5);
        gameGrid.addObstacle(obstacle1);
        gameGrid.addObstacle(obstacle2);

        // Création de l'affichage du jeu
        gameDisplay = new GameDisplay(player1, player2, player3);

        // Organiser la grille et l'affichage dans un HBox
        HBox hbox = new HBox(10, gameGrid.getGrid(), gameDisplay.createInfoBox());
        hbox.setSpacing(20); // Espacement entre la grille et l'affichage

        // Définir la taille de la scène et ajouter le HBox
        Scene scene = new Scene(hbox, 800, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                Player currentPlayer = getCurrentPlayer();
                if (!currentPlayer.hasAttacked() && canAttack(currentPlayer)) {
                    attack(currentPlayer);
                    gameDisplay.updateTurnInfo(currentPlayer, player1, player2, player3);
                } else {
                    System.out.println(currentPlayer.getName() + " ne peut pas attaquer ce tour.");
                }
            } else if (handlePlayerMovement(event, getCurrentPlayer())) {
                getCurrentPlayer().setHasAttacked(false); // Réinitialiser l'attaque pour le prochain tour
                currentPlayerIndex = (currentPlayerIndex + 1) % 3; // Passer au joueur suivant
                gameDisplay.updateTurnInfo(getCurrentPlayer(), player1, player2, player3);
            }
        });

        primaryStage.setTitle("Battle Arena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Vérifie si un joueur peut attaquer un autre joueur.
     */
    private boolean canAttack(Player currentPlayer) {
        for (Player otherPlayer : new Player[] { player1, player2, player3 }) {
            if (otherPlayer != currentPlayer) {
                // Vérifier si le joueur est adjacent (même ligne ou même colonne)
                if ((currentPlayer.getRow() == otherPlayer.getRow()
                        && Math.abs(currentPlayer.getCol() - otherPlayer.getCol()) == 1) ||
                        (currentPlayer.getCol() == otherPlayer.getCol()
                                && Math.abs(currentPlayer.getRow() - otherPlayer.getRow()) == 1)) {
                    return true; // Un joueur est adjacent
                }
            }
        }
        return false; // Aucun joueur adjacent
    }

    /**
     * Effectue une attaque sur un joueur adjacent.
     * L'attaque peut se faire dans toutes les directions adjacentes (haut, bas,
     * gauche, droite).
     */
    private void attack(Player attacker) {
        if (attacker.hasAttacked()) {
            System.out.println(attacker.getName() + " a déjà attaqué ce tour.");
            return;
        }

        for (Player target : new Player[] { player1, player2, player3 }) {
            if (target != attacker) {
                // Vérifier si le joueur cible est adjacent (haut, bas, gauche, droite)
                if ((attacker.getRow() == target.getRow() && Math.abs(attacker.getCol() - target.getCol()) == 1) ||
                        (attacker.getCol() == target.getCol() && Math.abs(attacker.getRow() - target.getRow()) == 1)) {

                    // Réduire la santé du joueur cible
                    target.setHealth(target.getHealth() - 10);

                    // Ajouter de l'XP à l'attaquant
                    attacker.addXP(10);

                    // Marquer l'attaquant comme ayant attaqué ce tour
                    attacker.setHasAttacked(true);

                    // Afficher une notification
                    System.out.println(attacker.getName() + " attaque " + target.getName() + " et gagne 10 XP!");
                    return;
                }
            }
        }

        System.out.println(attacker.getName() + " n'a pas de cible à attaquer.");
    }

    /**
     * Retourne le joueur dont c'est le tour actuel.
     */
    private Player getCurrentPlayer() {
        switch (currentPlayerIndex) {
            case 0:
                return player1;
            case 1:
                return player2;
            case 2:
                return player3;
            default:
                return player1;
        }
    }

    // Méthodes pour gérer les déplacements et l'interaction avec la grille
    private boolean handlePlayerMovement(javafx.scene.input.KeyEvent event, Player player) {
        int deltaX = 0;
        int deltaY = 0;

        // Déterminer la direction du mouvement
        if (event.getCode() == javafx.scene.input.KeyCode.UP) {
            deltaX = 0;
            deltaY = -1;
        } else if (event.getCode() == javafx.scene.input.KeyCode.RIGHT) {
            deltaX = 1;
            deltaY = 0;
        } else if (event.getCode() == javafx.scene.input.KeyCode.DOWN) {
            deltaX = 0;
            deltaY = 1;
        } else if (event.getCode() == javafx.scene.input.KeyCode.LEFT) {
            deltaX = -1;
            deltaY = 0;
        }

        // Vérifier si le mouvement est valide
        if (isMoveValid(player, deltaX, deltaY)) {
            movePlayer(player, deltaX, deltaY); // Déplacer le joueur
            getCurrentPlayer().setHasAttacked(false); // Réinitialiser l'état d'attaque pour le joueur actuel

            return true; // Mouvement valide, passer au joueur suivant
        } else {
            return false; // Mouvement invalide, reste au tour actuel
        }
    }

    private boolean isMoveValid(Player player, int deltaX, int deltaY) {
        int newRow = player.getRow() + deltaY;
        int newCol = player.getCol() + deltaX;

        // Vérifier que la nouvelle position est dans les limites de la grille
        if (newRow < 0 || newRow >= 10 || newCol < 0 || newCol >= 10) {
            return false; // Hors limites
        }

        // Vérifier qu'il n'y a pas d'obstacle à la nouvelle position
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.getRow() == newRow && obstacle.getCol() == newCol) {
                return false; // Il y a un obstacle
            }
        }

        // Vérifier qu'il n'y a pas d'autre joueur à la nouvelle position
        if (isPlayerAtPosition(newRow, newCol)) {
            return false; // Collision avec un autre joueur
        }

        return true; // Le mouvement est valide
    }

    private boolean isPlayerAtPosition(int row, int col) {
        return (player1.getRow() == row && player1.getCol() == col) ||
                (player2.getRow() == row && player2.getCol() == col) ||
                (player3.getRow() == row && player3.getCol() == col);
    }

    private void movePlayer(Player player, int deltaX, int deltaY) {
        int newRow = player.getRow() + deltaY;
        int newCol = player.getCol() + deltaX;

        player.setRow(newRow);
        player.setCol(newCol);

        // Mettre à jour l'affichage de la grille (déplacer le joueur sur la grille)
        updatePlayerPosition(player);
    }

    private void updatePlayerPosition(Player player) {
        // Supprimer l'ancien avatar du joueur
        gameGrid.getGrid().getChildren().remove(player.getAvatar());

        // Ajouter l'avatar à la nouvelle position
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());
    }

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
