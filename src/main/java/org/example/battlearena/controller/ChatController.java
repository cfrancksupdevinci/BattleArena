package org.example.battlearena.controller;

import org.example.battlearena.model.ChatMessage;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.FormParam;

@Path("/chat") // Le chemin de base pour le chat
public class ChatController {

  // Endpoint POST pour envoyer un message de chat
  @POST
  @Consumes("application/json") // Nous consommons des données JSON dans la requête
  public Response sendMessage(ChatMessage message) {
    // Logique pour gérer l'envoi du message (par exemple, stocker dans une base de
    // données ou le transmettre à d'autres joueurs)

    boolean success = saveChatMessage(message); // Cette méthode sera responsable de l'enregistrement ou du traitement
                                                // du message

    if (success) {
      return Response.status(Response.Status.OK)
          .entity("Message envoyé avec succès.") // Message de succès
          .build();
    } else {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Erreur lors de l'envoi du message.") // Message d'erreur
          .build();
    }
  }

  // Logique pour sauver ou traiter le message (par exemple, dans une base de
  // données)
  private boolean saveChatMessage(ChatMessage message) {
    // Cette méthode doit implémenter la logique spécifique pour enregistrer le
    // message.
    // Vous pouvez par exemple l'ajouter dans une liste, une base de données, ou le
    // diffuser aux autres joueurs.
    System.out.println("Message reçu: " + message.getPlayerName() + ": " + message.getMessage());

    // Par exemple, toujours renvoyer true pour indiquer que le message a bien été
    // "traité"
    return true;
  }
}
