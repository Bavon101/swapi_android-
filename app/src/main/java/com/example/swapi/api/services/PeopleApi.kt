package com.example.swapi.api.services

import com.example.swapi.api.models.People
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://us-central1-allmen-a006d.cloudfunctions.net/app/"

interface PeopleApi {

    @GET("people")
    suspend fun getPeople(@Query("query" ) query:String?, @Query("page") page: String?): People


    companion object {
        private var peopleApi: PeopleApi? = null
        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        fun getInstance(): PeopleApi {
            if (peopleApi == null) {
                peopleApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build().create(PeopleApi::class.java)
            }
            return peopleApi!!
        }
    }
}

interface  SearchApi {
    @GET("search")
    suspend fun getPeople(@Query("query" ) query:String?): People


    companion object {
        var searchApi: SearchApi? = null
        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        fun getInstance(): SearchApi {
            if (searchApi == null) {
                searchApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build().create(SearchApi::class.java)
            }
            return searchApi!!
        }
    }
}