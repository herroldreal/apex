package com.apex.remotesource.interceptors

import com.apex.remotesource.Constants.API_KEY
import com.apex.remotesource.Constants.API_KEY_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticatorHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (chain.request().headers[API_KEY_HEADER].isNullOrEmpty()) {
            builder.header(API_KEY, API_KEY)
        }

        return chain.proceed(builder.build())
    }
}