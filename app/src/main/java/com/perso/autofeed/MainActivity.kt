package com.perso.autofeed

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.perso.autofeed.listeners.CameraButtonEventListener
import com.perso.autofeed.retrofit.client.BoxOperations
import com.perso.autofeed.retrofit.client.RetroFitClient
import com.perso.data.model.model.BoxState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val retroFitClient = RetroFitClient().client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val loader  = findViewById<ProgressBar>(R.id.loader)
        val drawer1 = findViewById<Switch>(R.id.drawer1)
        val drawer2 = findViewById<Switch>(R.id.drawer2)
        val cameraButton  = findViewById<ImageButton>(R.id.cameraButton)
        val cameraView = findViewById<WebView>(R.id.cameraView)

        //loader.setBackgroundColor( Color.WHITE )

        Log.d("MRO" , "start the process")

        var operations = retroFitClient.create( BoxOperations::class.java )

        operations.getBoxState().enqueue( object : Callback<BoxState> {
            override fun onFailure(call: Call<BoxState>, t: Throwable) {
                Log.d( "MRO" , "Error : " + t.message )
                Toast.makeText(applicationContext , t.message , Toast.LENGTH_LONG)
            }

            override fun onResponse(call: Call<BoxState>, response: Response<BoxState>) {
                //loader.visibility = View.INVISIBLE
                Log.d( "MRO" , "Response : " + response.body())
                applicationStateReady( response.body()!! )
            }

        })

    }

    fun applicationStateReady( boxState: BoxState ){
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)
        cameraButton.setOnClickListener( CameraButtonEventListener( boxState.camera.state , retroFitClient.create( BoxOperations::class.java ) ) );
        Log.d("MRO", "Application ready")
    }

}
