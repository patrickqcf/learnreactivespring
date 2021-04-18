package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {
    List<String> names = Arrays.asList("Patrick", "Amanda", "Douglas", "Marcella");
    @Test
    public void fluxUsingIterable(){
        Flux<String> namesFlux = Flux.fromIterable(names).log();

        StepVerifier.create(namesFlux)
                .expectNext("Patrick", "Amanda", "Douglas", "Marcella")
                .verifyComplete()
                ;
    }
    @Test
    public void fluxUsingArray(){
        String[] names = new String[]{"Patrick", "Amanda", "Douglas", "Marcella"};

        Flux<String> namesFlux = Flux.fromArray(names).log();

        StepVerifier.create(namesFlux)
                .expectNext("Patrick", "Amanda", "Douglas", "Marcella")
                .verifyComplete()
                ;
    }

    @Test
    public void fluxUsingStream(){
        Flux<String> namesFlux = Flux.fromStream(names.stream()).log();

        StepVerifier.create(namesFlux)
                .expectNext("Patrick", "Amanda", "Douglas", "Marcella")
                .verifyComplete()
                ;
    }

    @Test
    public void monoUsingJustOrEmpty(){
        Mono<String> mono = Mono.justOrEmpty(null);
        StepVerifier.create(mono.log())
                .verifyComplete()
        ;
    }

    @Test
    public void monoUsingSupplier(){
        Supplier<String> stringSupplier = () -> "Patrick";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier).log();

        StepVerifier.create(stringMono)
                .expectNext("Patrick")
                .verifyComplete()
                ;
    }

    @Test
    public void fluxUsingRange(){
        Flux<Integer> integerFlux = Flux.range(1, 5);

        StepVerifier.create(integerFlux.log())
                .expectNext(1,2,3,4,5)
                .verifyComplete()
                ;
    }
}
