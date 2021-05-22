package com.bugull.walle.action.base

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import com.bugull.walle.R
import com.bugull.walle.action.core.AbsActionView

/**
 * Author by xpl, Date on 2021/5/17.
 */
abstract class BaseActionView : AbsActionView() {

    override fun onViewCreated(root: ViewGroup) {
        super.onViewCreated(root)

    }

    fun <T : View> find(id: Int): T? = mRootView?.findViewById<T>(id)

    fun toast(@StringRes text: Int) {
        Toast.makeText(
            mRootView?.context,
            mRootView?.context?.getString(text) ?: "-",
            Toast.LENGTH_SHORT
        ).show()
    }
}