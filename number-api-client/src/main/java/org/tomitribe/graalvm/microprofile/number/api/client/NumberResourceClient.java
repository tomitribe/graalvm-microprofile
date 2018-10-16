package org.tomitribe.graalvm.microprofile.number.api.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Dependent
@RegisterRestClient
@Path("/number")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public interface NumberResourceClient {
    @GET
    @Path("/generate")
    String getNumber();
}
