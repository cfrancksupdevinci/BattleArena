package org.example.battlearena;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {

    private GameGrid gameGrid;
    private Player player1;
    private Player player2;
    private Player player3;

    private boolean hasAttacked = false;

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

        // Ajout des cases spéciales
        gameGrid.addSpecialTile(new SpecialTile(2, 2));
        gameGrid.addSpecialTile(new SpecialTile(7, 7));
        gameGrid.addSpecialTile(new SpecialTile(4, 4));

        // Création de l'affichage du jeu
        gameDisplay = new GameDisplay(player1, player2, player3);

        // Organiser la grille et l'affichage dans un HBox
        HBox hbox = new HBox(10, gameGrid.getGrid(), gameDisplay.createInfoBox());
        hbox.setSpacing(20); // Espacement entre la grille et l'affichage

        // Définir la taille de la scène et ajouter le HBox
        Scene scene = new Scene(hbox, 800, 600);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                // Vérifier si le joueur peut attaquer
                if (!hasAttacked && canAttack(getCurrentPlayer())) {
                    attack(getCurrentPlayer());
                    hasAttacked = true; // Marque l'attaque comme effectuée
                    gameDisplay.updateTurnInfo(getCurrentPlayer(), player1, player2, player3);
                }
            } else if (handlePlayerMovement(event, getCurrentPlayer())) {
                currentPlayerIndex = (currentPlayerIndex + 1) % 3; // Passer au joueur suivant
                hasAttacked = false; // Réinitialiser l'indicateur d'attaque pour le joueur suivant
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
                if (currentPlayer.getRow() == otherPlayer.getRow()
                        && Math.abs(currentPlayer.getCol() - otherPlayer.getCol()) == 1) {
                    return true; // Joueur à gauche ou à droite
                } else if (currentPlayer.getCol() == otherPlayer.getCol()
                        && Math.abs(currentPlayer.getRow() - otherPlayer.getRow()) == 1) {
                    return true; // Joueur au-dessus ou en dessous
                }
            }
        }
        return false; // Aucun joueur à proximité immédiate
    }

    /**
     * Effectue l'attaque sur le joueur en face du joueur actuel.
     */
    private void attack(Player attacker) {
        for (Player target : new Player[] { player1, player2, player3 }) {
            if (target != attacker) {
                if (attacker.getRow() == target.getRow() && Math.abs(attacker.getCol() - target.getCol()) == 1) {
                    target.setHealth(target.getHealth() - 10); // Attaque sur le joueur à gauche ou à droite
                    attacker.addXP(10); // Gagne 10 XP après une attaque
                    return;
                } else if (attacker.getCol() == target.getCol() && Math.abs(attacker.getRow() - target.getRow()) == 1) {
                    target.setHealth(target.getHealth() - 10); // Attaque sur le joueur au-dessus ou en dessous
                    attacker.addXP(10); // Gagne 10 XP après une attaque
                    return;
                }
            }
        }
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

    /**
     * Gère les mouvements des joueurs et vérifie la validité.
     */
    private boolean handlePlayerMovement(javafx.scene.input.KeyEvent event, Player player) {
        int deltaX = 0;
        int deltaY = 0;

        if (event.getCode() == KeyCode.UP) {
            deltaX = 0;
            deltaY = -1;
        } else if (event.getCode() == KeyCode.RIGHT) {
            deltaX = 1;
            deltaY = 0;
        } else if (event.getCode() == KeyCode.DOWN) {
            deltaX = 0;
            deltaY = 1;
        } else if (event.getCode() == KeyCode.LEFT) {
            deltaX = -1;
            deltaY = 0;
        }

        if (isMoveValid(player, deltaX, deltaY)) {
            movePlayer(player, deltaX, deltaY);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Vérifie si le mouvement est valide.
     */
    private boolean isMoveValid(Player player, int deltaX, int deltaY) {
        int newRow = player.getRow() + deltaY;
        int newCol = player.getCol() + deltaX;

        if (newRow < 0 || newRow >= 10 || newCol < 0 || newCol >= 10) {
            return false;
        }

        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.getRow() == newRow && obstacle.getCol() == newCol) {
                return false;
            }
        }

        if (isPlayerAtPosition(newRow, newCol)) {
            return false;
        }

        return true;
    }

    /**
     * Vérifie si une case est occupée par un joueur.
     */
    private boolean isPlayerAtPosition(int row, int col) {
        return (player1.getRow() == row && player1.getCol() == col) ||
                (player2.getRow() == row && player2.getCol() == col) ||
                (player3.getRow() == row && player3.getCol() == col);
    }

    /**
     * Déplace un joueur et vérifie les cases spéciales.
     */
    private void movePlayer(Player player, int deltaX, int deltaY) {
        int newRow = player.getRow() + deltaY;
        int newCol = player.getCol() + deltaX;

        player.setRow(newRow);
        player.setCol(newCol);

        updatePlayerPosition(player);

        if (isOnSpecialTile(player)) {
            player.addXP(20);
            System.out.println(player.getName() + " atteint une case spéciale et gagne 20 XP!");
        }
    }

    /**
     * Met à jour la position du joueur sur la grille.
     */
    private void updatePlayerPosition(Player player) {
        gameGrid.getGrid().getChildren().remove(player.getAvatar());
        StackPane playerContainer = new StackPane();
        playerContainer.getChildren().add(player.getAvatar());
        gameGrid.addToGrid(playerContainer, player.getRow(), player.getCol());
    }

    /**
     * Vérifie si un joueur est sur une case spéciale.
     */
    private boolean isOnSpecialTile(Player player) {
        for (SpecialTile tile : gameGrid.getSpecialTiles()) {
            if (player.getRow() == tile.getRow() && player.getCol() == tile.getCol()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne les obstacles présents dans la grille.
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
