package com.aoe.fytcanbusmonitor

interface ConnectionObserver {
    fun onConnected(toolkit: IRemoteToolkit?)
    fun onDisconnected()
}