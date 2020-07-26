package com.schatzdesigns.covid19stats.di.modules

import com.schatzdesigns.covid19stats.BuildConfig
import com.schatzdesigns.covid19stats.api.StatisticsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModuleApiService {

    companion object {
        private const val BASE_URL = "base_url"
    }

    @Provides
    @Named(BASE_URL)
    internal fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return when {
            BuildConfig.DEBUG -> {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            }
            else -> {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
                }
            }
        }
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(httpInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpInterceptor)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named(BASE_URL)
    internal fun provideBaseRetrofit(
        @Named(BASE_URL) baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return provideRetrofit(baseUrl, okHttpClient)
    }

    private fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideStatisticsService(@Named(BASE_URL) retrofit: Retrofit): StatisticsService {
        return retrofit.create(StatisticsService::class.java)
    }

}
