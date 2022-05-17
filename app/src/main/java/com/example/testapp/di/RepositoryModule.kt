package com.example.testapp.di

import com.example.testapp.reposytory.NewsRepository
import com.example.testapp.reposytory.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRepository(impl: NewsRepositoryImpl): NewsRepository
}