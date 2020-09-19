package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "product_type")
data class ProductItem(
    val brand: String?,
    val category: String?,
    val currency: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image_link: String,
    val name: String,
    val price: String?,
    val price_sign: String?,
    val product_api_url: String,
    @TypeConverters(MakeupTypeConverter::class)
    val product_colors: List<ProductColor>,
    val product_link: String,
    val product_type: String,
    val rating: Double?,
    val website_link: String
)