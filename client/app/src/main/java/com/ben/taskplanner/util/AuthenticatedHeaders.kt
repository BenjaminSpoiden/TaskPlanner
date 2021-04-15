package com.ben.taskplanner.util

import javax.inject.Singleton


class AuthenticatedHeaders: Headers()

class HeadersProvider {

    companion object {
        private const val ACCEPT_LANGUAGE = "Accept-Language"
        private const val USER_AGENT = "User-Agent"
        private const val AUTHORIZATION = "Authorization"
        private const val HEADER_ACCEPT = "Accept"

    }


    fun getAuthenticatedHeader(accessToken: String?): AuthenticatedHeaders {
        return AuthenticatedHeaders().apply {
            accessToken?.let {
                put(AUTHORIZATION, it)
            }
        }
    }
}

open class Headers: HashMap<String, String>()