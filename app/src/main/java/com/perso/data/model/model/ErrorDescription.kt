package com.perso.data.model.model

import com.google.gson.annotations.SerializedName

data class ErrorDescription(

    @SerializedName("errorCode")
    val errorCode : Int,
    @SerializedName( "errorMessage" )
    val errorMessage: String

)