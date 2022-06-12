package com.aoe.fytcanbusmonitor

import android.os.*

/* loaded from: classes.dex */
interface IModuleCallback : IInterface {
    @Throws(RemoteException::class)
    fun update(updateCode: Int, intArray: IntArray?, floatArray: FloatArray?, strArray: Array<String?>?)

    /* loaded from: classes.dex */
    abstract class Stub : Binder(), IModuleCallback {
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
                TRANSACTION_update -> {
                    data.enforceInterface(DESCRIPTOR)
                    val updateCode = data.readInt()
                    val ints = data.createIntArray()
                    val flts = data.createFloatArray()
                    val strs = data.createStringArray()
                    update(updateCode, ints, flts, strs)
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
        private class Proxy internal constructor(private val mRemote: IBinder) : IModuleCallback {
            // android.os.IInterface
            override fun asBinder(): IBinder {
                return mRemote
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IModuleCallback
            override fun update(
                updateCode: Int,
                intArray: IntArray?,
                floatArray: FloatArray?,
                strArray: Array<String?>?
            ) {
                val data = Parcel.obtain()
                try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeInt(updateCode)
                    data.writeIntArray(intArray)
                    data.writeFloatArray(floatArray)
                    data.writeStringArray(strArray)
                    mRemote.transact(TRANSACTION_update, data, null, 1)
                } finally {
                    data.recycle()
                }
            }
        }

        companion object {
            //private const val DESCRIPTOR = "com.aoe.canbusmonitor.IModuleCallback"
            private const val DESCRIPTOR = "com.syu.ipc.IModuleCallback"
            const val TRANSACTION_update = 1
            fun asInterface(obj: IBinder?): IModuleCallback? {
                if (obj == null) {
                    return null
                }
                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is IModuleCallback) {
                    iin
                } else Proxy(obj)
            }
        }

        init {
            attachInterface(this, DESCRIPTOR)
        }
    }
}