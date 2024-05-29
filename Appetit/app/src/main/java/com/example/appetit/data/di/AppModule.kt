package com.example.appetit.data.di

import android.app.Application
import androidx.room.Room
import com.example.appetit.data.local.AppDatabase
import com.example.appetit.data.local.FavoriteRecipeDao
import com.example.appetit.data.network.AuthInterceptor
import com.example.appetit.data.network.EdamamApiService
import com.example.appetit.domain.repositories.FavoriteRepository
import com.example.appetit.domain.repositories.RecipeRepository
import com.example.appetit.domain.usecases.AddToFavoritesUseCase
import com.example.appetit.domain.usecases.RemoveFromFavoritesUseCase
import com.example.appetit.domain.usecases.GetFavoriteRecipesUseCase
import com.example.appetit.domain.usecases.GetRandomRecipeUseCase
import com.example.appetit.domain.usecases.GetRecipeUseCase
import com.example.appetit.domain.usecases.IsFavoriteUseCase
import com.example.appetit.domain.usecases.SearchRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEdamamApiService(retrofit: Retrofit): EdamamApiService {
        return retrofit.create(EdamamApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(authInterceptor: AuthInterceptor): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(api: EdamamApiService): RecipeRepository {
        return RecipeRepository(api)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteRecipeDao: FavoriteRecipeDao): FavoriteRepository {
        return FavoriteRepository(favoriteRecipeDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "appetit-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteRecipeDao(appDatabase: AppDatabase): FavoriteRecipeDao {
        return appDatabase.favoriteRecipeDao()
    }

    @Provides
    @Singleton
    fun provideGetFavoriteRecipesUseCase(favoriteRepository: FavoriteRepository): GetFavoriteRecipesUseCase {
        return GetFavoriteRecipesUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideAddToFavoritesUseCase(favoriteRepository: FavoriteRepository): AddToFavoritesUseCase {
        return AddToFavoritesUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromFavoritesUseCase(favoriteRepository: FavoriteRepository): RemoveFromFavoritesUseCase {
        return RemoveFromFavoritesUseCase(favoriteRepository)
    }

    @Provides
    @Singleton
    fun provideIsFavoriteUseCase(favoriteRepository: FavoriteRepository): IsFavoriteUseCase {
        return IsFavoriteUseCase(favoriteRepository)
    }


    @Provides
    @Singleton
    fun provideSearchRecipesUseCase(recipeRepository: RecipeRepository): SearchRecipesUseCase {
        return SearchRecipesUseCase(recipeRepository)
    }
    @Provides
    @Singleton
    fun provideGetRandomRecipeUseCase(recipeRepository: RecipeRepository): GetRandomRecipeUseCase {
        return GetRandomRecipeUseCase(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideGetRecipeUseCase(recipeRepository: RecipeRepository): GetRecipeUseCase {
        return GetRecipeUseCase(recipeRepository)
    }

}
