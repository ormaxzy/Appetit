package com.example.appetit.data.network

import com.example.appetit.data.models.RecipeResponse
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("app_id", "0ee6b7f6") // 0ee6b7f6 & fa6fb6cb
            .addQueryParameter(
                "app_key",
                "9e900c360412e4e80eaefcb31018441c"
            ) // 9e900c360412e4e80eaefcb31018441c & c8d1a050ed66ce83f547a2cfefafa760
            .addQueryParameter("type", "public")
            .build()

        val requestBuilder = original.newBuilder().url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

interface EdamamApiService {
    @GET("api/recipes/v2")
    suspend fun searchRecipes(
        @Query("q") query: String? = null,
        @Query("mealType") mealType: List<String>? = null,
        @Query("cuisineType") cuisineType: List<String>? = null,
        @Query("dishType") dishType: List<String>? = null,
        @Query("health") health: List<String>? = null
    ): RecipeResponse

    @GET
    suspend fun getNextPage(@Url url: String): RecipeResponse

    @GET("api/recipes/v2/by-uri")
    suspend fun getRecipe(
        @Query("uri") uri: List<String>
    ): RecipeResponse

    @GET("api/recipes/v2")
    suspend fun getRandomRecipes(
        @Query("random") random: Boolean = true,
        @Query("dishType") dishType: List<String> = listOf(
            "Biscuits and cookies",
            "Bread",
            "Cereals",
            "Condiments and sauces",
            "Desserts",
            "Drinks",
            "Main course",
            "Pancake",
            "Preps",
            "Preserve",
            "Salad",
            "Sandwiches",
            "Side dish",
            "Soup",
            "Starter",
            "Sweets"
        )
    ): RecipeResponse
}

