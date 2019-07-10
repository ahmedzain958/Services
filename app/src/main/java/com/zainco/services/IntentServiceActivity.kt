package com.zainco.services

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inten_service.*

class IntentServiceActivity : AppCompatActivity() {
    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inten_service)
        val intent = Intent(this, MyIntentService::class.java)
        intent.putExtra("service", 10)
        intent.putExtra("receiver", MyResultReceiver())
        button.setOnClickListener {
            startService(intent)
        }
    }

     private inner class MyResultReceiver : ResultReceiver(null) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            if (resultData != null && resultCode == 18) {
                val result = resultData.getString("resultIntent")
                // to run data in the main thread
                handler.post {
                    textView.text = result
                }
            }
        }
    }
}
