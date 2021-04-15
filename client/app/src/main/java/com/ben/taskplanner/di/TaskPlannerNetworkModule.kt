package com.ben.taskplanner.di

import com.ben.taskplanner.network.TaskPlannerService
import com.ben.taskplanner.util.HeadersProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskPlannerNetworkModule {

    @Singleton
    @Provides
    fun provideClient() =
        OkHttpClient.Builder().apply {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            this.addInterceptor(logging)
        }
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideGsonInstance(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideScalarsConverterFactory(): ScalarsConverterFactory = ScalarsConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitInstance(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory,
            scalarsConverterFactory: ScalarsConverterFactory
    ): Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4000")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(scalarsConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): TaskPlannerService = retrofit.create(TaskPlannerService::class.java)

    @Singleton
    @Provides
    fun provideHeaders(): HeadersProvider = HeadersProvider()

}