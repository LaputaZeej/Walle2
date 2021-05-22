package com.bugull.walle.action.content

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bugull.walle.R
import com.bugull.walle.WalleReal
import com.bugull.walle.action.base.BaseActionView
import com.bugull.walle.ext.defaultShadow
import com.bugull.walle.ext.screenSize

/**
 * Author by xpl, Date on 2021/5/17.
 */
class EnterActionView : BaseActionView() {
    override fun onCreateView(context: Context, root: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.action_walle, root, false).apply {
            defaultShadow()
        }
    }

    override fun onViewCreated(root: ViewGroup) {
        super.onViewCreated(root)
        root.setOnClickListener {
            WalleReal.showHome()

        }
    }

    override fun initLayoutParams(params: ViewGroup.LayoutParams) {
        super.initLayoutParams(params)
        when (params) {
            is WindowManager.LayoutParams -> {
                params.width = WindowManager.LayoutParams.WRAP_CONTENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                val screenSize = mRootView?.context?.screenSize
                params.x = screenSize?.let { it.x / 2 } ?: 0
                params.y = screenSize?.let { it.y / 2 } ?: 0
            }
        }
    }
}