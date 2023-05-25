package com.kev.dictionary.feature_dictionary.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.kev.dictionary.feature_dictionary.core.util.Constants
import com.kev.dictionary.feature_dictionary.data.local.Converters
import com.kev.dictionary.feature_dictionary.data.local.WordInfoDao
import com.kev.dictionary.feature_dictionary.data.local.WordsDatabase
import com.kev.dictionary.feature_dictionary.data.remote.DictionaryApiService
import com.kev.dictionary.feature_dictionary.data.remote.util.GsonParser
import com.kev.dictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.kev.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.kev.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesWordInfoRepository(
        dao: WordInfoDao,
        apiService: DictionaryApiService
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun providesGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfoUseCase {
        return GetWordInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesWordsDatabase(@ApplicationContext context: Context): WordsDatabase{
        return Room.databaseBuilder(
            context,
            WordsDatabase::class.java,
            "words_db"
        ).addTypeConverter(Converters(GsonParser(Gson()))).build()
    }


    @Provides
    fun providesWordsDao(database: WordsDatabase): WordInfoDao{
        return database.dao
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): DictionaryApiService{
        return retrofit.create(DictionaryApiService::class.java)
    }
}
