package org.example.battlearena.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServerExceptionHandler implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
    // Gérer les exceptions et retourner une réponse d'erreur
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("Erreur du serveur : " + exception.getMessage())
        .build();
  }
}
