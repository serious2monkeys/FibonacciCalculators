package ru.doronin.services

import io.micronaut.cache.annotation.Cacheable
import java.math.BigInteger
import javax.inject.Singleton


/* Created by doronantonin on 2021-06-22 */
@Singleton
open class CalculationService {
    @Cacheable(cacheNames = ["fibonacci"], parameters = ["elementNumber"], atomic = true)
    open fun calculateElement(elementNumber: Long = 1): BigInteger = calculate(elementNumber)

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
