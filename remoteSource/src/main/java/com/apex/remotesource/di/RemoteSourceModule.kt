package com.apex.remotesource.di

import com.apex.localsource.di.localSourceModule
import com.apex.remotesource.Constants
import com.apex.remotesource.api.APIService
import com.apex.remotesource.datasources.CharacterRemoteSource
import com.apex.remotesource.interceptors.AuthenticatorHeaderInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideOkHttpClient(
    authenticatorInterceptor: AuthenticatorHeaderInterceptor,
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY),
    )
    .addInterceptor(authenticatorInterceptor)
    .build()

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()

fun provideApiClient(retrofit: Retrofit): APIService =
    retrofit.create(APIService::class.java)

val remoteSourceModule = module {
    includes(localSourceModule)
    single { AuthenticatorHeaderInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
    single { provideApiClient(get()) }

    single { CharacterRemoteSource(get(), get(), get()) }
}