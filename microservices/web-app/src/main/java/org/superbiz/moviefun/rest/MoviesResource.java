/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.superbiz.moviefun.rest;

import org.apache.commons.lang.StringUtils;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.superbiz.moviefun.Comment;
import org.superbiz.moviefun.Movie;
import org.superbiz.moviefun.MoviesBean;
import org.superbiz.moviefun.utils.DecryptedValue;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("movies")
@Produces({"application/json"})
@ApplicationScoped
public class MoviesResource {
    private static final Logger LOGGER = Logger.getLogger(MoviesResource.class.getName());

    @Inject
    private NumberResource numberResource;
    @Inject
    @RestClient
    private CommentResourceClient commentResourceClient;

    @EJB
    private MoviesBean moviesBean;

    @Inject
    @Claim("username")
    private ClaimValue<String> username;

    @Inject
    @Claim("email")
    private ClaimValue<String> email;

    @Inject
    @Claim("jti")
    private ClaimValue<String> jti;

    @Inject
    @DecryptedValue("creditCard")
    private String creditCard;

    @Inject
    private Person person;

    @Inject
    private JsonWebToken jwtPrincipal;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("{id}")
    public Movie find(@PathParam("id") String id) {
        LOGGER.info("find: " + toIdentityString());
        final Movie movie = moviesBean.find(id);
        movie.setComments(commentResourceClient.getComments(id));
        return movie;
    }

    private String toIdentityString() {
        if (jwtPrincipal == null) {
            return "no authenticated user.";
        }

        final StringBuilder builder = new StringBuilder();

        builder.append(username);
        builder.append(String.format(" (jti=%s)", jti));
        builder.append(String.format(" (email=%s)", email));
        builder.append(String.format(" (person creditCard=%s)", person.getCreditCard()));
        builder.append(String.format(" (language=%s)", person.getLanguage()));
        builder.append(String.format(" (groups=%s)", StringUtils.join(jwtPrincipal.getGroups(), ", ")));
        return builder.toString();
    }

    @GET
    public List<Movie> getMovies(@QueryParam("first") Integer first, @QueryParam("max") Integer max,
                                 @QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm) {
        LOGGER.info("list: " + toIdentityString());
        final List<Movie> movies = moviesBean.getMovies(first, max, field, searchTerm);
        movies.forEach(movie -> movie.setComments(commentResourceClient.getComments(movie.getId())));
        return movies;
    }

    @POST
    @Consumes("application/json")
    public Movie addMovie(Movie movie) {
        movie.setId(numberResource.getNumber());

        LOGGER.info("add: " + toIdentityString());
        if (!securityContext.isUserInRole("create")) {
            throw new WebApplicationException("Bad permission.", Response.Status.FORBIDDEN);
        }
        moviesBean.addMovie(movie);
        return movie;
    }

    @POST
    @Path("{id}/comment")
    @Consumes("text/plain")
    public Movie addCommentToMovie(
            @PathParam("id") final String id,
            final String comment) {

        if (jwtPrincipal == null) {
            throw new WebApplicationException("Authentication required.", Response.Status.UNAUTHORIZED);
        }
        LOGGER.info("add comment to movie: " + toIdentityString());

        final Comment c = new Comment();
        c.setAuthor(username.getValue());
        c.setComment(comment);
        c.setEmail(email.getValue());
        c.setTimestamp(new Date());

        commentResourceClient.createComment(id, c);

        final Movie movie = moviesBean.find(id);
        movie.setComments(commentResourceClient.getComments(id));
        return movie;
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @RolesAllowed("update")
    public Movie editMovie(@PathParam("id") final String id, final Movie movie) {
        LOGGER.info("edit: " + toIdentityString());
        final Movie original = moviesBean.find(id);
        original.setTitle(movie.getTitle());
        original.setDirector(movie.getDirector());
        original.setGenre(movie.getGenre());
        original.setYear(movie.getYear());
        original.setRating(movie.getRating());
        moviesBean.editMovie(original);
        return movie;
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("delete")
    public void deleteMovie(@PathParam("id") final String id) {
        LOGGER.info("delete: " + toIdentityString());
        moviesBean.deleteMovie(id);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public int count(@QueryParam("field") String field, @QueryParam("searchTerm") String searchTerm) {
        LOGGER.info("count: " + toIdentityString());
        return moviesBean.count(field, searchTerm);
    }
}
