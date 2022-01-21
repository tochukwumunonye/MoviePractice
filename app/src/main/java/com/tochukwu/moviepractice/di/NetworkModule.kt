package com.tochukwu.moviepractice.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.moshi.Moshi
import com.tochukwu.moviepractice.R
import com.tochukwu.moviepractice.data.remote.MovieAPI
import com.tochukwu.moviepractice.data.remote.model.MovieDtoMapper
import com.tochukwu.moviepractice.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMovieDtoMapper(): MovieDtoMapper = MovieDtoMapper()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun ProvideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(): Interceptor = Interceptor{chain: Interceptor.Chain ->
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .addHeader("x-rapidapi-key", "94e0db81a5msh73023859a138990p19da88jsn64bf7eff790b")
            .addHeader("x-rapidapi-host", "movie-database-imdb-alternative.p.rapidapi.com")
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )



    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieAPI = retrofit
        .create(MovieAPI::class.java)

}

