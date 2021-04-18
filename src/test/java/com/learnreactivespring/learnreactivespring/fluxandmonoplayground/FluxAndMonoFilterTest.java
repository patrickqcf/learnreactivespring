package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Patrick", "Amanda", "Douglas", "Marcella");

    @Test
    public void filterTest(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.startsWith("A"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Amanda")
                .verifyComplete()
        ;
    }

    @Test
    public void filterTestLength(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.length() > 6)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("Patrick", "Douglas", "Marcella")
                .verifyComplete()
                ;
    }
}
