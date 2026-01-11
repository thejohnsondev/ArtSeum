package com.thejohnsondev.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtResponseDto(
    @SerialName("data")
    val data: ArtworkData,
    @SerialName("info")
    val info: Info,
    @SerialName("config")
    val config: Config
)

@Serializable
data class ArtworkData(
    @SerialName("id")
    val id: Int,
    @SerialName("api_model")
    val apiModel: String,
    @SerialName("api_link")
    val apiLink: String,
    @SerialName("is_boosted")
    val isBoosted: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("alt_titles")
    val altTitles: List<String>? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerialName("main_reference_number")
    val mainReferenceNumber: String,
    @SerialName("has_not_been_viewed_much")
    val hasNotBeenViewedMuch: Boolean,
    @SerialName("boost_rank")
    val boostRank: Int? = null,
    @SerialName("date_start")
    val dateStart: Int,
    @SerialName("date_end")
    val dateEnd: Int,
    @SerialName("date_display")
    val dateDisplay: String,
    @SerialName("date_qualifier_title")
    val dateQualifierTitle: String,
    @SerialName("date_qualifier_id")
    val dateQualifierId: Int? = null,
    @SerialName("artist_display")
    val artistDisplay: String,
    @SerialName("place_of_origin")
    val placeOfOrigin: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("short_description")
    val shortDescription: String? = null,
    @SerialName("dimensions")
    val dimensions: String,
    @SerialName("dimensions_detail")
    val dimensionsDetail: List<DimensionsDetail>,
    @SerialName("medium_display")
    val mediumDisplay: String,
    @SerialName("inscriptions")
    val inscriptions: String? = null,
    @SerialName("credit_line")
    val creditLine: String,
    @SerialName("catalogue_display")
    val catalogueDisplay: String? = null,
    @SerialName("publication_history")
    val publicationHistory: String? = null,
    @SerialName("exhibition_history")
    val exhibitionHistory: String? = null,
    @SerialName("provenance_text")
    val provenanceText: String? = null,
    @SerialName("edition")
    val edition: String? = null,
    @SerialName("publishing_verification_level")
    val publishingVerificationLevel: String,
    @SerialName("internal_department_id")
    val internalDepartmentId: Int,
    @SerialName("fiscal_year")
    val fiscalYear: Int? = null,
    @SerialName("fiscal_year_deaccession")
    val fiscalYearDeaccession: Int? = null,
    @SerialName("is_public_domain")
    val isPublicDomain: Boolean,
    @SerialName("is_zoomable")
    val isZoomable: Boolean,
    @SerialName("max_zoom_window_size")
    val maxZoomWindowSize: Int,
    @SerialName("copyright_notice")
    val copyrightNotice: String? = null,
    @SerialName("has_multimedia_resources")
    val hasMultimediaResources: Boolean,
    @SerialName("has_educational_resources")
    val hasEducationalResources: Boolean,
    @SerialName("has_advanced_imaging")
    val hasAdvancedImaging: Boolean,
    @SerialName("colorfulness")
    val colorfulness: Double,
    @SerialName("color")
    val color: String? = null, // Type uncertain from null, assuming String or Object
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
    @SerialName("latlon")
    val latLon: String? = null, // Type uncertain from null
    @SerialName("is_on_view")
    val isOnView: Boolean,
    @SerialName("on_loan_display")
    val onLoanDisplay: String? = null,
    @SerialName("gallery_title")
    val galleryTitle: String? = null,
    @SerialName("gallery_id")
    val galleryId: Int? = null,
    @SerialName("nomisma_id")
    val nomismaId: String? = null,
    @SerialName("artwork_type_title")
    val artworkTypeTitle: String,
    @SerialName("artwork_type_id")
    val artworkTypeId: Int,
    @SerialName("department_title")
    val departmentTitle: String,
    @SerialName("department_id")
    val departmentId: String,
    @SerialName("artist_id")
    val artistId: Int,
    @SerialName("artist_title")
    val artistTitle: String,
    @SerialName("alt_artist_ids")
    val altArtistIds: List<Int>,
    @SerialName("artist_ids")
    val artistIds: List<Int>,
    @SerialName("artist_titles")
    val artistTitles: List<String>,
    @SerialName("category_ids")
    val categoryIds: List<String>,
    @SerialName("category_titles")
    val categoryTitles: List<String>,
    @SerialName("term_titles")
    val termTitles: List<String>,
    @SerialName("style_id")
    val styleId: String? = null,
    @SerialName("style_title")
    val styleTitle: String? = null,
    @SerialName("alt_style_ids")
    val altStyleIds: List<String>,
    @SerialName("style_ids")
    val styleIds: List<String>,
    @SerialName("style_titles")
    val styleTitles: List<String>,
    @SerialName("classification_id")
    val classificationId: String,
    @SerialName("classification_title")
    val classificationTitle: String,
    @SerialName("alt_classification_ids")
    val altClassificationIds: List<String>,
    @SerialName("classification_ids")
    val classificationIds: List<String>,
    @SerialName("classification_titles")
    val classificationTitles: List<String>,
    @SerialName("subject_id")
    val subjectId: String? = null,
    @SerialName("alt_subject_ids")
    val altSubjectIds: List<String>,
    @SerialName("subject_ids")
    val subjectIds: List<String>,
    @SerialName("subject_titles")
    val subjectTitles: List<String>,
    @SerialName("material_id")
    val materialId: String? = null,
    @SerialName("alt_material_ids")
    val altMaterialIds: List<String>,
    @SerialName("material_ids")
    val materialIds: List<String>,
    @SerialName("material_titles")
    val materialTitles: List<String>,
    @SerialName("technique_id")
    val techniqueId: String? = null,
    @SerialName("alt_technique_ids")
    val altTechniqueIds: List<String>,
    @SerialName("technique_ids")
    val techniqueIds: List<String>,
    @SerialName("technique_titles")
    val techniqueTitles: List<String>,
    @SerialName("theme_titles")
    val themeTitles: List<String>,
    @SerialName("image_id")
    val imageId: String,
    @SerialName("alt_image_ids")
    val altImageIds: List<String>,
    @SerialName("document_ids")
    val documentIds: List<String>,
    @SerialName("sound_ids")
    val soundIds: List<String>,
    @SerialName("video_ids")
    val videoIds: List<String>,
    @SerialName("text_ids")
    val textIds: List<String>,
    @SerialName("section_ids")
    val sectionIds: List<String>,
    @SerialName("section_titles")
    val sectionTitles: List<String>,
    @SerialName("site_ids")
    val siteIds: List<String>,
    @SerialName("suggest_autocomplete_all")
    val suggestAutocompleteAll: List<AutocompleteSuggestion>,
    @SerialName("source_updated_at")
    val sourceUpdatedAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("timestamp")
    val timestamp: String
)

@Serializable
data class Thumbnail(
    @SerialName("lqip")
    val lqip: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("alt_text")
    val altText: String
)

@Serializable
data class DimensionsDetail(
    @SerialName("depth")
    val depth: Int? = null,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("diameter")
    val diameter: Int? = null,
    @SerialName("clarification")
    val clarification: String? = null
)

@Serializable
data class AutocompleteSuggestion(
    @SerialName("input")
    val input: List<String>,
    @SerialName("contexts")
    val contexts: Contexts,
    @SerialName("weight")
    val weight: Int? = null
)

@Serializable
data class Contexts(
    @SerialName("groupings")
    val groupings: List<String>
)

@Serializable
data class Info(
    @SerialName("license_text")
    val licenseText: String,
    @SerialName("license_links")
    val licenseLinks: List<String>,
    @SerialName("version")
    val version: String
)

@Serializable
data class Config(
    @SerialName("iiif_url")
    val iiifUrl: String,
    @SerialName("website_url")
    val websiteUrl: String
)