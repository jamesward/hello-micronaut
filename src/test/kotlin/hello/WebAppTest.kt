package hello

import io.micronaut.test.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class WebAppTest {

    @Inject
    lateinit var webApp: WebApp

    @Test
    fun testIndex() {
        val name = runBlocking {
            webApp.index().body()?.get("name")
        }

        assertEquals("world", name)
    }

}
