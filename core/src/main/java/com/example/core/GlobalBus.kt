package com.example.core

import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

class GlobalBus private constructor(){
    companion object {
        private var instance: Bus? = null
        @Synchronized
        fun getBus(): Bus {
            if (instance == null) {
                instance = Bus(ThreadEnforcer.ANY)
            }
            return instance!!
        }
    }
}