package com.bugull.walle.action.permission

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bugull.walle.ext.canOverlay
import com.bugull.walle.ext.gotoOverlay


class RequestOverlayActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!canOverlay()) {
            gotoOverlay()
            return
        }
    }

    companion object{
        fun Context.checkOverlay(){
            startActivity(Intent(this,RequestOverlayActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

}