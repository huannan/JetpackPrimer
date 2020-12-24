package com.nan.jetpackprimer.livedata.simple4

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {

    @GET("translate")
    suspend fun requestTranslateResult(@Query("input") input: String): BaseResult<String>

    companion object {

        private const val BASE_URL = "http://172.16.47.80:8080/TestServer/"
        private var service: TranslateService? = null

        fun getApi(): TranslateService {
            if (null == service) {
                val httpLoggingInterceptor =
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

                val client = OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                service = retrofit.create(TranslateService::class.java)
            }

            return service!!
        }
    }

}