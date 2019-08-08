package com.perso.data.model.model

import com.google.gson.annotations.SerializedName

data class BoxState(
    @SerializedName("drawer1")
    val drawer1: Drawer,
    @SerializedName( "drawer2" )
    val drawer2: Drawer,
    @SerializedName("led")
    val led:Led,
    @SerializedName( "camera" )
    val camera: Camera
)