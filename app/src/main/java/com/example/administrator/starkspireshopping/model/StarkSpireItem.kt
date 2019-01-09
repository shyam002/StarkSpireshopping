package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName


class StarkSpireItem {

    @SerializedName("categories")
    public var itemCategory: List<ItemCategory>? = null

    @SerializedName("rankings")
    public var ranking: List<Ranking>? = null

}
