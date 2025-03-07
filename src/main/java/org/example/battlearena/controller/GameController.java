package org.example.battlearena.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/game") // Spécifie le chemin de base pour les actions liées au jeu
public class GameController {

  // Endpoint POST pour mettre fin au tour du joueur
  @POST
  @Path("/end-turn") // L'URL pour accéder à ce service sera http://localhost:8080/game/end-turn
  @Consumes("application/json") // Cette annotation indique que l'API accepte des requêtes JSON (facultatif si
                                // aucune donnée n'est envoyée)
  public Response endTurn() {
    // Logique de fin de tour ici
    // Vous devez gérer la logique qui marque la fin du tour du joueur (changer
    // l'état, passer au joueur suivant, etc.)

    // Par exemple, vous pourriez avoir une méthode dans votre jeu qui gère la fin
    // du tour
    boolean success = endPlayerTurnLogic();

    if (success) {
      return Response.status(Response.Status.OK)
          .entity("Tour terminé avec succès.") // Message de succès
          .build();
    } else {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Erreur lors de la fin du tour.") // Message d'erreur
          .build();
    }
  }

  // Logique pour mettre fin au tour d'un joueur (implémentation de l'action
  // réelle)
  private boolean endPlayerTurnLogic() {
    // Implémentez la logique spécifique pour terminer le tour du joueur, comme :
    // - Passer au joueur suivant
    // - Réinitialiser les actions du joueur
    // - Mettre à jour l'état du jeu
    // Par exemple, ici je suppose que la méthode renvoie toujours true pour
    // indiquer que tout s'est bien passé.

    // Vous devez remplacer ce code par votre logique spécifique.
    return true;
  }
}
