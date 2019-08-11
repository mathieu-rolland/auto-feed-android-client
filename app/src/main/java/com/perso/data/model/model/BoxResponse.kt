package com.perso.data.model.model

import com.google.gson.annotations.SerializedName

data class BoxResponse(
    @SerializedName("boxState")
    val boxState: BoxState,
    @SerializedName("errorDescription")
    val errorDescription: ErrorDescription
)