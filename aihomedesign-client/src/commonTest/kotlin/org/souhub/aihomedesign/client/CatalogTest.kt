package org.souhub.aihomedesign.client

import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.souhub.aihomedesign.client.models.ServiceName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CatalogTest : AIHomeDesignTest() {

    @Test
    fun testGetSpaces() = test {
        val mockJson = """
            {
                "data": [
                    {
                        "id": "space-1",
                        "name": "living-room",
                        "title": "Living Room",
                        "description": "Modern living room design",
                        "prompt": "Design a modern living room",
                        "service_ids": ["service-item-removal", "service-virtual-staging"],
                        "icon_src": "https://example.com/icon.png",
                        "is_active": true,
                        "priority": 1,
                        "is_premium": false,
                        "is_multi_space": false
                    },
                    {
                        "id": "space-2",
                        "name": "bedroom",
                        "title": "Bedroom",
                        "description": "Cozy bedroom design",
                        "prompt": "Design a cozy bedroom",
                        "service_ids": ["service-item-removal"],
                        "icon_src": "https://example.com/bedroom-icon.png",
                        "is_active": true,
                        "priority": 2,
                        "is_premium": true,
                        "is_multi_space": false
                    }
                ]
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.catalog.getSpaces()

        assertEquals(2, result.data.size)

        // First space
        val space1 = result.data[0]
        assertEquals("space-1", space1.id)
        assertEquals("living-room", space1.name)
        assertEquals("Living Room", space1.title)
        assertEquals("Modern living room design", space1.description)
        assertEquals("Design a modern living room", space1.prompt)
        assertEquals(2, space1.serviceIds.size)
        assertEquals("service-item-removal", space1.serviceIds[0])
        assertEquals("service-virtual-staging", space1.serviceIds[1])
        assertEquals("https://example.com/icon.png", space1.iconSrc)
        assertTrue(space1.isActive)
        assertEquals(1, space1.priority)
        assertFalse(space1.isPremium)
        assertFalse(space1.isMultiSpace)

        // Second space
        val space2 = result.data[1]
        assertEquals("space-2", space2.id)
        assertEquals("bedroom", space2.name)
        assertTrue(space2.isPremium)
    }

    @Test
    fun testGetSpacesWithActiveFilter() = test {
        val mockJson = """
            {
                "data": [
                    {
                        "id": "space-1",
                        "name": "living-room",
                        "title": "Living Room",
                        "description": "Modern living room design",
                        "prompt": "Design a modern living room",
                        "service_ids": ["service-item-removal"],
                        "icon_src": "https://example.com/icon.png",
                        "is_active": true,
                        "priority": 1,
                        "is_premium": false,
                        "is_multi_space": false
                    }
                ]
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.catalog.getSpaces(active = true)

        assertEquals(1, result.data.size)
        assertTrue(result.data[0].isActive)
    }

    @Test
    fun testGetSpacesWithServiceNameFilter() = test {
        val mockJson = """
            {
                "data": [
                    {
                        "id": "space-1",
                        "name": "living-room",
                        "title": "Living Room",
                        "description": "Modern living room design",
                        "prompt": "Design a modern living room",
                        "service_ids": ["service-item-removal"],
                        "icon_src": "https://example.com/icon.png",
                        "is_active": true,
                        "priority": 1,
                        "is_premium": false,
                        "is_multi_space": false
                    }
                ]
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.catalog.getSpaces(serviceName = ServiceName.ItemRemoval)

        assertEquals(1, result.data.size)
        assertTrue(result.data[0].serviceIds.contains("service-item-removal"))
    }

    @Test
    fun testGetSpacesWithBothFilters() = test {
        val mockJson = """
            {
                "data": [
                    {
                        "id": "space-1",
                        "name": "kitchen",
                        "title": "Kitchen",
                        "description": "Modern kitchen design",
                        "prompt": "Design a modern kitchen",
                        "service_ids": ["service-item-removal"],
                        "icon_src": "https://example.com/kitchen-icon.png",
                        "is_active": true,
                        "priority": 3,
                        "is_premium": false,
                        "is_multi_space": true
                    }
                ]
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.catalog.getSpaces(active = true, serviceName = ServiceName.ItemRemoval)

        assertEquals(1, result.data.size)
        val space = result.data[0]
        assertTrue(space.isActive)
        assertTrue(space.serviceIds.contains("service-item-removal"))
        assertTrue(space.isMultiSpace)
    }

    @Test
    fun testGetSpacesEmpty() = test {
        val mockJson = """
            {
                "data": []
            }
        """.trimIndent()

        val result = aiHomeDesign { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"))
            )
        }.catalog.getSpaces()

        assertTrue(result.data.isEmpty())
    }
}