package com.example.moniepointtestapp.di

import android.content.Context
import com.example.moniepointtestapp.utils.JsonDecoder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesJsonDecoder(
        @ApplicationContext appContext: Context,
        moshi: Moshi
    ): JsonDecoder {
        return JsonDecoder(appContext, moshi)
    }
}
