package com.canerkorkmaz.cicek.jobdistribution.model

import com.canerkorkmaz.cicek.jobdistribution.util.sum
import com.canerkorkmaz.cicek.jobdistribution.util.sumByDouble

data class Solution(
    val store: StoreData,
    val jobs: Set<JobData>
) {
    val isValid by lazy {
        store.min <= jobs.size && jobs.size <= store.max
    }

    val cost by lazy {
        jobs.sumByDouble { store.distanceSqr(it) }
    }
}

data class SolutionPack(
    val stores: Map<String, Solution>,
    val data: InputDataFormatted
) {
    val isValid by lazy {
        stores.all { entry -> entry.value.isValid }
                && data.jobList.size == stores.sum { _, solution -> solution.jobs.size }
    }

    val totalCost by lazy {
        stores.sumByDouble { _, sol -> sol.cost }
    }

    override fun toString(): String {
        return "Solution is ${if (isValid) "valid" else "invalid"}, cost is $totalCost"
    }

    fun printWithExtraDetails(): String {
        val builder = StringBuilder()
        for ((storeName, solution) in stores) {
            builder.append("Store ").append(storeName).append(": ")
                .append(solution.jobs.size).append(" jobs: \n")
                .append(solution.jobs.joinToString(separator = ", ", prefix = "[", postfix = "]", transform = {
                    it.id.toString()
                }))
                .append("\n")
        }
        return builder.toString()
    }
}
