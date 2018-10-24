package org.superbiz.moviefun.rest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.decorator.Decorator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Decorator
@RegisterRestClient
@Path("/number")
@Produces(MediaType.TEXT_PLAIN)
public interface NumberResourceClient {
    @GET
    @Path("/generate")
    String getNumber();
}
