package com.aoe.canbusmonitor.example

import android.os.RemoteException
import com.aoe.canbusmonitor.example.ModuleCallback
import com.aoe.canbusmonitor.ipc.ConnectionObserver
import com.aoe.canbusmonitor.ipc.IRemoteToolkit
import com.aoe.canbusmonitor.ipc.RemoteModuleProxy

class IPCConnection(private val moduleId: Int) : ConnectionObserver {
    private val callbacks = arrayListOf<Pair<ModuleCallback, Int>>()
    val remoteProxy = RemoteModuleProxy()

    override fun onConnected(toolkit: IRemoteToolkit?) {
        try {
            remoteProxy.remoteModule = toolkit!!.getRemoteModule(moduleId)

        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        for (callback in callbacks) {
            remoteProxy.register(callback.first, callback.second, 1)
        }
    }

     fun addCallback(callback: ModuleCallback, id: Int) {
         callbacks.add(Pair(callback, id))
     }
    // com.syu.module.ConnectionObserver
    override fun onDisconnected() {
        for (callback in callbacks) {
            remoteProxy.unregister(callback.first, callback.second)
        }
        remoteProxy.remoteModule = null
    }
}