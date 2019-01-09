package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName

class ItemCategory {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    public var name: String? = null

    @SerializedName("products")
    var products: List<Product>? = null

    @SerializedName("child_categories")
    var childCategories: List<Int>? = null

}
