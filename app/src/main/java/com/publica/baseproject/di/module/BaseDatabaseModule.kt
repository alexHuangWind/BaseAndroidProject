package com.publica.baseproject.di.module

import android.app.Application
import com.publica.baseproject.data.local.BasePostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class BaseDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application) = BasePostsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: BasePostsDatabase) = database.getPostsDao()

}