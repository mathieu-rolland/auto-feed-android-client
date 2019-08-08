package com.perso.data.model.model

import com.google.gson.annotations.SerializedName

data class Drawer (
    @SerializedName("name")
    val name: String ? = null,
    @SerializedName("state")
    val state: String ? = null
)