package org.example.battlearena.controller;

import org.example.battlearena.Game;
import org.example.battlearena.model.Objective;
import org.example.battlearena.model.Player;
import org.example.battlearena.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    PlayerRepository repository;

    private static final Game game = new Game();

    @GetMapping
    public List<Player> getAllPlayer() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable(value = "id") Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Player not found for id: " + id));
    }

    @GetMapping("/can-move/{id}/{x}/{y}")
    public boolean canMove(@PathVariable(value = "id") Long id,
            @PathVariable(value = "x") int x,
            @PathVariable(value = "y") int y) {
        Player player = repository.findById(id).orElseThrow(() -> new RuntimeException("Player not found for id: " + id));

        if (player == null) {
            return false;
        }

        // game.movePlayer(x, y);

        return true;
    }

    @GetMapping("/{id}/attack/{targetId}")
    public boolean attackPlayer(@PathVariable(value = "id") Long attackerId, @PathVariable(value = "targetId") Long targetId) {
        Player attacker = repository.findById(attackerId).orElseThrow(() -> new RuntimeException("Player not found for id: " + attackerId));
        Player target = repository.findById(targetId).orElseThrow(() -> new RuntimeException("Player not found for id: " + targetId));

        if (attacker == null || target == null) {
            return false;
        }

        // TODO Victoria, ajouter méthode attack dans Game
        // game.attack(attacker, target);

        return true;
    }

    @GetMapping("/{id}/survive")
    public boolean surviveTurn(@PathVariable(value = "id") Long playerId) {
        Player player = repository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found for id: " + playerId));

        if (player == null) {
            return false;
        }

        // TODO Victoria, ajouter méthode checkSurvival dans Game
        // boolean survived = game.checkSurvival(player);

        return true;
    }

    @GetMapping("/{id}/capture/{objectiveId}")
    public boolean captureObjective(@PathVariable(value = "id") Long playerId, @PathVariable(value = "objectiveId") Long objectiveId) {
        Player player = repository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found for id: " + playerId));
        //Objective objective = repository.getObjectiveById(objectiveId);

        // if (player == null || objective == null) {
        //     return Response.status(Response.Status.NOT_FOUND).entity("Player or Objective not found").build();
        // }

        // TODO Victoria, ajouter méthode captureObjective dans Game
        // game.captureObjective(player, objective);

        return true;
    }

}
