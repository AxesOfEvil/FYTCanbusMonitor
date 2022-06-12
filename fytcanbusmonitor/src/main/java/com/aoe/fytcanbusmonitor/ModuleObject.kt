package com.aoe.fytcanbusmonitor

class ModuleObject {
    lateinit var flts: FloatArray
    var ints: IntArray? = null
    var strs: Array<String>? = null

    companion object {
        fun checkInts(obj: ModuleObject?, min: Int): Boolean {
            return if (obj == null || obj.ints == null || obj.ints!!.size < min) false else true
        }

        operator fun get(obj: ModuleObject?, valueIfNotOk: Int): Int {
            return if (obj != null && obj.ints != null && obj.ints!!.size >= 1) {
                obj.ints!![0]
            } else valueIfNotOk
        }

        operator fun get(obj: ModuleObject?, valueIfNotOk: String): String {
            return if (obj != null && obj.strs != null && obj.strs!!.size >= 1) {
                obj.strs!![0]
            } else valueIfNotOk
        }

        operator fun get(proxy: RemoteModuleProxy, getCode: Int, valueIfNotOk: Int): Int {
            val obj: ModuleObject? = proxy.get(getCode, null, null, null)
            return if (obj != null && obj.ints != null && obj.ints!!.size >= 1) {
                obj.ints!![0]
            } else valueIfNotOk
        }
    }
}