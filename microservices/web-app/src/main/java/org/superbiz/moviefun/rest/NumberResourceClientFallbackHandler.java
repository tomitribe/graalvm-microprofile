package org.superbiz.moviefun.rest;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NumberResourceClientFallbackHandler implements FallbackHandler<String> {
    @Override
    public String handle(final ExecutionContext executionContext) {
        return "MV-FALLBACK-" + (int) Math.floor((Math.random() * 9999999)) + 1;
    }
}
