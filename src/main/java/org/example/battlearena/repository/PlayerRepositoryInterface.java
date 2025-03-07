package org.example.battlearena.repository;

import org.example.battlearena.model.Objective;
import org.example.battlearena.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepositoryInterface extends CrudRepository<Player, Long> {

    public List<Player> getAllPlayer();

    public Player getPlayerById(Long id);

    Objective getObjectiveById(Long objectiveId);
}
