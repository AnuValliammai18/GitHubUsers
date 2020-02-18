package com.example.searchusers

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi
{
    @GET("users")
    suspend fun getUsers(@Query("q") Login:String,
                 @Query("page") page:Int): Users

    companion object
    {
        operator fun invoke():UsersApi
        {
            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("factory", message)
                }
            })
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

            return Retrofit.Builder()
                .baseUrl("https://api.github.com/search/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsersApi::class.java)
        }
    }
}