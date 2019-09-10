package org.superbiz.moviefun.rest;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.superbiz.moviefun.Comment;

import javax.decorator.Decorator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Decorator
@RegisterRestClient
@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CommentResourceClient {
    @GET
    @Path("{id}")
    @Fallback(CouldNotLoadCommentsFallback.class)
    List<Comment> getComments(@PathParam("id") final String id);

    @POST
    @Path("{id}")
    void createComment(@PathParam("id")final String id, final Comment comment);
}
