package com.canerkorkmaz.cicek.jobdistribution.model

interface IPositionable {
    fun getLatitude(): Double
    fun getLongitude(): Double
}

fun IPositionable.distanceSqr(other: IPositionable): Double =
    Math.pow(getLatitude() - other.getLatitude(), 2.0) +
            Math.pow(getLongitude() - other.getLongitude(), 2.0)
