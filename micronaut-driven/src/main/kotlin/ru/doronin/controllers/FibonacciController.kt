package ru.doronin.controllers

import io.micronaut.http.annotation.Controller
import ru.doronin.services.CalculationService
import java.math.BigInteger
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam


/* Created by doronantonin on 2021-06-22 */
@Controller
@Path("/fib")
class FibonacciController(private val calculationService: CalculationService) {

    @GET
    @Path("{element}")
    fun calculateElement(@PathParam("element") elementNumber: Long = 1): BigInteger =
            calculationService.calculateElement(elementNumber)
}
