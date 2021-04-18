package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {
    @Test
    public void combineUsingMerge(){
        Flux<String> fluxA = Flux.just("A", "B", "C");
        Flux<String> fluxB = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.merge(fluxA, fluxB);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
                ;
    }
    @Test
    public void combineUsingMergeWithDelay(){
        Flux<String> fluxA = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> fluxB = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(fluxA, fluxB);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNextCount(6)
//                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
                ;
    }

    @Test
    public void combineUsingConcat(){
        Flux<String> fluxA = Flux.just("A", "B", "C");
        Flux<String> fluxB = Flux.just("D", "E", "F");

        Flux<String> concatenetedFlux = Flux.concat(fluxA, fluxB);

        StepVerifier.create(concatenetedFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
                ;
    }

    @Test
    public void combineUsingConcatWithDelay(){
        Flux<String> fluxA = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> fluxB = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));

        Flux<String> concatenetedFlux = Flux.concat(fluxA, fluxB);

        StepVerifier.create(concatenetedFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
                ;
    }

    @Test
    public void combineUsingZip(){
        Flux<String> fluxA = Flux.just("A", "B", "C");
        Flux<String> fluxB = Flux.just("D", "E", "F");

        Flux<String> concatenetedFlux = Flux.zip(fluxA, fluxB, (t1,t2) -> {
            return t1.concat(t2); //A,D / B,E / C,F
        });

        StepVerifier.create(concatenetedFlux.log())
                .expectSubscription()
                .expectNext("AD", "BE", "CF")
                .verifyComplete()
        ;
    }
}
