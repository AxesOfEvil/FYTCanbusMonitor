package com.aoe.fytcanbusmonitor

import android.os.*


interface IRemoteModule : IInterface {
    @Throws(RemoteException::class)
    fun cmd(i: Int, iArr: IntArray?, fArr: FloatArray?, strArr: Array<String?>?)

    @Throws(RemoteException::class)
    operator fun get(
        i: Int,
        iArr: IntArray?,
        fArr: FloatArray?,
        strArr: Array<String?>?
    ): ModuleObject?

    @Throws(RemoteException::class)
    fun register(iModuleCallback: IModuleCallback?, i: Int, i2: Int)

    @Throws(RemoteException::class)
    fun unregister(iModuleCallback: IModuleCallback?, i: Int)

    /* loaded from: classes.dex */
    abstract class Stub : Binder(), IRemoteModule {
        // android.os.IInterface
        override fun asBinder(): IBinder {
            return this
        }

        @Throws(RemoteException::class)  // android.os.Binder
        public override fun onTransact(
            code: Int,
            data: Parcel,
            reply: Parcel?,
            flags: Int
        ): Boolean {
            return when (code) {
                TRANSACTION_cmd -> {
                    data.enforceInterface(DESCRIPTOR)
                    val cmdCode = data.readInt()
                    val ints = data.createIntArray()
                    val flts = data.createFloatArray()
                    val strs = data.createStringArray()
                    cmd(cmdCode, ints, flts, strs)
                    true
                }
                TRANSACTION_get -> {
                    data.enforceInterface(DESCRIPTOR)
                    val getCode = data.readInt()
                    val ints2 = data.createIntArray()
                    val flts2 = data.createFloatArray()
                    val strs2 = data.createStringArray()
                    val result: ModuleObject? = get(getCode, ints2, flts2, strs2)
                    reply!!.writeNoException()
                    if (result != null) {
                        reply.writeInt(1)
                        reply.writeIntArray(result.ints)
                        reply.writeFloatArray(result.flts)
                        reply.writeStringArray(result.strs)
                        return true
                    }
                    reply.writeInt(0)
                    true
                }
                TRANSACTION_register -> {
                    data.enforceInterface(DESCRIPTOR)
                    val callback: IModuleCallback? =
                        IModuleCallback.Stub.asInterface(data.readStrongBinder())
                    val updateCode = data.readInt()
                    val update = data.readInt()
                    register(callback, updateCode, update)
                    true
                }
                TRANSACTION_unregister -> {
                    data.enforceInterface(DESCRIPTOR)
                    val callback2: IModuleCallback? =
                        IModuleCallback.Stub.asInterface(data.readStrongBinder())
                    val updateCode2 = data.readInt()
                    unregister(callback2, updateCode2)
                    true
                }
                INTERFACE_TRANSACTION -> {
                    reply!!.writeString(DESCRIPTOR)
                    true
                }
                else -> super.onTransact(code, data, reply, flags)
            }
        }

        /* loaded from: classes.dex */
        private class Proxy internal constructor(private val mRemote: IBinder) : IRemoteModule {
            // android.os.IInterface
            override fun asBinder(): IBinder {
                return mRemote
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IRemoteModule
            override fun cmd(
                cmdCode: Int,
                ints: IntArray?,
                flts: FloatArray?,
                strs: Array<String?>?
            ) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeInt(cmdCode)
                    data.writeIntArray(ints)
                    data.writeFloatArray(flts)
                    data.writeStringArray(strs)
                    mRemote.transact(TRANSACTION_cmd, data, reply, 1)
                    reply.readException()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IRemoteModule
            override fun get(
                getCode: Int,
                ints: IntArray?,
                flts: FloatArray?,
                strs: Array<String?>?
            ): ModuleObject? {
                val result: ModuleObject?
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                return try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeInt(getCode)
                    data.writeIntArray(ints)
                    data.writeFloatArray(flts)
                    data.writeStringArray(strs)
                    mRemote.transact(TRANSACTION_get, data, reply, 0)
                    reply.readException()
                    if (reply.readInt() != 0) {
                        result = ModuleObject()
                        result.ints = reply.createIntArray()
                        result.flts = reply.createFloatArray()!!
                        result.strs = reply.createStringArray()
                    } else {
                        result = null
                    }
                    result
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IRemoteModule
            override fun register(callback: IModuleCallback?, updateCode: Int, update: Int) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeStrongBinder(if (callback != null) callback.asBinder() else null)
                    data.writeInt(updateCode)
                    data.writeInt(update)
                    mRemote.transact(TRANSACTION_register, data, reply, 1)
                    reply.readException()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IRemoteModule
            override fun unregister(callback: IModuleCallback?, updateCode: Int) {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeStrongBinder(if (callback != null) callback.asBinder() else null)
                    data.writeInt(updateCode)
                    mRemote.transact(TRANSACTION_unregister, data, reply, 1)
                    reply.readException()
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }
        }

        companion object {
            //private const val DESCRIPTOR = "com.aoe.fytcanbusmonitor.IRemoteModule"
            private const val DESCRIPTOR = "com.syu.ipc.IRemoteModule"
            const val TRANSACTION_cmd = 1
            const val TRANSACTION_get = 2
            const val TRANSACTION_register = 3
            const val TRANSACTION_unregister = 4
            fun asInterface(obj: IBinder?): IRemoteModule? {
                if (obj == null) {
                    return null
                }
                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is IRemoteModule) {
                    iin
                } else Proxy(obj)
            }
        }

        init {
            attachInterface(this, DESCRIPTOR)
        }
    }
}