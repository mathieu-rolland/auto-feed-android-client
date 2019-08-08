package com.perso.autofeed.retrofit.client

import com.perso.data.model.model.BoxState
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BoxOperations {

    @GET("/box/state" )
    fun getBoxState(): Call<BoxState>

    @GET("/drawer/{number}/open")
    fun openTheDrawer(@Path("number") number:Int ) : Call<BoxState>

    @GET( "/drawer/{number}/close")
    fun closeTheDrawer( @Path( "number" ) number:Int ) : Call<BoxState>

    @GET( "/material/security/stop/force" )
    fun forceStopAll() : Call<BoxState>

    @GET( "/camera/start" )
    fun startStreamin() : Call<BoxState>

    @GET( "/camera/stop" )
    fun stopStreamin() : Call<BoxState>

}