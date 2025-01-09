package ru.mark.appbininfo.data.model

data class BinInfo(
    val country: Country,
    val bank: Bank,
    val type: String
)

data class Country(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

data class Bank(
    val name: String,
    val url: String,
    val phone: String,
    val city: String
)