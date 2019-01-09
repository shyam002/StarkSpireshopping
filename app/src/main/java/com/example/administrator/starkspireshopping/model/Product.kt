package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName

class Product {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("date_added")
    var date_added: String? = null

    @SerializedName("variants")
    var variants: List<Variants>? = null

    @SerializedName("tax")
    var tax: Tax? = null
}
