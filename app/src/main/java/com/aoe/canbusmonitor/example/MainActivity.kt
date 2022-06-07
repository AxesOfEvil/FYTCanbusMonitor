package com.aoe.canbusmonitor.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aoe.canbusmonitor.R
import com.aoe.canbusmonitor.ipc.ModuleCodes.MODULE_CODE_CANBUS
import com.aoe.canbusmonitor.ipc.ModuleCodes.MODULE_CODE_CANUP
import com.aoe.canbusmonitor.ipc.ModuleCodes.MODULE_CODE_MAIN
import com.aoe.canbusmonitor.ipc.ModuleCodes.MODULE_CODE_SOUND
import com.aoe.canbusmonitor.ipc.MsToolkitConnection

class MainActivity : AppCompatActivity() {
    private val connections = arrayListOf<IPCConnection>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ModuleCallback.init(this)
        connectMain()
        connectCanbus()
        connectSound()
        connectCanUp()
        MsToolkitConnection.instance.connect(this)

    }

    private fun connectMain() {
        val callback = ModuleCallback("Main", findViewById(R.id.text_view))
        val connection = IPCConnection(MODULE_CODE_MAIN)
        for (i in 0..119) {
            connection.addCallback(callback, i)
        }
        MsToolkitConnection.instance.addObserver(connection)
    }

    private fun connectCanbus() {
        val callback = ModuleCallback("Canbus", findViewById(R.id.text_view))
        val connection = IPCConnection(MODULE_CODE_CANBUS)
        for (i in 0..1199) {
            connection.addCallback(callback, i)
        }
        MsToolkitConnection.instance.addObserver(connection)
    }

    private fun connectSound() {
        val callback = ModuleCallback("Sound", findViewById(R.id.text_view))
        val connection = IPCConnection(MODULE_CODE_SOUND)
        for (i in 0..49) {
            connection.addCallback(callback, i)
        }
        MsToolkitConnection.instance.addObserver(connection)
    }

    private fun connectCanUp() {
        val callback = ModuleCallback("CanUp", findViewById(R.id.text_view))
        val connection = IPCConnection(MODULE_CODE_CANUP)
        connection.addCallback(callback, 100)
        MsToolkitConnection.instance.addObserver(connection)
    }
}