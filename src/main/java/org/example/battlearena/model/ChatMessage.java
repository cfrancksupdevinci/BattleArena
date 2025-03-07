package org.example.battlearena.model;

public class ChatMessage {
  private String playerName;
  private String message;

  // Constructeur
  public ChatMessage(String playerName, String message) {
    this.playerName = playerName;
    this.message = message;
  }

  // Getters et Setters
  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
