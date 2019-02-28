package com.hackathon.data.error

enum class LoginErrorType {
    INVALID_EMAIL;

    fun toError(): LoginError =
            LoginError(this)
}