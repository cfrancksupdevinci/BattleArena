package org.example.battlearena.repository;

import org.example.battlearena.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public class PlayerRepository implements CrudRepository<Player, Long> {

    public List<Player> getAllPlayer() {
        return null;
    }

    public Player getPlayerById(Long id) {
        return null;
    }
}
