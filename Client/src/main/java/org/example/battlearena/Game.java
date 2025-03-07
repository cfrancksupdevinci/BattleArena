package org.example.battlearena;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

    private GameGrid gameGrid;
    private Player player1;
    private Player player2;
    private Player player3;

    private boolean hasAttacked = false;

    private int currentPlayerIndex = 0; // Indice du joueur actuel (tour par tour)
    private GameDisplay gameDisplay;
    private Text eventMessage = new Text();

    private List<Chest> chests = new ArrayList<>(); // Liste pour stocker les coffres
    private int chestEventCounter = 0;

    @Override
    public void start(Stage primaryStage) {
        // Création de la grille de jeu
        gameGrid = new GameGrid();

        // Création et placement des joueurs
        player1 = new Player("Joueur 1", 10, 0, 9, 9, 1);
        StackPane player1Container = new StackPane();
        player1Container.getChildren().add(player1.getAvatar());
        gameGrid.addToGrid(player1Container, player1.getRow(), player1.getCol());

        player2 = new Player("Joueur 2", 10, 0, 0, 0, 2); // Position initiale du joueur 2
        StackPane player2Container = new StackPane();
        player2Container.getChildren().add(player2.getAvatar());
        gameGrid.addToGrid(player2Container, player2.getRow(), player2.getCol());

        player3 = new Player("Joueur 3", 10, 0, 5, 5, 3); // Position initiale du joueur 3
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

        eventMessage.setStyle("-fx-font-size: 20px; -fx-fill: red;");
        eventMessage.setOpacity(0); // Au départ, le texte est invisible
        hbox.getChildren().add(eventMessage);

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
                // Passer au joueur suivant tout en ignorant les joueurs morts
                do {
                    currentPlayerIndex = (currentPlayerIndex + 1) % 3;
                } while (!getCurrentPlayer().isAlive());

                hasAttacked = false;

                // Générer un événement aléatoire de temps en temps
                generateRandomEvent(getCurrentPlayer());

                checkForChest(getCurrentPlayer());

                gameDisplay.updateTurnInfo(getCurrentPlayer(), player1, player2, player3);
            }
        });

        primaryStage.setTitle("Battle Arena");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Génère un événement aléatoire (ex : tempête).
     */
    private void generateRandomEvent(Player player) {
        // 50% de chance de déclencher un événement
        if (Math.random() < 0.01) {
            triggerStormEvent(player);
        }

        // Apparition d'un coffre tous les 5 tours
        chestEventCounter++;
        if (chestEventCounter >= 8) {
            spawnChest(); // Générer un coffre
            chestEventCounter = 0; // Réinitialiser le compteur
        }
    }

    /**
     * Gère un événement de tempête.
     */
    private void triggerStormEvent(Player player) {
        // Appliquer des dégâts au joueur
        player.setHealth(player.getHealth() - 10); // La tempête prend 10 points de vie
        if (player.getHealth() <= 0) {
            handlePlayerDeath(player, getCurrentPlayer());
        }

        // Afficher un message sur l'écran
        eventMessage.setText("Une tempête frappe " + player.getName() + " et lui prend 10 PV!");
        eventMessage.setOpacity(1); // Rendre le texte visible

        // Faire disparaître le message après 2 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> eventMessage.setOpacity(0)); // Rendre le texte invisible
        pause.play();
    }

    private void spawnChest() {
        // Générer un coffre sur une position aléatoire
        int row = (int) (Math.random() * 10);
        int col = (int) (Math.random() * 10);

        // Vérifier que le coffre ne se trouve pas déjà sur une case occupée
        if (!isOccupied(row, col)) {
            Chest chest = new Chest(row, col);
            chests.add(chest);

            // Ajouter le coffre visuel sur la grille
            StackPane chestContainer = new StackPane();
            chestContainer.getChildren().add(chest.getChestRectangle());
            gameGrid.addToGrid(chestContainer, row, col);

            // Afficher le coffre
            System.out.println("Un coffre apparaît en (" + row + ", " + col + ")!");
        }
    }

    private boolean isOccupied(int row, int col) {
        // Vérifier si un joueur ou un obstacle occupe la case
        if (isPlayerAtPosition(row, col)) {
            return true;
        }

        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.getRow() == row && obstacle.getCol() == col) {
                return true;
            }
        }

        return false;
    }

    private void checkForChest(Player player) {
        for (Chest chest : chests) {
            if (player.getRow() == chest.getRow() && player.getCol() == chest.getCol()) {
                player.addXP(20); // Le joueur gagne 20 XP
                chests.remove(chest); // Retirer le coffre de la liste

                // Retirer le coffre visuel de la grille
                gameGrid.getGrid().getChildren().remove(chest.getChestRectangle());

                System.out.println(player.getName() + " a trouvé un coffre et gagne 20 XP!");

                // Une fois que le coffre est pris, on peut sortir de la boucle
                break;
            }
        }
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

    private void attack(Player attacker) {
        for (Player target : new Player[] { player1, player2, player3 }) {
            if (target != attacker && target.isAlive()) { // Vérifie si le joueur est encore en vie
                if (attacker.getRow() == target.getRow() && Math.abs(attacker.getCol() - target.getCol()) == 1) {
                    target.setHealth(target.getHealth() - 10); // Attaque sur le joueur à gauche ou à droite
                    if (target.getHealth() <= 0) {
                        handlePlayerDeath(target, attacker);
                    } else {
                        attacker.addXP(10); // Gagne 10 XP après une attaque réussie
                    }
                    return;
                } else if (attacker.getCol() == target.getCol() && Math.abs(attacker.getRow() - target.getRow()) == 1) {
                    target.setHealth(target.getHealth() - 10); // Attaque sur le joueur au-dessus ou en dessous
                    if (target.getHealth() <= 0) {
                        handlePlayerDeath(target, attacker);
                    } else {
                        attacker.addXP(10); // Gagne 10 XP après une attaque réussie
                    }
                    return;
                }
            }
        }
    }

    /**
     * Gère la mort d'un joueur, donne 30 XP à l'attaquant et enlève le joueur de la
     * grille.
     */
    private void handlePlayerDeath(Player target, Player attacker) {
        System.out.println(target.getName() + " a été éliminé par " + attacker.getName() + "!");
        attacker.addXP(30); // Gain de 30 XP pour l'attaque décisive

        // Retirer l'avatar du joueur de la grille
        gameGrid.getGrid().getChildren().remove(target.getAvatar());

        // Si le joueur est contenu dans un StackPane, on le retire aussi
        StackPane playerContainer = getPlayerContainer(target);
        if (playerContainer != null) {
            gameGrid.getGrid().getChildren().remove(playerContainer);
        }

        // Marquer le joueur comme inactif pour éviter d'autres actions
        target.setAlive(false); // Le joueur est maintenant "mort" et inactif
    }

    private StackPane getPlayerContainer(Player player) {
        for (javafx.scene.Node node : gameGrid.getGrid().getChildren()) {
            if (node instanceof StackPane) {
                StackPane stackPane = (StackPane) node;
                if (stackPane.getChildren().contains(player.getAvatar())) {
                    return stackPane; // Retourne le StackPane contenant l'avatar du joueur
                }
            }
        }
        return null; // Si aucun StackPane trouvé
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
