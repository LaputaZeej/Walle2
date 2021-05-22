package com.bugull.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import com.bugull.walle.OnAppChangedListener
import com.bugull.walle.Status
import com.bugull.walle.Walle
import com.bugull.walle.ext.gotoOverlay

class MainActivity : AppCompatActivity(), OnAppChangedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn("显示菜单") {
            Walle.showHome()
        }

        addBtn("关闭") {
            Walle.hideHome()
        }

        addBtn("权限") {
            gotoOverlay()
        }

        addBtn("logo") {
            Walle.show()
        }

        addBtn("隐藏logo") {
            Walle.hide()
        }

        addBtn("TEST") {
            repeat(10) {
                println("tt->$it")
            }
        }

        Walle.show()

    }

    private fun addBtn(text: String, block: () -> Unit) {
        findViewById<LinearLayout>(R.id.ll_content).addView(Button(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setText(text)
            setOnClickListener {
                block()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onDestroy() {
        super.onDestroy()
        Walle.close()
    }

    override fun onChanged(p1: Status) {
        Log.i("walle", "xxxxxxxxx3 $p1")
    }


}