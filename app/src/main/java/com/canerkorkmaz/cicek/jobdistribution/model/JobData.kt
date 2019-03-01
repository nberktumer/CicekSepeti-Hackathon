package com.canerkorkmaz.cicek.jobdistribution.model

data class JobData(
    val id: Int,
    val lat: Double,
    val long: Double
) : IPositionable {
    override fun getLatitude(): Double = lat

    override fun getLongitude(): Double = long

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JobData) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
