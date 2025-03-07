package org.example.battlearena.controller;


import org.example.battlearena.model.Player;
import org.example.battlearena.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/player")
@RestController
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    PlayerRepository repository;

    @GetMapping
    public List<Player> getAllPlayer(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable("id") Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    // @GET
    // @Path("/can-move/{id}/{x}/{y}")
    // public Response canMove(@PathParam("id") Long id,
    //                         @PathParam("x") int x,
    //                         @PathParam("y") int y) {
    //     Player player = repository.getPlayerById(id);

    //     if (player == null) {
    //         return Response.status(Response.Status.NOT_FOUND).entity("Player not found").build();
    //     }

    //     //TODO Victoria: modifier nom "GameLogic par le nom du fichier java utiliser pour le FX
    //     boolean canMove = true; //GameLogic.canMove(player, x, y);

    //     return Response.ok(canMove).build();
    // }
}
