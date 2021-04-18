package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After error"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                  (e) -> System.err.println("Captured exception: " + e),
                  () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElementsWithoutErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                    .expectNext("Spring")
                    .expectNext("Spring Boot")
                    .expectNext("Reactive Spring")
                    .verifyComplete()
        ;
    }

    @Test
    public void fluxTestElementsWithErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                    .expectNext("Spring")
                    .expectNext("Spring Boot")
                    .expectNext("Reactive Spring")
//                    .expectError(RuntimeException.class)
                    .expectErrorMessage("Exception Occurred")
                    .verify()
//                    .verifyComplete()
        ;
    }

    @Test
    public void fluxTestElementsWithErrorsAlt(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                    .expectNext("Spring", "Spring Boot", "Reactive Spring")
                    .expectErrorMessage("Exception Occurred")
                    .verify()
        ;
    }

    @Test
    public void fluxTestElementsCountWithErrors(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                    .expectNextCount(3)
                    .expectErrorMessage("Exception Occurred")
                    .verify()
        ;
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete()
                ;
    }

    @Test
    public void monoTestError(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                ;
    }
}
