package ru.doronin.fibonacci.springdriven.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.doronin.fibonacci.springdriven.services.FibonacciCalculationService;

import java.math.BigInteger;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FibonacciController {
    private final FibonacciCalculationService calculationService;

    /**
     * Расчёт элемента ряда Фибоначи
     *
     * @param element - номер элемента
     */
    @GetMapping("/fib/{element}")
    public Mono<BigInteger> calculateElement(@PathVariable("element") long element) {
        return calculationService.calculateElement(element);
    }
}
