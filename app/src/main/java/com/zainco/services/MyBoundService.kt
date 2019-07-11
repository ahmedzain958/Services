package com.zainco.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyBoundService : Service() {
    private val myLocalBinder = MyLocalBinder()

    inner class MyLocalBinder : Binder() {
        fun getService(): MyBoundService {
            return this@MyBoundService
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return myLocalBinder
    }

     fun add(val1: Int, val2: Int): Int {
        return val1 + val2
    }

     fun mul(val1: Int, val2: Int): Int {
        return val1 * val2
    }

     fun sub(val1: Int, val2: Int): Int {
        return val1 - val2
    }

     fun div(val1: Int, val2: Int): Int {
        return val1 / val2
    }
}