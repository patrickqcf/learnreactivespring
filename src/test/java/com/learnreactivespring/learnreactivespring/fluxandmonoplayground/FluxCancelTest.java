package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxCancelTest {

    @Test
    public void returnFluxWithXRequest(){
        Flux<Integer> fluxo = Flux.range(1,100).log();

        fluxo.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(30);
                System.out.println("Value : " + value);
                fluxo.doOnComplete(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("No more values");
                        cancel();
                    }
                });
            }
        });
    }
}
