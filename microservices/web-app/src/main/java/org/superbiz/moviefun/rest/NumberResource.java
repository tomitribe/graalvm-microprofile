package org.superbiz.moviefun.rest;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/number-api/number")
public class NumberResource {
    @RestClient
    @Inject
    private NumberResourceClient numberService;

    @GET
    @Path("/generate")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker
    @Fallback(NumberResourceClientFallbackHandler.class)
    public String getNumber() {
        return numberService.getNumber();
    }
}
