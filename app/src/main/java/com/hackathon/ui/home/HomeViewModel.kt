package com.hackathon.ui.home

import com.hackathon.data.error.ServerError
import com.hackathon.di.ILogger
import com.hackathon.di.module.SchedulersModule
import com.hackathon.domain.problem.SolverTask
import com.hackathon.lib.event.ObservableResult
import com.hackathon.lib.typing.Err
import com.hackathon.lib.typing.single
import com.hackathon.ui.base.BaseViewModel

class HomeViewModel(
        private val logger: ILogger,
        private val solverTask: SolverTask,
        schedulersModule: SchedulersModule
) : BaseViewModel(schedulersModule) {
    val onProblemSolved = ObservableResult<Unit>()

    // Button Listeners
    // Login Button Click listener
    fun solveProblem() {
        // Call login function with given values when login button is clicked
        subscribe(
                event = onProblemSolved,
                disposable = { solverTask.execute().single() },
                onSuccess = { it },
                onError = { Err(ServerError(it.message ?: "Unknown Error")) }
        )
    }
}
