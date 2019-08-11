package com.perso.autofeed

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.perso.autofeed.listeners.*
import com.perso.autofeed.retrofit.client.BoxOperations
import com.perso.autofeed.retrofit.client.RetroFitClient
import com.perso.data.model.model.BoxResponse
import com.perso.data.model.model.BoxState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast.makeText as makeText1

class MainActivity : AppCompatActivity(),
                     CameraViewChange,
                     DrawerStateChange,
                     ErrorDisplay {

    private val retroFitClient = RetroFitClient().client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val loader  = findViewById<ProgressBar>(R.id.loader)
        var drawer1 = findViewById<Switch>(R.id.drawer1)
        val drawer2 = findViewById<Switch>(R.id.drawer2)

        Log.d("MRO" , "start the process")

        drawer1.setOnCheckedChangeListener( SwitchDrawerListener( retroFitClient.create( BoxOperations::class.java ) , 1 ,this ) )
        drawer2.setOnCheckedChangeListener( SwitchDrawerListener( retroFitClient.create( BoxOperations::class.java ) , 2 ,this ) )


        val soundButton = findViewById<ImageView>(R.id.play_sound_button)
        soundButton.setOnClickListener( PlaySoundListener( retroFitClient.create( BoxOperations::class.java ) , this)  )

        var operations = retroFitClient.create( BoxOperations::class.java )

        operations.getBoxState().enqueue( object : Callback<BoxResponse> {
            override fun onFailure(call: Call<BoxResponse>, t: Throwable) {
                Log.d( "MRO" , "Error : " + t.message )
                Toast.makeText(applicationContext , t.message , Toast.LENGTH_LONG).show()
                setLinkStatusToKo()
            }

            override fun onResponse(call: Call<BoxResponse>, response: Response<BoxResponse>) {
                //loader.visibility = View.INVISIBLE
                Log.d( "MRO" , "Response : " + response.body())
                applicationStateReady( response.body()?.boxState!! )
                setLinkStatusToOk()
            }

        })

    }

    fun applicationStateReady( boxState: BoxState ){
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)
        cameraButton.setOnClickListener( CameraButtonEventListener( boxState.camera.state , retroFitClient.create( BoxOperations::class.java ) , this , this) );
        Log.d("MRO", "Application ready")
        if( "RUNNING".equals( boxState.camera.state ) ){
            openTheCameraStream()
        }else{
            stopTheCameraStream()
        }
    }

    fun setLinkStatusToOk(){
        var linkStatusText = findViewById<TextView>( R.id.link_status_txt )
        var linkStatusImg = findViewById<ImageView>( R.id.link_status_img )

        linkStatusText.text = "Connexion OK"
        linkStatusText.setTextColor( Color.GREEN )

        linkStatusImg.setImageDrawable( ContextCompat.getDrawable( applicationContext , R.mipmap.backoffice_connected ) )

    }

    fun setLinkStatusToKo(){
        var linkStatusText = findViewById<TextView>( R.id.link_status_txt )
        var linkStatusImg = findViewById<ImageView>( R.id.link_status_img )

        linkStatusText.text = "Connexion KO"
        linkStatusText.setTextColor( Color.RED )

        linkStatusImg.setImageDrawable( ContextCompat.getDrawable( applicationContext , R.mipmap.backoffice_disconnected ) )

    }

    override fun openTheCameraStream() {
        val cameraView = findViewById<WebView>(R.id.cameraView)
        cameraView.visibility = View.VISIBLE
        cameraView.loadUrl("https://consomac.fr/")
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)
        cameraButton.setImageDrawable( ContextCompat.getDrawable( applicationContext , R.mipmap.camera_running ) )
    }

    override fun stopTheCameraStream() {
        val cameraView = findViewById<WebView>(R.id.cameraView)
        cameraView.visibility = View.INVISIBLE
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)
        cameraButton.setImageDrawable( ContextCompat.getDrawable( applicationContext , R.mipmap.camera_stop ) )
    }

    override fun drawerOpen() {
        Toast.makeText(this, "Le tiroir est fermé!" , Toast.LENGTH_LONG ).show()
    }

    override fun drawerClosed() {
        Toast.makeText(this, "Le tiroir est fermé!" , Toast.LENGTH_LONG ).show()
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this, message , Toast.LENGTH_LONG ).show()
    }

    override fun displayError(message: String) {
        Toast.makeText(this, message , Toast.LENGTH_LONG ).show()
    }

}
