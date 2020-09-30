package com.sundaydavid989.shesmakeup.data.db.entity


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductColor(
    @SerializedName("colour_name")
    val colourName: String,
    @SerializedName("hex_value")
    val hexValue: String
): Serializable