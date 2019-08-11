package com.perso.autofeed.listeners

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.perso.autofeed.retrofit.client.BoxOperations
import com.perso.data.model.model.BoxResponse
import com.perso.data.model.model.BoxState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SwitchDrawerListener( val operations: BoxOperations , val number: Int , val drawerStateChange: DrawerStateChange ) : CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(component: CompoundButton?, isActivated: Boolean) {
        Log.e( "MRO" , "On checkedChanged " + number )
        if( isActivated ){

            operations.openTheDrawer( number ).enqueue( object : Callback<BoxResponse> {
                override fun onFailure(call: Call<BoxResponse>, t: Throwable) {
                    Log.e( "MRO" , "An error occured when openning the drawer " + number )
                }

                override fun onResponse(call: Call<BoxResponse>, response: Response<BoxResponse>) {
                    drawerOpening()
                    Log.d("MRO" , "Response : " + response.body() )
                    if( response.body()?.errorDescription != null ){
                        drawerStateChange.displayMessage( response.body()?.errorDescription?.errorMessage!! )
                    }
                }

            })

        }else{

            operations.closeTheDrawer( number ).enqueue( object : Callback<BoxResponse> {
                override fun onFailure(call: Call<BoxResponse>, t: Throwable) {
                    Log.e( "MRO" , "An error occured when closing the drawer " + number )
                }

                override fun onResponse(call: Call<BoxResponse>, response: Response<BoxResponse>) {
                    Log.d("MRO" , "Response : " + response.body())
                    drawerClosing()
                    if( response.body()?.errorDescription != null ){
                        drawerStateChange.displayMessage( response.body()?.errorDescription?.errorMessage!! )
                    }
                }

            })

        }

    }

    fun drawerClosing(  ){
        Log.d( "MRO" , "Closing the drawer " + number )
        drawerStateChange.displayMessage( "Le tiroir "+ number +"est en cours de fermeture" )
    }

    fun drawerOpening( )
    {
        Log.d( "MRO" , "Opening the drawer " + number )
        drawerStateChange.displayMessage( "Le tiroir " + number + " est en cours d'ouverture" )
    }

}