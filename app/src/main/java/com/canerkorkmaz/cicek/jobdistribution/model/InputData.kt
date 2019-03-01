package com.canerkorkmaz.cicek.jobdistribution.model

data class InputData(
    val orders: List<JobData>,
    val shops: List<StoreData>
) {
    fun format(): InputDataFormatted =
        InputDataFormatted(
            jobs = orders.associateBy { it.id },
            stores = shops.associateBy { it.name },
            jobList = orders,
            storeList = shops
        )
}

data class InputDataFormatted(
    val jobs: Map<Int, JobData>,
    val jobList: List<JobData>,
    val stores: Map<String, StoreData>,
    val storeList: List<StoreData>
)
