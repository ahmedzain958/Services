package com.zainco.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_inten_service.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.checkBox
import kotlinx.android.synthetic.main.activity_main.start
import kotlinx.android.synthetic.main.activity_main.stop
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MyService::class.java)
        intent.putExtra("service", 10)
        start.setOnClickListener {
            startService(intent)
        }
        checkBox.setOnClickListener {
            startService(intent)
        }
        stop.setOnClickListener {
            stopService(intent)
        }

    }

    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            stop.text = intent?.getStringExtra("result")
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("action.service.activity")
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    class MyService : Service() {
        override fun onCreate() {
            super.onCreate()
            Log.i("MainActivity", "onCreate " + Thread.currentThread().name)
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            val sleepTime = intent?.getIntExtra("service", 1)

            MyAsyncTask().execute(sleepTime)
            Log.i("MainActivity", "onStartCommand " + Thread.currentThread().name)
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(intent: Intent?): IBinder? {
            Log.i("MainActivity", "onBind " + Thread.currentThread().name)
            return null
        }

        override fun onDestroy() {
            Log.i("MainActivity", "onDestroy " + Thread.currentThread().name)
            super.onDestroy()
        }

        inner class MyAsyncTask : AsyncTask<Int, String, String>() {
            override fun onPreExecute() {
                Log.i("MainActivity", "onPreExecute " + Thread.currentThread().name)
                super.onPreExecute()
            }

            override fun doInBackground(vararg params: Int?): String {
                Log.i("MainActivity", "doInBackground " + Thread.currentThread().name)
                val sleepTime: Int? = params[0]
                var counter = 1
                while (counter < sleepTime!!) {
                    publishProgress("counter now is $counter ")
                    try {
                        Thread.sleep(1000)
                    } catch (e: Exception) {
                    }
                    counter++
                }
                return "counter $counter "
            }

            override fun onProgressUpdate(vararg values: String?) {
                Log.i(
                    "MainActivity",
                    "onProgressUpdate " + "counter now is ${values[0]} " + Thread.currentThread().name
                )
                super.onProgressUpdate(*values)
            }

            override fun onPostExecute(result: String) {
                stopSelf()
                Log.i("MainActivity", "onPostExecute " + Thread.currentThread().name)
                val intent = Intent("action.service.activity")
                intent.putExtra("result", result)
                sendBroadcast(intent)
                super.onPostExecute(result)
            }
        }
    }


}
