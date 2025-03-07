package org.example.battlearena.model;

import jakarta.persistence.Entity;

@Entity(name = "player")
public class Player {
    private String name;
    private int life;

    

    public Player(String name, int life) {
        this.name = name;
        this.life = life;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }
}
