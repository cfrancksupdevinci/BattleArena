package org.example.battlearena.controller;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.example.battlearena.Game;
import org.example.battlearena.model.Objective;
import org.example.battlearena.model.Player;
import org.example.battlearena.repository.PlayerRepository;

import java.util.List;

@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerController {
    private static final PlayerRepository repository = new PlayerRepository();
    private static final Game game = new Game();

    @GET
    public List<Player> getAllPlayer() {
        return repository.getAllPlayer();
    }

    @GET
    @Path("/{id}")
    public Response getPlayerById(@PathParam("id") Long id) {
        Player player = repository.getPlayerById(id);
        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player not found").build();
        }
        return Response.ok(player).build();
    }

    @GET
    @Path("/can-move/{id}/{x}/{y}")
    public Response canMove(@PathParam("id") Long id,
            @PathParam("x") int x,
            @PathParam("y") int y) {
        Player player = repository.getPlayerById(id);

        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player not found").build();
        }

        game.movePlayer(x, y);

        return Response.ok("Player moved successfully").build();
    }

    @GET
    @Path("/{id}/attack/{targetId}")
    public Response attackPlayer(@PathParam("id") Long attackerId, @PathParam("targetId") Long targetId) {
        Player attacker = repository.getPlayerById(attackerId);
        Player target = repository.getPlayerById(targetId);

        if (attacker == null || target == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player not found").build();
        }

        // TODO Victoria, ajouter méthode attack dans Game
        game.attack(attacker, target);

        return Response.ok("Attack completed").build();
    }

    @GET
    @Path("/{id}/survive")
    public Response surviveTurn(@PathParam("id") Long playerId) {
        Player player = repository.getPlayerById(playerId);

        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player not found").build();
        }

        // TODO Victoria, ajouter méthode checkSurvival dans Game
        boolean survived = game.checkSurvival(player);

        return Response.ok(survived ? "Survived this turn" : "Did not survive this turn").build();
    }

    @GET
    @Path("/{id}/capture/{objectiveId}")
    public Response captureObjective(@PathParam("id") Long playerId, @PathParam("objectiveId") Long objectiveId) {
        Player player = repository.getPlayerById(playerId);
        Objective objective = repository.getObjectiveById(objectiveId);

        if (player == null || objective == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Player or Objective not found").build();
        }

        // TODO Victoria, ajouter méthode captureObjective dans Game
        game.captureObjective(player, objective);

        return Response.ok("Objective captured").build();
    }

}
