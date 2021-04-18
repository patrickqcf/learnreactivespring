package com.learnreactivespring.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("Patrick", "Amanda", "Douglas", "Marcella");

    @Test
    public void transformUsingMap(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(String::toUpperCase)
                .log()
                ;

        StepVerifier.create(namesFlux)
                .expectNext("PATRICK", "AMANDA", "DOUGLAS", "MARCELLA")
                .verifyComplete()
                ;
    }

    @Test
    public void transformUsingMapLength(){
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(name -> name.length())
                .log()
                ;

        StepVerifier.create(namesFlux)
                .expectNext(7, 6, 7, 8)
                .verifyComplete()
                ;
    }

    @Test
    public void transformUsingMapLengthRepeat(){
        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(name -> name.length())
                .repeat(1)
                .log()
                ;

        StepVerifier.create(namesFlux)
                .expectNext(7, 6, 7, 8, 7, 6, 7, 8)
                .verifyComplete()
                ;
    }

    @Test
    public void transformUsingMapFilter(){
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.length() > 6)
                .map(name -> name.toUpperCase())
                .log()
                ;

        StepVerifier.create(namesFlux)
                .expectNext("PATRICK", "DOUGLAS", "MARCELLA")
                .verifyComplete()
                ;
    }

    @Test
    public void transformUsingFlatMap(){
        Flux<String> nameFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s)); // vai retornar A -> List[A, newValue], B -> List[B, newValue], ...
                }) //usar quando o DB ou um serviço retornar um fluxo de dados -> s = Flux<String>
                .log()
                ;
        StepVerifier.create(nameFlux)
                .expectNextCount(12)
                .verifyComplete()
                ;
    }

    @Test
    public void transformUsingFlatMapUsingParallel(){
        Flux<String> nameFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2) //Flux<Flux<String>> -> (A,B) , (C,D) , (E,F) || window diz que vai pegar de 2 em 2 elementos na lista
                .flatMap(s -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))  // Flux<List<String>>
                .flatMap(s -> Flux.fromIterable(s))
                .log()
                ;
        StepVerifier.create(nameFlux)
                .expectNextCount(12)
                .verifyComplete()
                ;
    }
    @Test
    public void transformUsingFlatMapUsingParallelMaintainingOrderOfElements(){
        Flux<String> nameFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMapSequential(s -> s.map(this::convertToList).subscribeOn(Schedulers.parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log()
                ;
        StepVerifier.create(nameFlux)
                .expectNextCount(12)
                .verifyComplete()
                ;
    }

    private List<String> convertToList(String s){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }
}
