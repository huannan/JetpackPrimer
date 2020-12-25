package com.nan.jetpackprimer.livedata.simple4

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateService {

    /**
     * 获取每日一词接口
     * 新版本的Retrofit支持直接声明成挂起函数，并且函数直接返回网络返回数据
     */
    @GET("dailyword")
    suspend fun requestDailyWord(): BaseResult<String>

    /**
     * 翻译接口
     */
    @GET("translate")
    suspend fun requestTranslateResult(@Query("input") input: String): BaseResult<String>

    companion object {

        private const val BASE_URL = "http://172.16.47.80:8080/TestServer/"
        private var service: TranslateService? = null

        /**
         * 通过Retrofit的动态代理生成TranslateService实现类
         */
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