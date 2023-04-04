package com.xr6sfoftware.tastyapp.di.modules

import com.xr6sfoftware.tastyapp.repositories.RecipesRepository
import com.xr6sfoftware.tastyapp.repositories.RecipesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun recipesRepository(repository: RecipesRepositoryImpl): RecipesRepository
}


