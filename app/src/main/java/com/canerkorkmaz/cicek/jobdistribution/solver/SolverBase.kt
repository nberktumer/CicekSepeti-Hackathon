package com.canerkorkmaz.cicek.jobdistribution.solver

import com.canerkorkmaz.cicek.jobdistribution.model.InputDataFormatted
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack

abstract class SolverBase(
    val data: InputDataFormatted
) {
    val name = this::class.simpleName!!
    val solution by lazy {
        solve()
    }

    protected abstract fun solve(): SolutionPack
    fun calculateSolution() = solution
}
