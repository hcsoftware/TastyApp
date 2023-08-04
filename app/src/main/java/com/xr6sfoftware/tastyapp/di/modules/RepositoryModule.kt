package com.xr6sfoftware.tastyapp.di.modules

import com.xr6sfoftware.tastyapp.repositories.RecipesRepository
import com.xr6sfoftware.tastyapp.repositories.RecipesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 This class provides the repository instance via Hilt Injection.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRecipesRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository

}
