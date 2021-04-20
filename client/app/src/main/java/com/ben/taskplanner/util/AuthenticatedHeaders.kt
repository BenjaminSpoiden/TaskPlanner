package com.ben.taskplanner.util

typealias Headers = HashMap<String, String>

class AuthenticatedHeaders: Headers()

class HeadersProvider {

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val VERIFICATION_TOKEN = "Verification-Token"
    }


    fun getAuthenticatedHeader(accessToken: String?): AuthenticatedHeaders {
        return AuthenticatedHeaders().apply {
            accessToken?.let {
                put(AUTHORIZATION, it)
            }
        }
    }

    fun getVerificationToken(verificationToken: String?): AuthenticatedHeaders {
        return AuthenticatedHeaders().apply {
            verificationToken?.let {
                put(VERIFICATION_TOKEN, it)
            }
        }
    }
}