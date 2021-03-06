package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {
    @Test
    public void backPressureTest(){
        Flux<Integer> finiteFlux = Flux.range(1,10)
                .log();
        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(3)
                .expectNext(1,2,3)
                .thenRequest(1)
                .expectNext(4)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressure(){
        Flux<Integer> finiteFlux = Flux.range(1,10)
                .log();
        finiteFlux.subscribe((element) -> System.out.println("Element is: " +element),
                e -> System.out.println("Exception is: " +e),
                () -> System.out.println("Done"),
                subscription -> subscription.request(2)
        );
    }

    @Test
    public void backPressureCancel(){
        Flux<Integer> finiteFlux = Flux.range(1,10)
                .log();
        finiteFlux.subscribe((element) -> System.out.println("Element is: " +element),
                e -> System.out.println("Exception is: " +e),
                () -> System.out.println("Done"),
                subscription -> subscription.cancel()
        );
    }

    @Test
    public void CustomBackPressure(){
        Flux<Integer> finiteFlux = Flux.range(1,10)
                .log();

        finiteFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value received is: " +value);
                if (value == 4){
                    cancel();
                }
            }
        });

    }
}
