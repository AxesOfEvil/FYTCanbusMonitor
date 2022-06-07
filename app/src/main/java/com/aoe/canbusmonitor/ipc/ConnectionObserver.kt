package com.aoe.canbusmonitor.ipc

import com.aoe.canbusmonitor.ipc.IRemoteToolkit

interface ConnectionObserver {
    fun onConnected(toolkit: IRemoteToolkit?)
    fun onDisconnected()
}