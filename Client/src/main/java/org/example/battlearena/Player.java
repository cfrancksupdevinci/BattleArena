package org.example.battlearena;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player {
    private String name;
    private int health;
    private int xp;
    private int row;
    private int col;
    private Circle avatar; // Représentation graphique du joueur
    private boolean hasAttacked = false;
    private boolean alive = true;

    public Player(String name, int health, int xp, int row, int col) {
        this.name = name;
        this.health = health;
        this.xp = xp;
        this.row = row;
        this.col = col;

        // Création du cercle représentant le joueur
        this.avatar = new Circle(20); // Rayon de 20 pixels
        this.avatar.setFill(Color.HOTPINK); // Couleur du joueur
        this.avatar.setStroke(Color.BLACK); // Bordure noire
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Circle getAvatar() {
        return avatar;
    }

    // Mise à jour de la position
    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

}
