package org.example.battlearena.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "player")
public class Player {
    private int id;
    private String name;
    private int life;
    private Position pos;

    public Player(int id, String name, int life, Position pos) {
        this.id = id;
        this.name = name;
        this.life = life;
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
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
