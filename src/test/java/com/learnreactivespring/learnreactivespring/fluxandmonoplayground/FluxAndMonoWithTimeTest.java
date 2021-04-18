package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {
    @Test
    public void infiniteSequence() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200)) //[0..200]
                .log()
        ;

        infiniteFlux
                .subscribe(element -> System.out.println("Element is: " +element))
                ;

        Thread.sleep(3000);
    }

    @Test
    public void finiteSequenceTest() throws InterruptedException {
        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(200)) //[0..200]
                .take(3)
                .log()
                ;

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete()
                ;
    }

    @Test
    public void infiniteSequenceMap() throws InterruptedException {
        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(200)) //[0..200]
                .map(num -> (long) num.intValue())
                .take(3)
                .log()
                ;

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete()
                ;
    }

    @Test
    public void infiniteSequenceMapWithDelay() throws InterruptedException {
        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(200)) //[0..200]
                .delayElements(Duration.ofSeconds(1))
                .map(num -> new Long(num.intValue()))
                .take(3)
                .log()
                ;

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete()
                ;
    }
}
