package com.canerkorkmaz.cicek.jobdistribution

import com.canerkorkmaz.cicek.jobdistribution.model.InputData
import com.canerkorkmaz.cicek.jobdistribution.model.SolutionPack
import com.canerkorkmaz.cicek.jobdistribution.solver.IPSolver
import kotlin.system.measureTimeMillis

fun solve(data: InputData): SolutionPack {
    println("========================================================")
    println("==  Kotlin solver for cicek job distribution problem  ==")
    println("========================================================\n\n")

    val dataFormatted = data.format()

    val solver = IPSolver(dataFormatted)

    println("Using solver ${solver.name}")

    val duration = measureTimeMillis {
        solver.calculateSolution()
    }

    println("Solved in $duration millis")
    println("${solver.solution}\n\n")


    println("===============")
    println("==  Results  ==")
    println("===============")

    println(solver.solution.printWithExtraDetails())

    return solver.solution
}
