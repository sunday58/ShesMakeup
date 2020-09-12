package com.sundaydavid989.shesmakeup.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "make_up")
data class MakeupItem(
    val brand: String?,
    val category: String?,
    val currency: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String?,
    val name: String,
    val price: String?,
    @SerializedName("price_sign")
    val priceSign: String?,
    @TypeConverters(MakeupTypeConverter::class)
    @SerializedName("product_colors")
    val productColors: List<ProductColor>,
    @SerializedName("product_link")
    val productLink: String?,
    @SerializedName("product_type")
    val productType: String?,
    val rating: Double?
): Serializable