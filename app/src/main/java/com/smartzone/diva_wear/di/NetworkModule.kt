package com.smartzone.diva_wear.di

import android.content.Context
import android.os.Build
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.smartzone.diva_wear.BuildConfig
import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.AppPreferencesHelper
import com.smartzone.diva_wear.utilis.BASE_URL
import com.smartzone.diva_wear.utilis.LogUtil
import com.smartzone.diva_wear.utilis.REQUEST_TIMEOUT

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


val retrofitClient = module {

    fun cache(contex: Context): Cache {

        return Cache(
            File(contex.cacheDir, "okhttp_cach"),
            (10 * 1000 * 1000).toLong()
        ) // 10MB
    }


    fun interceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                try {
                    val url = request.url().toString()
                    LogUtil.error("urlHelper", url)
                    LogUtil.error(
                        "bodyHelper",
                        bodyToString(request.body()) + ""
                    )
                    LogUtil.error(
                        "headerHelper",
                        request.headers().toString()
                    )
                    val response = chain.proceed(request)
                    val bodyString: String? = response.body()?.string()
                    LogUtil.error(bodyToString(request.body()), bodyString)
                    return response.newBuilder()
                        .body(ResponseBody.create(response.body()?.contentType(), bodyString))
                        .build()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return chain.proceed(request)
                }
            }

            private fun bodyToString(body: RequestBody?): String {
                try {
                    val buffer = Buffer()
                    if (body != null)
                        body.writeTo(buffer)
                    else
                        return ""
                    return buffer.readUtf8()
                } catch (e: IOException) {
                    return "did not work"
                }
            }
        }
    }


    fun provideOkHttpClient(context: Context, intercaptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor1 = HttpLoggingInterceptor()
            interceptor1.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor1)
        }
        client.addInterceptor(intercaptor)
        client.cache(cache(context))
        client.connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        client.writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
        return client.build()
    }

    fun createWebService(okHttpClient: OkHttpClient): APIHelper {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        return retrofit.create(APIHelper::class.java)
    }
    single {
        provideOkHttpClient(get(), get())
    }
    single {
        interceptor()
    }
    single { createWebService(get()) }

    single { AppPreferencesHelper(get()) }
}

