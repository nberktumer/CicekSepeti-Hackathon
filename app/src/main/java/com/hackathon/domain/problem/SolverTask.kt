package com.hackathon.domain.problem

import android.content.Context
import com.beust.klaxon.Klaxon
import com.canerkorkmaz.cicek.jobdistribution.model.InputData
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack
import com.canerkorkmaz.cicek.jobdistribution.solve
import com.hackathon.Constants
import com.hackathon.data.error.BaseError
import com.hackathon.data.error.ServerError
import com.hackathon.di.ILogger
import com.hackathon.domain.base.BaseTask
import com.hackathon.lib.typing.*


class SolverTask(
        private val context: Context,
        private val logger: ILogger
) : BaseTask() {
    fun execute(): SingleResult<SolutionPack, BaseError> {
        val inputStream = context.assets.open(Constants.FILE_NAME)
        val data = Klaxon().parse<InputData>(inputStream)?.ok()
                ?: Err(ServerError("Cannot read file"))
        return data.map { solve(it) }
                .single()
    }
}

