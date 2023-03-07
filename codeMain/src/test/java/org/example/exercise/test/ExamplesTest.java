package org.example.exercise.test;

import org.example.exercise.Examples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExamplesTest {
    Examples exercise;

    @BeforeEach
    void setUp() {
        exercise = new Examples();
    }

    /**
     * 1. Crear un Stream de datos de tipo String con 1 elemento, es necesario que este dato     * se transforme a mayusculas.
     */
    @ParameterizedTest
    @ValueSource(strings = {"john", "marie", "Daniel", "ElEnA", "39202"})
    void shouldReturnNameInUpperCase(String name) {
        Mono<String> nameUpperCase = exercise.mapToUpperCase(name);
        StepVerifier.create(nameUpperCase)
                .expectNextMatches(value -> value.equals(name.toUpperCase()))
                .verifyComplete();
    }

    /**
     * 2. Crear un Stream de datos de tipo String con 10 nombres y filtrar aquellos nombres que     * tengan más de 3 letras, después obtener la longitud del nombre y filtrar aquellos nombres     * donde su longitud sea impar.
     */
    @Test
    void shouldReturnListOfNamesWithOddLength() {
        List<String> namesToFilter = List.of("Nana", "Marie", "John", "William", "Jimmy", "Tah");
        Flux<String> names = exercise.filterOddNameLength(namesToFilter);
        StepVerifier.create(names)
                .expectNext("Marie", "William", "Jimmy", "Tah")
                .verifyComplete();
    }

    /**
     * 3. Crear un Stream de datos de tipo Double con 1 elemento. El metodo debe permitir realizar la division     * entre dos numeros y en caso de error se debe retornar por defecto el valor cero.
     */
    @ParameterizedTest
    @MethodSource("divideNumberData")
    void shouldDivideTwoNumbers(Double x, Double y, Double result) {
        Mono<Double> divideNumbers = exercise.divide(x, y);
        StepVerifier.create(divideNumbers)
                .expectNextMatches(value -> value.equals(result))
                .verifyComplete();
    }

    private static Stream<Arguments> divideNumberData() {
        return Stream.of(
                arguments(2D, 4D, 0.5),
                arguments(10D, 5D, 2D),
                arguments(1D, 3D, 0.3333333333333333),
                arguments(3D, 0D, 0D),
                arguments(0D, 7D, 0D),
                arguments(0D, null, 0D),
                arguments(null, 0D, 0D),
                arguments(null, null, 0D)
        );
    }
}
