package com.thejohnsondev.data

import com.thejohnsondev.network.api.models.ArtworkData
import com.thejohnsondev.network.api.models.ArtworkSearchData
import com.thejohnsondev.network.api.models.Config
import com.thejohnsondev.network.api.models.Thumbnail
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MapperTest {

    private val testConfig = Config(
        iiifUrl = "https://example.com/iiif",
        websiteUrl = "https://example.com"
    )

    @Test
    fun artworkData_toDomainModel_maps_basic_fields_correctly() {
        val artworkData = ArtworkData(
            id = 123,
            title = "Test Title",
            artistDisplay = "Test Artist",
            dateDisplay = "2023",
            mediumDisplay = "Oil on canvas",
            description = "Long description",
            shortDescription = "Short description",
            departmentTitle = "Modern Art",
            isPublicDomain = true,
            creditLine = "Gift of someone",
            dimensions = "10x10",
            latitude = 10.0,
            longitude = 20.0,
            placeOfOrigin = "New York",
            galleryTitle = "Gallery 1",
            isOnView = true,
            styleTitle = "Abstract",
            classificationTitle = "Painting",
            exhibitionHistory = "Exhibited somewhere",
            publicationHistory = "Published somewhere"
        )

        val domainModel = artworkData.toDomainModel(testConfig)

        assertEquals(123, domainModel.id)
        assertEquals("Test Title", domainModel.title)
        assertEquals("Test Artist", domainModel.artist)
        assertEquals("2023", domainModel.date)
        assertEquals("Oil on canvas", domainModel.medium)
        assertEquals("Short description", domainModel.description) // Should prefer shortDescription
        assertEquals("Modern Art", domainModel.department)
        assertTrue(domainModel.isPublicDomain)
        assertEquals("Gift of someone", domainModel.creditLine)
        assertEquals("10x10", domainModel.dimensions)
        assertEquals(10.0, domainModel.latitude)
        assertEquals(20.0, domainModel.longitude)
        assertEquals("New York", domainModel.placeOfOrigin)
        assertEquals("Gallery 1", domainModel.galleryTitle)
        assertTrue(domainModel.isOnView)
        assertEquals("Abstract", domainModel.style)
        assertEquals("Painting", domainModel.classification)
        assertEquals("Exhibited somewhere", domainModel.exhibitionHistory)
        assertEquals("Published somewhere", domainModel.publicationHistory)
    }

    @Test
    fun artworkData_toDomainModel_handles_null_fields_with_defaults() {
        val artworkData = ArtworkData(
            id = null,
            title = null,
            artistDisplay = null,
            dateDisplay = null,
            mediumDisplay = null,
            description = null,
            shortDescription = null,
            departmentTitle = null,
            isPublicDomain = null,
            creditLine = null,
            dimensions = null,
            latitude = null,
            longitude = null,
            placeOfOrigin = null,
            galleryTitle = null,
            isOnView = null,
            styleTitle = null,
            classificationTitle = null,
            exhibitionHistory = null,
            publicationHistory = null
        )

        val domainModel = artworkData.toDomainModel(testConfig)

        assertEquals(artworkData.hashCode(), domainModel.id)
        assertEquals("", domainModel.title)
        assertEquals("Unknown Artist", domainModel.artist)
        assertEquals("", domainModel.date)
        assertEquals("", domainModel.medium)
        assertNull(domainModel.description)
        assertEquals("", domainModel.department)
        assertFalse(domainModel.isPublicDomain)
        assertEquals("", domainModel.creditLine)
        assertEquals("", domainModel.dimensions)
        assertNull(domainModel.latitude)
        assertNull(domainModel.longitude)
        assertEquals("", domainModel.placeOfOrigin)
        assertNull(domainModel.galleryTitle)
        assertFalse(domainModel.isOnView)
        assertNull(domainModel.style)
        assertNull(domainModel.classification)
        assertNull(domainModel.exhibitionHistory)
        assertNull(domainModel.publicationHistory)
    }

    @Test
    fun artworkData_toDomainModel_generates_image_URLs_correctly() {
        val artworkData = ArtworkData(
            imageId = "img1",
            altImageIds = listOf("img2", "img3")
        )

        val domainModel = artworkData.toDomainModel(testConfig)

        assertEquals(3, domainModel.imagesUrls?.size)
        assertEquals("https://example.com/iiif/img1/full/843,/0/default.jpg", domainModel.imagesUrls?.get(0))
        assertEquals("https://example.com/iiif/img2/full/843,/0/default.jpg", domainModel.imagesUrls?.get(1))
        assertEquals("https://example.com/iiif/img3/full/843,/0/default.jpg", domainModel.imagesUrls?.get(2))
    }

    @Test
    fun artworkData_toDomainModel_handles_missing_main_image_correctly() {
        val artworkData = ArtworkData(
            imageId = null,
            altImageIds = listOf("img2", "img3")
        )

        val domainModel = artworkData.toDomainModel(testConfig)

        assertEquals(2, domainModel.imagesUrls?.size)
        assertEquals("https://example.com/iiif/img2/full/843,/0/default.jpg", domainModel.imagesUrls?.get(0))
        assertEquals("https://example.com/iiif/img3/full/843,/0/default.jpg", domainModel.imagesUrls?.get(1))
    }

    @Test
    fun artworkData_toDomainModel_handles_null_config_correctly() {
        val artworkData = ArtworkData(
            imageId = "img1",
            altImageIds = listOf("img2")
        )

        val domainModel = artworkData.toDomainModel(null)

        assertEquals(2, domainModel.imagesUrls?.size)
        // Note: The current implementation produces "null/..." if config is null.
        // This test documents the current behavior.
        assertEquals("null/img1/full/843,/0/default.jpg", domainModel.imagesUrls?.get(0))
        assertEquals("null/img2/full/843,/0/default.jpg", domainModel.imagesUrls?.get(1))
    }

    @Test
    fun thumbnail_toDomainModel_maps_fields_correctly() {
        val thumbnail = Thumbnail(
            lqip = "lqip_string",
            width = 100,
            height = 200,
            altText = "Alt text"
        )

        val domainThumbnail = thumbnail.toDomainModel()

        assertEquals("lqip_string", domainThumbnail.lqip)
        assertEquals(100, domainThumbnail.width)
        assertEquals(200, domainThumbnail.height)
        assertEquals("Alt text", domainThumbnail.altText)
    }

    @Test
    fun artworkSearchData_toDomainModel_maps_fields_correctly() {
        val thumbnail = Thumbnail(
            lqip = "lqip_string",
            width = 100,
            height = 200,
            altText = "Alt text"
        )
        val searchData = ArtworkSearchData(
            id = 456,
            title = "Search Title",
            thumbnail = thumbnail
        )

        val domainSearchItem = searchData.toDomainModel()

        assertEquals(456, domainSearchItem.id)
        assertEquals("Search Title", domainSearchItem.title)
        assertEquals("lqip_string", domainSearchItem.thumbnail?.lqip)
    }

    @Test
    fun artworkSearchData_toDomainModel_handles_null_fields() {
        val searchData = ArtworkSearchData(
            id = null,
            title = null,
            thumbnail = null
        )

        val domainSearchItem = searchData.toDomainModel()

        assertEquals(searchData.hashCode(), domainSearchItem.id)
        assertEquals("", domainSearchItem.title)
        assertNull(domainSearchItem.thumbnail)
    }
}
