package com.login

import android.os.Build
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.gson.GsonBuilder
import com.login.api.ApiService
import com.login.base.BaseActivity
import com.login.utils.Constants
import com.login.utils.NetworkUtility
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginDemo: MultiDexApplication()  {

    private lateinit var apiService: ApiService
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        iniRetrofit()
    }

    private fun iniRetrofit() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(0, 5 * 60 * 1000, TimeUnit.SECONDS))
            .addInterceptor(object : NetworkUtility.KotlinDemoInterceptor() {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = request.newBuilder()
                        .addHeader("device-id", Build.MANUFACTURER)
                        .addHeader("device-os", "android")
                        .addHeader("accept-version", BuildConfig.VERSION_NAME)
                        .build()
                    Log.d("Headers", request.headers.toString())
                    return chain.proceed(request)
                }
            })

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }
        val gson = GsonBuilder().create()
        val okHttpClient = builder.build()
        retrofit = Retrofit.Builder().client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiService = retrofit.create(ApiService::class.java)

    }


    fun getAPIService(): ApiService {
        return apiService
    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }

}