package com.perso.autofeed.listeners

import com.perso.data.model.model.Camera

interface CameraViewChange {

    fun openTheCameraStream()
    fun stopTheCameraStream();

}