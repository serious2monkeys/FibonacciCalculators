package doronin.ru

import doronin.ru.plugins.configureRouting
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/fib/4").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("3", response.content)
            }
        }
    }
}
