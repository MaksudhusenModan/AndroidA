package com.omi.birthday

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        // Your code to execute when Boot Completd
        val i = Intent(context, MyService::class.java)
        context.startService(i)
        Toast.makeText(context, "Booting Completed", Toast.LENGTH_LONG).show()
    }
}