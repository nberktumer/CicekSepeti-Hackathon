package com.canerkorkmaz.cicek.jobdistribution.model

data class StoreData(
    val name: String,
    val lat: Double,
    val long: Double,
    val min: Int,
    val max: Int
) : IPositionable {
    override fun getLatitude(): Double = lat

    override fun getLongitude(): Double = long

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StoreData) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }


}
