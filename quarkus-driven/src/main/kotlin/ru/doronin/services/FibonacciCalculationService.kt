package ru.doronin.services

import io.quarkus.cache.CacheKey
import io.quarkus.cache.CacheResult
import java.math.BigInteger
import javax.inject.Singleton

@Singleton
class FibonacciCalculationService {

    @CacheResult(cacheName = "fibonacci")
    fun calculateElement(@CacheKey elementNumber: Long = 1): BigInteger = calculate(elementNumber)

    private tailrec fun calculate(
        elementNumber: Long,
        prePreviousValue: BigInteger = BigInteger.ZERO,
        previousValue: BigInteger = BigInteger.ONE
    ): BigInteger =
        when (elementNumber) {
            0L -> prePreviousValue
            1L -> previousValue
            else -> calculate(elementNumber - 1, previousValue, prePreviousValue + previousValue)
        }
}