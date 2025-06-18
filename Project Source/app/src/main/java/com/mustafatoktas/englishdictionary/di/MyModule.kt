package com.mustafatoktas.englishdictionary.di

import android.content.Context
import com.mustafatoktas.englishdictionary.common.Constants
import com.mustafatoktas.englishdictionary.common.MyDataStore
import com.mustafatoktas.englishdictionary.data.remote.DictionaryApi
import com.mustafatoktas.englishdictionary.data.repositoryImpl.DictionaryRepositoryImpl
import com.mustafatoktas.englishdictionary.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Provides
    @Singleton
    fun providesDictionaryApi() : DictionaryApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create()
    }


    @Provides
    @Singleton
    fun provideMyDataStore(@ApplicationContext context: Context): MyDataStore {
        return MyDataStore(context)
    }

}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDictionaryRepository(dictionaryRepositoryImpl: DictionaryRepositoryImpl): DictionaryRepository
}