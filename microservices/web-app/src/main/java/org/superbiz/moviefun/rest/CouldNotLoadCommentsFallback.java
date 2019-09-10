package org.superbiz.moviefun.rest;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.superbiz.moviefun.Comment;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class CouldNotLoadCommentsFallback implements FallbackHandler<List<Comment>> {
    @Override
    public List<Comment> handle(final ExecutionContext executionContext) {
        final Comment comment = new Comment();
        comment.setComment("Could not load comments!");
        return Collections.singletonList(comment);
    }
}
