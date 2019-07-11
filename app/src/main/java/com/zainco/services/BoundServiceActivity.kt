package com.zainco.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_bound_service.*

class BoundServiceActivity : AppCompatActivity() {

    var isBound = false
    lateinit var myBoundService: MyBoundService

    val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = true
        }

        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            val binder: MyBoundService.MyLocalBinder = iBinder as MyBoundService.MyLocalBinder
            myBoundService = binder.getService()
            isBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_service)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyBoundService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun onClickEvent(v: View) {
        val number1 = editText2.text.toString().toInt()
        val number2 = editText3.text.toString().toInt()

        if (isBound) {
            when (v.id) {
                R.id.add -> textViewResult.setText(myBoundService.add(number1, number2))
                R.id.sub -> textViewResult.setText(myBoundService.sub(number1, number2))
                R.id.mul -> textViewResult.setText(myBoundService.mul(number1, number2))
                R.id.div -> textViewResult.setText(myBoundService.div(number1, number2))
            }
        }
    }
}
