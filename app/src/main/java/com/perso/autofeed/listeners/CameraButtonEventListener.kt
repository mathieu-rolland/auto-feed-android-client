package com.perso.autofeed.listeners

import android.util.Log
import android.view.View
import com.perso.autofeed.retrofit.client.BoxOperations
import com.perso.autofeed.retrofit.client.RetroFitClient
import com.perso.data.model.model.BoxState
import com.perso.data.model.model.Camera
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraButtonEventListener( var cameraState : String , var operations : BoxOperations , var cameraViewChange: CameraViewChange ) : View.OnClickListener {

    override fun onClick(p0: View?) {

        if( "RUNNING".equals( cameraState ) ){
            operations.stopStreamin().enqueue( object : Callback<BoxState> {
                override fun onFailure(call: Call<BoxState>, t: Throwable) {
                    Log.e("MRO" , "An error occured on closing camera streaming.")
                }

                override fun onResponse(call: Call<BoxState>, response: Response<BoxState>) {
                    updateCameraState( response.body()?.camera!! );
                    cameraViewChange.stopTheCameraStream()
                }
            })
        }else if ( "STOPPED".equals( cameraState ) ){
            operations.startStreamin().enqueue( object : Callback<BoxState> {
                override fun onFailure(call: Call<BoxState>, t: Throwable) {
                    Log.e("MRO" , "Failed to start camera streaming");
                }

                override fun onResponse(call: Call<BoxState>, response: Response<BoxState>) {
                    updateCameraState( response.body()?.camera!! )
                    cameraViewChange.openTheCameraStream()
                }
            })
        }

    }

    fun updateCameraState ( camera: Camera )
    {
        cameraState = camera.state
        Log.d("MRO" , "Camera is now in " + cameraState )
    }

}