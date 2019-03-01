package com.canerkorkmaz.cicek.jobdistribution.solver

import com.canerkorkmaz.cicek.jobdistribution.model.InputDataFormatted
import com.canerkorkmaz.cicek.jobdistribution.model.JobData
import com.canerkorkmaz.cicek.jobdistribution.model.Solution
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack
import com.canerkorkmaz.cicek.jobdistribution.model.StoreData
import com.canerkorkmaz.cicek.jobdistribution.model.distanceSqr

/**
 * Greedy valid solution generator with a simple heuristic
 */
class GreedySolver(data: InputDataFormatted) : SolverBase(data) {
    override fun solve(): SolutionPack {
        val solutions = data.stores.keys.associateWith { mutableSetOf<JobData>() }
        val jobs = data.jobList.toMutableSet()
        // Put min number of closest jobs to each store
        for (store in data.storeList) {
            val jobsSorted = jobs.sortedBy { it.distanceSqr(store) }
                .take(store.min)
            solutions.getValue(store.name).addAll(jobsSorted)
            jobs.removeAll(jobsSorted)
        }
        val remainingDistances: Map<JobData, List<StoreData>> =
            jobs.associateWith {
                data.storeList.sortedBy { store -> store.distanceSqr(it) }
            }
        val availableStores = data.stores.keys.toMutableSet()
        for (store in data.stores) {
            if (solutions.getValue(store.key).size >= store.value.max) {
                availableStores.remove(store.key)
            }
        }
        // Put remaining jobs to the closest store that has available space
        for((job, stores) in remainingDistances) {
            val store = stores.first { availableStores.contains(it.name) }
            val sol = solutions.getValue(store.name)
            sol.add(job)
            if(sol.size >= store.max) {
                availableStores.remove(store.name)
            }
        }
        return SolutionPack(
            solutions.map { (key, value) -> Solution(data.stores.getValue(key), value) }
                .associateBy { it.store.name },
            data
        )
    }

}
