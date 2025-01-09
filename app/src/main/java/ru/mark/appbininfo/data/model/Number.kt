package com.example.example

import com.google.gson.annotations.SerializedName


data class Number (

  @SerializedName("length" ) var length : Int?     = null,
  @SerializedName("luhn"   ) var luhn   : Boolean? = null

)