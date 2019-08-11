package com.perso.autofeed.listeners

import android.util.Log
import android.view.View
import com.perso.autofeed.retrofit.client.BoxOperations
import com.perso.autofeed.retrofit.client.RetroFitClient
import com.perso.data.model.model.BoxResponse
import com.perso.data.model.model.BoxState
import com.perso.data.model.model.Camera
import com.perso.data.model.model.ErrorDescription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraButtonEventListener(var cameraState : String, var operations : BoxOperations, var cameraViewChange: CameraViewChange, var errorDisplay: ErrorDisplay ) : View.OnClickListener {

    override fun onClick(p0: View?) {

        if( "RUNNING".equals( cameraState ) ){
            operations.stopStreamin().enqueue( object : Callback<BoxResponse> {
                override fun onFailure(call: Call<BoxResponse>, t: Throwable) {
                    Log.e("MRO" , "An error occured on closing camera streaming.")
                }

                override fun onResponse(call: Call<BoxResponse>, response: Response<BoxResponse>) {
                    updateCameraState( response.body()?.boxState?.camera!! );
                    cameraViewChange.stopTheCameraStream()
                }
            })
        }else if ( "STOPPED".equals( cameraState ) ){
            operations.startStreamin().enqueue( object : Callback<BoxResponse> {
                override fun onFailure(call: Call<BoxResponse>, t: Throwable) {
                    Log.e("MRO" , "Failed to start camera streaming");
                }

                override fun onResponse(call: Call<BoxResponse>, response: Response<BoxResponse>) {
                    updateCameraState( response.body()?.boxState?.camera!! )
                    cameraViewChange.openTheCameraStream()
                    if( response.body()?.errorDescription != null  ) displayError(response.body()?.errorDescription!!)
                }
            })
        }

    }

    fun displayError( errorDescription: ErrorDescription ) {
        errorDisplay.displayError(errorDescription.errorMessage)
    }


    fun updateCameraState ( camera: Camera )
    {
        cameraState = camera.state
        Log.d("MRO" , "Camera is now in " + cameraState )
    }

}