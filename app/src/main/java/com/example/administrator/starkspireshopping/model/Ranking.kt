package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName

class Ranking {

    @SerializedName("ranking")
    var ranking: String? = null

    @SerializedName("products")
    var rankingProducts: List<RankingProduct>? = null
}
