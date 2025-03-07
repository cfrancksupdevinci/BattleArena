package org.example.battlearena.model;

import jakarta.persistence.Entity;

@Entity(name = "player")
public class Player {
    private Long id; // Change from int to Long to match the ID type you're using
    private String name;
    private int life;
    private Position pos;

    public Player(Long id, String name, int life, Position pos) {
        this.id = id;
        this.name = name;
        this.life = life;
        this.pos = pos;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public void setId(Long id) { // Change to Long and void
        this.id = id;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Position getPosition() {
        return pos;
    }

    public void setPositionX(int posX) {
        this.pos.setX(posX);
    }

    public void setPositionY(int posY) {
        this.pos.setY(posY);
    }
}
