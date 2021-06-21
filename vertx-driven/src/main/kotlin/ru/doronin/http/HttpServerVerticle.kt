package ru.doronin.http

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import ru.doronin.calculation.FibCalculationVerticle


/* Created by doronantonin on 2021-06-21 */
/**
 * Компонент-сервер HTTP
 */
private val logger = KotlinLogging.logger {}

class HttpServerVerticle : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>) {
        vertx
            .createHttpServer()
            .requestHandler(configureRouter())
            .listen(8888)
            .onSuccess {
                logger.info { "Http Server configured" }
                startPromise.complete()
            }
            .onFailure {
                logger.error { "Failed to configure server: $it" }
                startPromise.fail(it)
            }
    }

    /**
     * Настройка роутинговой функции
     */
    private fun configureRouter(): Router {
        val router = Router.router(vertx)
        router.route(HttpMethod.GET, "/fib/:element").handler(this::handleFibRequest)
        return router
    }

    /**
     * Обработка запроса для расчёта чисел Фибоначи
     */
    private fun handleFibRequest(context: RoutingContext) {
        val elementNum = (context.pathParam("element")).toLong()
        vertx.eventBus()
            .request<String>(FibCalculationVerticle.CALCULATE_ELEMENT_BUS, elementNum) { calculationResult ->
                if (calculationResult.succeeded()) {
                    context.response().end(calculationResult.result().body())
                } else {
                    context.response().end("Failed to calculate")
                }
            }
    }
}
