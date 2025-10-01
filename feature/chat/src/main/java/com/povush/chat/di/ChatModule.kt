package com.povush.chat.di

import com.povush.chat.network.dto.QuestDto
import com.povush.chat.BuildConfig
import com.povush.chat.service.QuestGeneratorLLMService
import com.povush.chat.ChatConfig
import com.povush.chat.network.OpenRouterService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ChatModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.OPENROUTER_API_KEY}")
                .build()
            chain.proceed(request)
        }

        val okHttp = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ChatConfig.OPEN_ROUTER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttp)
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenRouterService(
        retrofit: Retrofit
    ): OpenRouterService = retrofit.create(OpenRouterService::class.java)

    @Provides
    @Singleton
    fun provideQuestAdapter(
        moshi: Moshi
    ): JsonAdapter<QuestDto> = moshi.adapter(QuestDto::class.java)

    @Provides
    @Singleton
    fun provideQuestGeneratorLLMService(
        openRouterService: OpenRouterService,
        questAdapter: JsonAdapter<QuestDto>
    ) = QuestGeneratorLLMService(openRouterService, questAdapter)
}