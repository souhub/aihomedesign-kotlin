package org.souhub.aihomedesign.client

import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.test.Test
import kotlin.test.assertEquals

class SpacesTest : AIHomeDesignTest() {

    @Test
    fun testGetSpaces() = test {
        val mockJson = """
            {
                "data": [
                    {
                        "id": "xxx",
                        "name": "space-bedroom",
                        "title": "Bedroom",
                        "description": "",
                        "prompt": "Bedroom",
                        "service_ids": [
                            "yyy"
                        ],
                        "icon_src": "https://example.com/img.svg",
                        "is_active": true,
                        "priority": 0,
                        "is_premium": false,
                        "is_multi_space": false
                    }
                ]
            }
        """.trimIndent()

        val getSpaces = aiHomeDesign { request ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.spaces.getSpaces()

        assertEquals(getSpaces[0].id, "xxx")
    }
}