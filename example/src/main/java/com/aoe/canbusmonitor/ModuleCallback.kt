package com.aoe.canbusmonitor

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.os.RemoteException
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.aoe.fytcanbusmonitor.IModuleCallback
import java.io.OutputStream
import java.time.Instant
import java.util.concurrent.locks.ReentrantLock

class ModuleCallback(private val name: String, private val view: TextView?) : IModuleCallback.Stub() {
    @Throws(RemoteException::class)
    override fun update(
        updateCode: Int,
        intArray: IntArray?,
        floatArray: FloatArray?,
        strArray: Array<String?>?
    ) {
        logMsg("Module: $name Code: $updateCode Ints: ${intArray!![0]}")
    }

    init {
        if(false) {
            Thread {
                while (true) {
                    logMsg("Message from $name: ${System.currentTimeMillis() / 1000}")
                    Thread.sleep(5000L)
                }
            }.start()
        }
    }

    companion object {
        private lateinit var act: MainActivity
        private var view: TextView? = null
        private var ostrm: OutputStream? = null
        private var numLines = 0
        private var lines = arrayListOf<String>()
        private val lock = ReentrantLock()

        @RequiresApi(Build.VERSION_CODES.O)
        fun init(mainAct: MainActivity) {
            act = mainAct
            view = mainAct.findViewById(R.id.text_view)
            Log.i("TextView", "Count1: ${view?.lineCount}")
            //view?.movementMethod = ScrollingMovementMethod()
            for (i in 1..100) {
                lines.add("\n")
            }
            view?.text = lines.toString()
            val resolver = act.contentResolver
            val values = ContentValues()
            val fileName = "CanBox-" + Instant.now().toString()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), values)
            ostrm = uri?.let { resolver.openOutputStream(it, "wt") }
        }

        @SuppressLint("SetTextI18n")
        @Synchronized
        private fun logMsg(msg: String) {
            if (view != null) {
                act.runOnUiThread(Runnable {
                    lock.lock()
                    lines.add(msg + "\n")
                    if ((numLines == 0) and (view!!.layout != null)) {
                        val height = view!!.height
                        numLines = view!!.layout.getLineForVertical(height)
                        Log.i("TextView", "H:$height L1:$numLines")
                    } else if (numLines > 0) {
                        while (lines.count() > numLines) {
                            lines.removeFirst()
                        }
                    }
                    view!!.text = lines.joinToString("")
                    lock.unlock()
                })
            }
            if (ostrm != null) {
                ostrm!!.write((msg + "\n").toByteArray())
                ostrm!!.flush()
            }
            Log.i("UPDATE", msg)
        }
    }
}