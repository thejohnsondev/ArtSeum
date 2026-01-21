package com.thejohnsondev.network.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtResponse(
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
    val id: Int? = null,
    @SerialName("api_model")
    val apiModel: String? = null,
    @SerialName("api_link")
    val apiLink: String? = null,
    @SerialName("is_boosted")
    val isBoosted: Boolean? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("alt_titles")
    val altTitles: List<String>? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?? = null,
    @SerialName("main_reference_number")
    val mainReferenceNumber: String? = null,
    @SerialName("has_not_been_viewed_much")
    val hasNotBeenViewedMuch: Boolean? = null,
    @SerialName("boost_rank")
    val boostRank: Int? = null,
    @SerialName("date_start")
    val dateStart: Int? = null,
    @SerialName("date_end")
    val dateEnd: Int? = null,
    @SerialName("date_display")
    val dateDisplay: String? = null,
    @SerialName("date_qualifier_title")
    val dateQualifierTitle: String? = null,
    @SerialName("date_qualifier_id")
    val dateQualifierId: Int? = null,
    @SerialName("artist_display")
    val artistDisplay: String? = null,
    @SerialName("place_of_origin")
    val placeOfOrigin: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("short_description")
    val shortDescription: String? = null,
    @SerialName("dimensions")
    val dimensions: String? = null,
    @SerialName("dimensions_detail")
    val dimensionsDetail: List<DimensionsDetail>? = null,
    @SerialName("medium_display")
    val mediumDisplay: String? = null,
    @SerialName("inscriptions")
    val inscriptions: String? = null,
    @SerialName("credit_line")
    val creditLine: String? = null,
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
    val publishingVerificationLevel: String? = null,
    @SerialName("internal_department_id")
    val internalDepartmentId: Int? = null,
    @SerialName("fiscal_year")
    val fiscalYear: Int? = null,
    @SerialName("fiscal_year_deaccession")
    val fiscalYearDeaccession: Int? = null,
    @SerialName("is_public_domain")
    val isPublicDomain: Boolean? = null,
    @SerialName("is_zoomable")
    val isZoomable: Boolean? = null,
    @SerialName("max_zoom_window_size")
    val maxZoomWindowSize: Int? = null,
    @SerialName("copyright_notice")
    val copyrightNotice: String? = null,
    @SerialName("has_multimedia_resources")
    val hasMultimediaResources: Boolean? = null,
    @SerialName("has_educational_resources")
    val hasEducationalResources: Boolean? = null,
    @SerialName("has_advanced_imaging")
    val hasAdvancedImaging: Boolean? = null,
    @SerialName("colorfulness")
    val colorfulness: Double? = null,
    @SerialName("color")
    val color: ColorInfo? = null,
    @SerialName("latitude")
    val latitude: Double? = null,
    @SerialName("longitude")
    val longitude: Double? = null,
    @SerialName("latlon")
    val latLon: String? = null, // Type uncertain from null
    @SerialName("is_on_view")
    val isOnView: Boolean? = null,
    @SerialName("on_loan_display")
    val onLoanDisplay: String? = null,
    @SerialName("gallery_title")
    val galleryTitle: String? = null,
    @SerialName("gallery_id")
    val galleryId: Int? = null,
    @SerialName("nomisma_id")
    val nomismaId: String? = null,
    @SerialName("artwork_type_title")
    val artworkTypeTitle: String? = null,
    @SerialName("artwork_type_id")
    val artworkTypeId: Int? = null,
    @SerialName("department_title")
    val departmentTitle: String? = null,
    @SerialName("department_id")
    val departmentId: String? = null,
    @SerialName("artist_id")
    val artistId: Int? = null,
    @SerialName("artist_title")
    val artistTitle: String? = null,
    @SerialName("alt_artist_ids")
    val altArtistIds: List<Int>? = null,
    @SerialName("artist_ids")
    val artistIds: List<Int>? = null,
    @SerialName("artist_titles")
    val artistTitles: List<String>? = null,
    @SerialName("category_ids")
    val categoryIds: List<String>? = null,
    @SerialName("category_titles")
    val categoryTitles: List<String>? = null,
    @SerialName("term_titles")
    val termTitles: List<String>? = null,
    @SerialName("style_id")
    val styleId: String? = null,
    @SerialName("style_title")
    val styleTitle: String? = null,
    @SerialName("alt_style_ids")
    val altStyleIds: List<String>? = null,
    @SerialName("style_ids")
    val styleIds: List<String>? = null,
    @SerialName("style_titles")
    val styleTitles: List<String>? = null,
    @SerialName("classification_id")
    val classificationId: String? = null,
    @SerialName("classification_title")
    val classificationTitle: String? = null,
    @SerialName("alt_classification_ids")
    val altClassificationIds: List<String>? = null,
    @SerialName("classification_ids")
    val classificationIds: List<String>? = null,
    @SerialName("classification_titles")
    val classificationTitles: List<String>? = null,
    @SerialName("subject_id")
    val subjectId: String? = null,
    @SerialName("alt_subject_ids")
    val altSubjectIds: List<String>? = null,
    @SerialName("subject_ids")
    val subjectIds: List<String>? = null,
    @SerialName("subject_titles")
    val subjectTitles: List<String>? = null,
    @SerialName("material_id")
    val materialId: String? = null,
    @SerialName("alt_material_ids")
    val altMaterialIds: List<String>? = null,
    @SerialName("material_ids")
    val materialIds: List<String>? = null,
    @SerialName("material_titles")
    val materialTitles: List<String>? = null,
    @SerialName("technique_id")
    val techniqueId: String? = null,
    @SerialName("alt_technique_ids")
    val altTechniqueIds: List<String>? = null,
    @SerialName("technique_ids")
    val techniqueIds: List<String>? = null,
    @SerialName("technique_titles")
    val techniqueTitles: List<String>? = null,
    @SerialName("theme_titles")
    val themeTitles: List<String>? = null,
    @SerialName("image_id")
    val imageId: String? = null,
    @SerialName("alt_image_ids")
    val altImageIds: List<String>? = null,
    @SerialName("document_ids")
    val documentIds: List<String>? = null,
    @SerialName("sound_ids")
    val soundIds: List<String>? = null,
    @SerialName("video_ids")
    val videoIds: List<String>? = null,
    @SerialName("text_ids")
    val textIds: List<String>? = null,
    @SerialName("section_ids")
    val sectionIds: List<String>? = null,
    @SerialName("section_titles")
    val sectionTitles: List<String>? = null,
    @SerialName("site_ids")
    val siteIds: List<String>? = null,
    @SerialName("suggest_autocomplete_all")
    val suggestAutocompleteAll: List<AutocompleteSuggestion>? = null,
    @SerialName("source_updated_at")
    val sourceUpdatedAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null,
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
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null,
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

@Serializable
data class ColorInfo(
    @SerialName("h") val h: Int,
    @SerialName("l") val l: Int,
    @SerialName("s") val s: Int,
    @SerialName("percentage") val percentage: Double,
    @SerialName("population") val population: Int
)