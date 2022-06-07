package com.aoe.canbusmonitor.ipc

import android.os.*


interface IRemoteToolkit : IInterface {
    @Throws(RemoteException::class)
    fun getRemoteModule(i: Int): IRemoteModule?

    /* loaded from: classes.dex */
    abstract class Stub : Binder(), IRemoteToolkit {
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
                TRANSACTION_getRemoteModule   -> {
                    data.enforceInterface(DESCRIPTOR)
                    val moduleCode = data.readInt()
                    reply!!.writeNoException()
                    reply.writeStrongBinder(getRemoteModule(moduleCode)?.asBinder())
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
        private class Proxy internal constructor(private val mRemote: IBinder) : IRemoteToolkit {
            // android.os.IInterface
            override fun asBinder(): IBinder {
                return mRemote
            }

            @Throws(RemoteException::class)  // com.syu.ipc.IRemoteToolkit
            override fun getRemoteModule(moduleCode: Int): IRemoteModule {
                val data = Parcel.obtain()
                val reply = Parcel.obtain()
                return try {
                    data.writeInterfaceToken(DESCRIPTOR)
                    data.writeInt(moduleCode)
                    mRemote.transact(1, data, reply, 0)
                    reply.readException()
                    IRemoteModule.Stub.asInterface(reply.readStrongBinder())!!
                } finally {
                    reply.recycle()
                    data.recycle()
                }
            }
        }

        companion object {
            //private const val DESCRIPTOR = "com.aoe.canbusmonitor.ipc.IRemoteToolkit"
            private const val DESCRIPTOR = "com.syu.ipc.IRemoteToolkit"
            const val TRANSACTION_getRemoteModule = 1
            fun asInterface(obj: IBinder?): IRemoteToolkit? {
                if (obj == null) {
                    return null
                }
                val iin = obj.queryLocalInterface(DESCRIPTOR)
                return if (iin != null && iin is IRemoteToolkit) {
                    iin
                } else Proxy(obj)
            }
        }

        init {
            attachInterface(this, DESCRIPTOR)
        }
    }
}