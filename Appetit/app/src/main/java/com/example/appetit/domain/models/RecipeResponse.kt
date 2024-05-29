package com.example.appetit.domain.models

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("from") val from: Int,
    @SerializedName("to") val to: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("_links") val links: Links,
    @SerializedName("hits") val hits: List<RecipeHit>
)

data class Links(
    @SerializedName("self") val self: LinkInfo,
    @SerializedName("next") val next: LinkInfo? = null
)

data class LinkInfo(
    @SerializedName("href") val href: String,
    @SerializedName("title") val title: String
)


data class RecipeHit(
    @SerializedName("recipe") val recipe: Recipe,
    @SerializedName("_links") val links: Links
)

data class Recipe(
    @SerializedName("uri") val uri: String,
    @SerializedName("label") val label: String,
    @SerializedName("image") val image: String?,
    @SerializedName("images") val images: RecipeImages,
    @SerializedName("url") val url: String,
    @SerializedName("shareAs") val shareAs: String,
    @SerializedName("yield") val yield: Double,
    @SerializedName("dietLabels") val dietLabels: List<String>?,
    @SerializedName("healthLabels") val healthLabels: List<String>?,
    @SerializedName("cautions") val cautions: List<String>?,
    @SerializedName("ingredientLines") val ingredientLines: List<String>?,
    @SerializedName("ingredients") val ingredients: List<Ingredient?>,
    @SerializedName("calories") val calories: Double,
    @SerializedName("totalWeight") val totalWeight: Double,
    @SerializedName("cuisineType") val cuisineType: List<String>?,
    @SerializedName("mealType") val mealType: List<String>?,
    @SerializedName("dishType") val dishType: List<String>?,
    @SerializedName("instructions") val instructions: List<String>?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("externalId") val externalId: String,
    @SerializedName("totalNutrients") val totalNutrients: Map<String, Nutrient>,
    @SerializedName("totalDaily") val totalDaily: Map<String, Nutrient>,
    @SerializedName("digest") val digest: List<NutrientDigest>
)


data class RecipeImages(
    @SerializedName("THUMBNAIL") val thumbnail: ImageInfo,
    @SerializedName("SMALL") val small: ImageInfo,
    @SerializedName("REGULAR") val regular: ImageInfo,
    @SerializedName("LARGE") val large: ImageInfo
)

data class ImageInfo(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)

data class Ingredient(
    @SerializedName("text") val text: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("measure") val measure: String,
    @SerializedName("food") val food: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("foodId") val foodId: String
)

data class Nutrient(
    @SerializedName("label") val label: String,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("unit") val unit: String
)

data class NutrientDigest(
    @SerializedName("label") val label: String,
    @SerializedName("tag") val tag: String,
    @SerializedName("schemaOrgTag") val schemaOrgTag: String,
    @SerializedName("total") val total: Double,
    @SerializedName("hasRDI") val hasRDI: Boolean,
    @SerializedName("daily") val daily: Double,
    @SerializedName("unit") val unit: String,
    @SerializedName("sub") val sub: List<NutrientDigest>?
)


