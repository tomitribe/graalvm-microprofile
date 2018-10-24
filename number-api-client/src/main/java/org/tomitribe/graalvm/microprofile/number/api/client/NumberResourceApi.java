package org.tomitribe.graalvm.microprofile.number.api.client;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NumberResourceApi {
    @RestClient
    @Inject
    private NumberResourceClient numberService;

    @CircuitBreaker
    @Fallback(NumberResourceClientFallbackHandler.class)
    public String getNumber() {
        return numberService.getNumber();
    }
}
