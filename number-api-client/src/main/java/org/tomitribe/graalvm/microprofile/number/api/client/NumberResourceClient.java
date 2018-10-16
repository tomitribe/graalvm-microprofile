package org.tomitribe.graalvm.microprofile.number.api.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
@Path("/number")
@Produces(MediaType.TEXT_PLAIN)
public interface NumberResourceClient {
    @GET
    @Path("/generate")
    String getNumber();
}
