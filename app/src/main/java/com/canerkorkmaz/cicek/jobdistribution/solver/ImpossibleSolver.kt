package com.canerkorkmaz.cicek.jobdistribution.solver

import com.canerkorkmaz.cicek.jobdistribution.model.InputDataFormatted
import com.canerkorkmaz.cicek.jobdistribution.model.JobData
import com.canerkorkmaz.cicek.jobdistribution.model.Solution
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack
import com.canerkorkmaz.cicek.jobdistribution.model.distanceSqr

/**
 * This is an impossible solution generator for given a theoretical lower bound
 */
class ImpossibleSolver(data: InputDataFormatted) : SolverBase(data) {
    override fun solve(): SolutionPack {
        val solutions: Map<String, MutableSet<JobData>> = data.storeList.associateBy({ it.name }) { HashSet<JobData>() }
        for (job in data.jobList) {
            val store = data.storeList.minBy { it.distanceSqr(job) }!!
            solutions.getValue(store.name).add(job)
        }
        return SolutionPack(data = data,
            stores = solutions.mapValues { (key, value) ->
                Solution(data.stores.getValue(key), value)
            })
    }
}
