package ru.doronin.fibonacci.springdriven.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.Positive;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.doronin.fibonacci.springdriven.configuration.CachingConfiguration;

import java.math.BigInteger;
import java.util.Objects;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class FibonacciCalculationService implements ApplicationListener<ApplicationStartedEvent> {
    private final CacheManager cacheManager;

    /**
     * Calculation of the next Fibonacci row element
     *
     * @param elementNumber - elementNumber
     */
    public Mono<BigInteger> calculateElement(@Positive long elementNumber) {
        return getFromCache(elementNumber).switchIfEmpty(elementCalculation(elementNumber));
    }

    private Mono<BigInteger> elementCalculation(long elementNumber) {
        Mono<BigInteger> element;
        if (elementNumber > 2) {
            element = calculateElement(elementNumber - 2)
                    .flatMap(value -> calculateElement(elementNumber - 1).map(newVal -> newVal.add(value)));
        } else {
            element = Mono.just(BigInteger.ONE);
        }
        return element.map(value -> {
            storeInCache(elementNumber, value);
            return value;
        });
    }

    private Mono<BigInteger> getFromCache(long elementNumber) {
        CaffeineCache rowCache =
                (CaffeineCache) Objects.requireNonNull(cacheManager.getCache(CachingConfiguration.FIBONACCI_CACHE), "Cache shouldn't be null");
        return Mono.justOrEmpty(rowCache.get(elementNumber, BigInteger.class));
    }

    private void storeInCache(long elementNumber, BigInteger elementValue) {
        CaffeineCache rowCache =
                (CaffeineCache) Objects.requireNonNull(cacheManager.getCache(CachingConfiguration.FIBONACCI_CACHE), "Cache shouldn't be null");
        rowCache.put(elementNumber, elementValue);
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        long start = System.currentTimeMillis();
        Flux.range(1, 1000)
                .map(Long::valueOf)
                .concatMap(this::calculateElement)
                .subscribe();
        log.info("Bootstrap taken: {} ms", System.currentTimeMillis() - start);
    }
}
