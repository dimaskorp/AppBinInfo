package ru.mark.appbininfo.data.model

data class BinInfo(
    var number  : Number,
    var scheme  : String,
    var type    : String,
    var brand   : String,
    var country : Country,
    var bank    : Bank
)

data class Country(
    var numeric   : String,
    var alpha2    : String,
    var name      : String,
    var emoji     : String,
    var currency  : String,
    var latitude  : Int,
    var longitude : Int
)

data class Bank(
    var name  : String,
    var url   : String,
    var phone : String,
    var city  : String
)

data class Number (
    var length : Int,
    var luhn   : Boolean
)