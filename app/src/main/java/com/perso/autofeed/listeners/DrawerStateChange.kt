package com.perso.autofeed.listeners

interface DrawerStateChange{

    fun drawerOpen()
    fun drawerClosed()
    fun displayMessage( message : String )

}