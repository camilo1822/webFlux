package org.example.exercise;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Examples {

    Function<String, String> myFunction = String::toUpperCase;

    /**
     * 1. Crear un Stream de datos de tipo String con 1 elemento, es necesario que este dato
     * se transforme a mayusculas.
     */
    public Mono<String> mapToUpperCase(String value) {
        return Mono.just(value).map(String::toUpperCase);
        //return Mono.just(value).map(myFunction);
        //return Mono.just(value).map(x -> x.toUpperCase());
    }

    /**
     * 2. Crear un Stream de datos de tipo String con 10 nombres y filtrar aquellos nombres que
     * tengan más o igual a 3 letras, después obtener la longitud del nombre y filtrar aquellos nombres
     * donde su longitud sea impar.
     */
    public Flux<String> filterOddNameLength(List<String> names) {

        return Flux.fromIterable(names).filter(name -> name.length() >= 3 && name.length() % 2 != 0);

    }

     /* public Flux<String> filterOddNameLength(List<String> names) {
        return Flux.fromIterable(names)
                .filter( name -> name.length() >= 3 )
                .filter( name -> name.length() % 2 != 0)
                ;
    }*/


    /**
     * 3. Crear un Stream de datos de tipo Double con 1 elemento. El metodo debe permitir realizar la division
     * entre dos numeros y en caso de error se debe retornar por defecto el valor cero.
     */
    public Mono<Double> divide(Double x, Double y) {
        Supplier<Double> myDivide = () -> x/y;

        return Mono.fromSupplier(myDivide)
                .filter(result -> !result.isInfinite())
                .onErrorReturn(0D)
                .switchIfEmpty(Mono.just(0D));

        //opcion1
        /*return Mono.defer(() -> this.opcionUnoDivision(x, y))
                .filter(result -> !result.isInfinite())
                .onErrorReturn(0D)
                .switchIfEmpty(Mono.just(0D));*/
    }

    public Mono<Double> opcionUnoDivision(Double x, Double y) {
        try {
            return Mono.just(x / y);
        } catch (Exception e) {
            return Mono.error(new RuntimeException(e));
        }
    }
}
