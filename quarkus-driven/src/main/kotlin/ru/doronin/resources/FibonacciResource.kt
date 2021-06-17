package ru.doronin.resources

import ru.doronin.services.FibonacciCalculationService
import java.math.BigInteger
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("/fib")
class FibonacciResource(@Inject private val fibonacciCalculationService: FibonacciCalculationService) {

    @GET
    @Path("{element}")
    fun calculateElement(@PathParam("element") elementNumber: Long = 1): BigInteger =
        fibonacciCalculationService.calculateElement(elementNumber)
}