package com.aoe.canbusmonitor.ipc

import android.os.RemoteException

class RemoteModuleProxy : IRemoteModule.Stub() {
    var remoteModule: IRemoteModule? = null
    var moduleType = -1

    // com.syu.ipc.IRemoteModule
    override fun cmd(cmdCode: Int, ints: IntArray?, flts: FloatArray?, strs: Array<String?>?) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.cmd(cmdCode, ints, flts, strs)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun cmd(cmdCode: Int) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.cmd(cmdCode, null, null, null)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun cmd(cmdCode: Int, value: Int) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.cmd(cmdCode, intArrayOf(value), null, null)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    fun cmd(cmdCode: Int, value1: Int, value2: Int) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.cmd(cmdCode, intArrayOf(value1, value2), null, null)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    // com.syu.ipc.IRemoteModule
    override fun get(
        getCode: Int,
        ints: IntArray?,
        flts: FloatArray?,
        strs: Array<String?>?
    ): ModuleObject? {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                return remoteModule[getCode, ints, flts, strs]
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        return null
    }

    operator fun get(getCode: Int, value: Int): ModuleObject? {
        val remoteModule = remoteModule
        return if (remoteModule != null) {
            try {
                remoteModule[getCode, intArrayOf(value), null, null]
            } catch (e: RemoteException) {
                e.printStackTrace()
                null
            }
        } else null
    }

    fun getI(getCode: Int, valueIfNotOk: Int): Int {
        val remoteModule = remoteModule
        return if (remoteModule != null) {
            try {
                val obj = remoteModule[getCode, null, null, null]
                if (obj?.ints != null && obj.ints!!.isNotEmpty()) {
                    obj.ints!![0]
                } else valueIfNotOk
            } catch (e: RemoteException) {
                e.printStackTrace()
                valueIfNotOk
            }
        } else valueIfNotOk
    }

    fun getS(getCode: Int, value: Int): String? {
        val remoteModule = remoteModule
        return if (remoteModule != null) {
            try {
                val obj = remoteModule[getCode, intArrayOf(value), null, null]
                if (obj?.strs != null && obj.strs!!.isNotEmpty()) {
                    obj.strs!![0]
                } else null
            } catch (e: RemoteException) {
                e.printStackTrace()
                null
            }
        } else null
    }

    fun getS(getCode: Int, value1: Int, value2: Int): String? {
        val remoteModule = remoteModule
        return if (remoteModule != null) {
            try {
                val obj = remoteModule[getCode, intArrayOf(value1, value2), null, null]
                if (obj?.strs != null && obj.strs!!.isNotEmpty()) {
                    obj.strs!![0]
                } else null
            } catch (e: RemoteException) {
                e.printStackTrace()
                null
            }
        } else null
    }

    // com.syu.ipc.IRemoteModule
    override fun register(callback: IModuleCallback?, updateCode: Int, update: Int) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.register(callback, updateCode, update)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }

    // com.syu.ipc.IRemoteModule
    override fun unregister(callback: IModuleCallback?, updateCode: Int) {
        val remoteModule = remoteModule
        if (remoteModule != null) {
            try {
                remoteModule.unregister(callback, updateCode)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }
}