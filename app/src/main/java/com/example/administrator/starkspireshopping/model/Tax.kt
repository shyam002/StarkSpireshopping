package com.example.administrator.starkspire.model

import com.google.gson.annotations.SerializedName

class Tax {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("value")
    var value: String? = null

}
