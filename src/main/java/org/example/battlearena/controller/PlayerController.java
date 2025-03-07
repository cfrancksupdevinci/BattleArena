package org.example.battlearena.controller;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.battlearena.model.Player;
import org.example.battlearena.repository.PlayerRepository;

import java.util.List;

@Path("/player")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerController {
    private static final PlayerRepository repository = new PlayerRepository();

    @GET
    public List<Player> getAllPlayer(){
        return repository.getAllPlayer();
    }

    @GET
    @Path("/{id}")
    public Response getPlayerById(@PathParam("id") Long id){
        Player player = repository.getPlayerById(id);
        if (player == null){
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

        //TODO Victoria: modifier nom "GameLogic par le nom du fichier java utiliser pour le FX
        boolean canMove = true; //GameLogic.canMove(player, x, y);

        return Response.ok(canMove).build();
    }
}
