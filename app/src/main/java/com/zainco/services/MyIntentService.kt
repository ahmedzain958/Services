package com.zainco.services

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import java.lang.Exception

class MyIntentService : IntentService("MyWorkerThread") {

    override fun onCreate() {
        super.onCreate()
        Log.i("MyIntentService", "onCreate " + Thread.currentThread().name)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("MyIntentService", "onHandleIntent " + Thread.currentThread().name)
        val sleepTime = intent?.getIntExtra("service", 1)
        val receiver = intent?.getParcelableExtra("receiver") as ResultReceiver
        var counter = 1
        while (counter < sleepTime!!) {
            Log.i("MyIntentService", "counter now is $counter " + Thread.currentThread().name)
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {
            }
            counter++
        }
        val bundle = Bundle()
        bundle.putString("resultIntent", "current counter $counter ")
        receiver.send(18, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyIntentService", "onDestroy " + Thread.currentThread().name)
    }
}