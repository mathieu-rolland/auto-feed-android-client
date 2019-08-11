package com.perso.autofeed.retrofit.client

import com.perso.data.model.model.BoxResponse
import com.perso.data.model.model.BoxState
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BoxOperations {

    @GET("/box/state" )
    fun getBoxState(): Call<BoxResponse>

    @GET("/drawer/{number}/open")
    fun openTheDrawer(@Path("number") number:Int ) : Call<BoxResponse>

    @GET( "/drawer/{number}/close")
    fun closeTheDrawer( @Path( "number" ) number:Int ) : Call<BoxResponse>

    @GET( "/material/security/stop/force" )
    fun forceStopAll() : Call<BoxResponse>

    @GET( "/camera/start" )
    fun startStreamin() : Call<BoxResponse>

    @GET( "/camera/stop" )
    fun stopStreamin() : Call<BoxResponse>

    @GET("/box/sound/play")
    fun playSound() : Call<String>

}