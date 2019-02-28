package com.hackathon.domain.auth

import com.hackathon.data.error.BaseError
import com.hackathon.di.ILogger
import com.hackathon.di.IValidator
import com.hackathon.domain.base.BaseTask
import com.hackathon.lib.typing.Ok
import com.hackathon.lib.typing.Result


class LoginTask(
        private val validator: IValidator,
        private val logger: ILogger
) : BaseTask() {
    fun execute(): Result<Unit, BaseError> {
        return Ok(Unit)
    }
}

