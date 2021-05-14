package com.login.utils

import okhttp3.Interceptor
import okhttp3.Response


class NetworkUtility {
    open class KotlinDemoInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val moreHeaders = request.headers.newBuilder()
                .add("Cache-control", "no-cache")
                .build()
            request = request.newBuilder().headers(moreHeaders).build()
            return chain.proceed(request)
        }
    }

}