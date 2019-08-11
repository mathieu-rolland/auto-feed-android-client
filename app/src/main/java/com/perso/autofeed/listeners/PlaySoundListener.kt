package com.perso.autofeed.listeners

import android.util.Log
import android.view.View
import com.perso.autofeed.retrofit.client.BoxOperations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaySoundListener( var operations: BoxOperations , val errorDisplay: ErrorDisplay ) : View.OnClickListener
{
    override fun onClick(p0: View?) {
        Log.d("MRO" , "Play sound")
        operations.playSound().enqueue(object :  Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                errorDisplay.displayError( "Erreur lors de la lecture du son." )
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                errorDisplay.displayError("Son joué avec succès.")
            }

        } )
    }

}