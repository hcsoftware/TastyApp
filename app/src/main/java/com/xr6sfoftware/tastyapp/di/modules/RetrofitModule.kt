package com.xr6sfoftware.tastyapp.di.modules

import com.xr6sfoftware.tastyapp.network.NetworkConstants
import com.xr6sfoftware.tastyapp.network.RecipeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("X-RapidAPI-Key", NetworkConstants.RAPID_API_KEY)
                .header("X-RapidAPI-Host", NetworkConstants.RAPID_API_HOST)
                .build()
            chain.proceed(request)
        }
        .build()

    @Singleton
    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService =
        retrofit.create(RecipeApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}