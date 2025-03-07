package org.example.battlearena.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/server")
public class ServerController {

  @GET
  @Path("/status")
  public Response getServerStatus() {
    return Response.ok("Le serveur est en ligne et fonctionne correctement").build();
  }
}
