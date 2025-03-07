package org.example.battlearena;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameDisplay {

  private Label turnLabel;
  private Label player1Info;
  private Label player2Info;
  private Label player3Info;

  public GameDisplay(Player player1, Player player2, Player player3) {
    // Création des labels d'information des joueurs
    turnLabel = new Label("Tour de " + player1.getName());
    player1Info = new Label("Joueur 1 - PV: " + player1.getHealth() + " | XP: " + player1.getXp());
    player2Info = new Label("Joueur 2 - PV: " + player2.getHealth() + " | XP: " + player2.getXp());
    player3Info = new Label("Joueur 3 - PV: " + player3.getHealth() + " | XP: " + player3.getXp());
  }

  public VBox createInfoBox() {
    VBox infoBox = new VBox(10, turnLabel, player1Info, player2Info, player3Info);
    infoBox.setLayoutX(500); // Positionner les informations à droite de la scène
    infoBox.setLayoutY(50);
    return infoBox;
  }

  public void updateTurnInfo(Player currentPlayer, Player player1, Player player2, Player player3) {
    // Mettre à jour l'indication du tour
    turnLabel.setText("Tour de " + currentPlayer.getName());

    // Mettre à jour les PV et XP des joueurs
    player1Info.setText("Joueur 1 - PV: " + player1.getHealth() + " | XP: " + player1.getXp());
    player2Info.setText("Joueur 2 - PV: " + player2.getHealth() + " | XP: " + player2.getXp());
    player3Info.setText("Joueur 3 - PV: " + player3.getHealth() + " | XP: " + player3.getXp());
  }
}
