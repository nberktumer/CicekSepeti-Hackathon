package com.hackathon.ui.home

import com.hackathon.data.error.ServerError
import com.hackathon.di.ILogger
import com.hackathon.di.module.SchedulersModule
import com.hackathon.domain.auth.LoginTask
import com.hackathon.lib.event.ObservableResult
import com.hackathon.lib.typing.Err
import com.hackathon.lib.typing.single
import com.hackathon.ui.base.BaseViewModel
import androidx.lifecycle.MutableLiveData

class HomeViewModel(
        private val logger: ILogger,
        private val loginTask: LoginTask,
        schedulersModule: SchedulersModule
) : BaseViewModel(schedulersModule) {
    // Username and password for two way binding in login form
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    // Events

    // On Login Finished event with a result type with unit success and string error
    // View handles whether to navigate or show error with given result
    val onLoginFinished = ObservableResult<Unit>()

    // Button Listeners
    // Login Button Click listener
    fun onLoginClick() {
        // Call login function with given values when login button is clicked
        subscribe(
                event = onLoginFinished,
                disposable = { loginTask.execute().single() },
                onSuccess = { it },
                onError = { Err(ServerError(it.message ?: "Unknown Error")) }
        )
    }
}
