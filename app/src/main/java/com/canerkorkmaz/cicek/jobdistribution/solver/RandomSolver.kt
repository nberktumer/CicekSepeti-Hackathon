package com.canerkorkmaz.cicek.jobdistribution.solver

import com.canerkorkmaz.cicek.jobdistribution.model.InputDataFormatted
import com.canerkorkmaz.cicek.jobdistribution.model.JobData
import com.canerkorkmaz.cicek.jobdistribution.model.Solution
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack

/**
 * Random valid solution generator for test purposes, should be the worst in general
 */
class RandomSolver(data: InputDataFormatted) : SolverBase(data) {
    override fun solve(): SolutionPack {
        var currentStart = 0
        val solutions = data.stores.keys.associateWith { mutableSetOf<JobData>() }
        val list = data.jobList.shuffled()
        for (store in data.storeList) {
            solutions.getValue(store.name).addAll(list.subList(currentStart, currentStart + store.min))
            currentStart += store.min
        }
        val availableStores = data.stores.keys.toMutableSet()
        for (store in data.stores) {
            if (solutions.getValue(store.key).size >= store.value.max) {
                availableStores.remove(store.key)
            }
        }
        for (i in currentStart until list.size) {
            val storeKey = availableStores.random()
            val store = data.stores.getValue(storeKey)
            val storeSolution = solutions.getValue(storeKey)
            storeSolution.add(list[i])
            if (storeSolution.size >= store.max) {
                availableStores.remove(storeKey)
            }
        }
        return SolutionPack(
            solutions.map { (key, value) -> Solution(data.stores.getValue(key), value) }
                .associateBy { it.store.name },
            data
        )
    }

}
