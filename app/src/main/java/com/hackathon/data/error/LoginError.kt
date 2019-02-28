package com.hackathon.data.error

import com.hackathon.R
import com.hackathon.ui.util.fromContextString
import android.content.Context

class LoginError(val loginErrorType: LoginErrorType) : BaseError() {
    override fun parseTitleAndMessage(context: Context): Pair<String, String> = when (loginErrorType) {
        LoginErrorType.INVALID_EMAIL -> Pair(R.string.app_name, R.string.app_name).fromContextString(context)
    }
}
