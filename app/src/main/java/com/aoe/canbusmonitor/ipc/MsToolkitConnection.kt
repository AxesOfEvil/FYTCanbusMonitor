package com.aoe.canbusmonitor.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import java.util.*


class MsToolkitConnection private constructor() : ServiceConnection {
    private var mConnecting = false
    private var mContext: Context? = null
    var remoteToolkit: IRemoteToolkit? = null
        private set
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private val mConnectionObservers: ArrayList<ConnectionObserver> = ArrayList()
    private val mRunnableConnect: Runnable = object : Runnable {
        // from class: com.syu.module.MsToolkitConnection.1
        // java.lang.Runnable
        override fun run() {
            if (remoteToolkit != null) {
                mConnecting = false
                return
            }
            val intent = Intent("com.syu.ms.toolkit")
            intent.component = ComponentName("com.syu.ms", "app.ToolkitService")
            mContext?.bindService(intent, instance, 1)
            mHandler.postDelayed(this, Random().nextInt(3000) + 1000L)
        }
    }

    companion object {
        val instance = MsToolkitConnection()
        var looper: Looper? = null

        init {
            val thread = HandlerThread("ConnectionThread")
            thread.start()
            looper = thread.looper
        }
    }

    @Synchronized
    fun connect(context: Context?) {
        connect(context, 0L)
    }

    private fun connect(context: Context?, delayMillis: Long) {
        if (!mConnecting && remoteToolkit == null && context != null) {
            mContext = context.applicationContext
            mConnecting = true
            mHandler.postDelayed(mRunnableConnect, delayMillis)
        }
    }

    @Synchronized
    fun addObserver(observer: ConnectionObserver?) {
        if (observer != null) {
            if (!mConnectionObservers.contains(observer)) {
                mConnectionObservers.add(observer)
                if (remoteToolkit != null) {
                    mHandler.post(OnServiceConnected(this, observer, null))
                }
            }
        }
    }

    @Synchronized
    fun removeObserver(observer: ConnectionObserver?) {
        if (observer != null) {
            mConnectionObservers.remove(observer)
        }
        if (remoteToolkit != null) {
            mHandler.post(OnServiceDisconnected(this, observer, null))
        }
    }

    @Synchronized
    fun clearObservers() {
        if (remoteToolkit != null) {
            val it: Iterator<ConnectionObserver> = mConnectionObservers.iterator()
            while (it.hasNext()) {
                val observer: ConnectionObserver = it.next()
                mHandler.post(OnServiceDisconnected(this, observer, null))
            }
        }
        mConnectionObservers.clear()
    }


    @Synchronized  // android.content.ServiceConnection
    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        remoteToolkit = IRemoteToolkit.Stub.asInterface(service)
        val it: Iterator<ConnectionObserver> = mConnectionObservers.iterator()
        while (it.hasNext()) {
            val observer: ConnectionObserver = it.next()
            mHandler.post(OnServiceConnected(this, observer, null))
        }
    }

    @Synchronized  // android.content.ServiceConnection
    override fun onServiceDisconnected(name: ComponentName) {
        remoteToolkit = null
        val it: Iterator<ConnectionObserver> = mConnectionObservers.iterator()
        while (it.hasNext()) {
            val observer: ConnectionObserver = it.next()
            mHandler.post(OnServiceDisconnected(this, observer, null))
        }
        connect(mContext, Random().nextInt(3000) + 1000L)
    }

    /* JADX INFO: Access modifiers changed from: private */ /* loaded from: classes.dex */
    inner class OnServiceConnected private constructor(observer: ConnectionObserver) : Runnable {
        private val observer: ConnectionObserver?

        // synthetic
        internal constructor(
            msToolkitConnection: MsToolkitConnection?,
            connectionObserver: ConnectionObserver,
            onServiceConnected: OnServiceConnected?
        ) : this(connectionObserver) {
        }

        // java.lang.Runnable
        override fun run() {
            val toolkit = remoteToolkit
            if (toolkit != null && observer != null) {
                observer.onConnected(toolkit)
            }
        }

        init {
            this.observer = observer
        }
    }

    /* loaded from: classes.dex */
    private inner class OnServiceDisconnected private constructor(observer: ConnectionObserver?) :

        Runnable {
        private val observer: ConnectionObserver?

        // synthetic
        internal constructor(
            msToolkitConnection: MsToolkitConnection?,
            connectionObserver: ConnectionObserver?,
            onServiceDisconnected: OnServiceDisconnected?
        ) : this(connectionObserver) {
        }

        // java.lang.Runnable
        override fun run() {
            if (observer != null) {
                observer.onDisconnected()
            }
        }

        init {
            this.observer = observer
        }
    }
}