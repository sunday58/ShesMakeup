package com.sundaydavid989.shesmakeup.data.db.entity


import com.google.gson.annotations.SerializedName

data class MakeupItem(
    val brand: String,
    val category: String,
    val currency: String,
    val description: String,
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String,
    val name: String,
    val price: String,
    @SerializedName("price_sign")
    val priceSign: String,
    @SerializedName("product_colors")
    val productColors: List<ProductColor>,
    @SerializedName("product_link")
    val productLink: String,
    @SerializedName("product_type")
    val productType: String,
    val rating: Double,
    @SerializedName("tag_list")
    val tagList: List<String>
)