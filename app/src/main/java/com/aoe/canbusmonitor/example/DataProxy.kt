package com.aoe.canbusmonitor.example

import com.aoe.canbusmonitor.ipc.RemoteModuleProxy

object DataProxy {
    val canbusProxy = RemoteModuleProxy()
    val canUpProxy = RemoteModuleProxy()
    val mainProxy = RemoteModuleProxy()
}