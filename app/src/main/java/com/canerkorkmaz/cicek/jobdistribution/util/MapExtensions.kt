package com.canerkorkmaz.cicek.jobdistribution.util

inline fun <T1, T2> Map<T1, T2>.sum(selector: (T1, T2) -> Int): Int {
    var sum: Int = 0
    for (element in this) {
        sum += selector(element.key, element.value)
    }
    return sum
}


inline fun <T1, T2> Map<T1, T2>.sumByDouble(selector: (T1, T2) -> Double): Double {
    var sum: Double = 0.0
    for (element in this) {
        sum += selector(element.key, element.value)
    }
    return sum
}
