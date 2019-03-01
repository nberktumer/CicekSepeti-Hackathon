package com.canerkorkmaz.cicek.jobdistribution.solver

import com.canerkorkmaz.cicek.jobdistribution.model.InputDataFormatted
import com.canerkorkmaz.cicek.jobdistribution.model.JobData
import com.canerkorkmaz.cicek.jobdistribution.model.Solution
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack
import com.canerkorkmaz.cicek.jobdistribution.model.StoreData
import com.canerkorkmaz.cicek.jobdistribution.model.distanceSqr
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.Variable
import java.util.UUID

/**
 * We have created the integer programming model for the given problem and using a library that can solve integer
 * programming optimisation problems in pure java,
 * this class can solve the problem for any given job/store input pairs in a reasonable time
 */
class IPSolver(data: InputDataFormatted) : SolverBase(data) {
    override fun solve(): SolutionPack {
        val model = ExpressionsBasedModel()
        val variables = data.storeList.map { store ->
            data.jobList.map { job ->
                Variable.makeBinary(nameOf(store, job))
                    .weight(store.distanceSqr(job))
            }
        }
        variables.forEach { model.addVariables(it) }
        for (j in 0 until data.jobList.size) {
            val exp = model.addExpression(UUID.randomUUID().toString())
                .lower(1.0).upper(1.0)
            for (i in 0 until data.storeList.size) {
                exp.setLinearFactor(variables[i][j], 1)
            }
        }
        for (i in 0 until data.storeList.size) {
            val exp = model.addExpression(UUID.randomUUID().toString())
                .lower(data.storeList[i].min).upper(data.storeList[i].max)
            for (j in 0 until data.jobList.size) {
                exp.setLinearFactor(variables[i][j], 1)
            }
        }
        val result = model.minimise()
        val resultVars = data.storeList.mapIndexed { i, _ ->
            data.jobList.mapIndexed { j, _ ->
                result.get(model.indexOf(variables[i][j]).toLong())
            }
        }
        return SolutionPack(
            resultVars.mapIndexed { i, lst ->
                i to lst.mapIndexed { j, res ->
                    j to (res.toInt() == 1)
                }.filter { it.second }.map { data.jobList[it.first] }.toSet()
            }.map {
                data.storeList[it.first] to it.second
            }.associateBy({ it.first.name }) {
                Solution(store = it.first, jobs = it.second)
            },
            data
        )
    }
}

private fun nameOf(store: StoreData, job: JobData): String =
    "x_${store.name}_${job.id}"
