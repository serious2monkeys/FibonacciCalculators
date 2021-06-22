package doronin.ru.services

import java.math.BigInteger


/* Created by doronantonin on 2021-06-22 */
object CalculationService {
    fun calculateElement(elementNumber: Long = 1): BigInteger = calculate(elementNumber)

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
