package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName

class Variants {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("color")
    var color: String? = null

    @SerializedName("size")
    var size: String? = null

    @SerializedName("price")
    var price: String? = null
}
