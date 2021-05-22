package com.bugull.walle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bugull.walle.ext.logv

/**
 * Author by xpl, Date on 2021/5/6.
 */
internal class WalleFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentPreAttached(fm, f, context)
        logv(
            Configuration.TAG,
            "WalleFragmentLifecycleCallbacks::onFragmentPreAttached ${f.hashCode()}"
        )
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fm, f, context)
        logv(
            Configuration.TAG,
            "WalleFragmentLifecycleCallbacks::onFragmentAttached ${f.hashCode()}"
        )
    }

    override fun onFragmentPreCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentPreCreated(fm, f, savedInstanceState)
        if (DEBUG) logv("$SUB_TAG::onFragmentPreCreated ${f.hashCode()}")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)
        if (DEBUG) logv("$SUB_TAG::onFragmentCreated ${f.hashCode()}")
    }

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)
        if (DEBUG) logv("$SUB_TAG::onFragmentActivityCreated ${f.hashCode()}")
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        if (DEBUG) logv("$SUB_TAG::onFragmentViewCreated ${f.hashCode()}")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentStarted ${f.hashCode()}")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentResumed ${f.hashCode()}")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentPaused ${f.hashCode()}")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentStopped ${f.hashCode()}")
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        super.onFragmentSaveInstanceState(fm, f, outState)
        if (DEBUG) logv("$SUB_TAG::onFragmentSaveInstanceState ${f.hashCode()}")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentViewDestroyed ${f.hashCode()}")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentDestroyed ${f.hashCode()}")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fm, f)
        if (DEBUG) logv("$SUB_TAG::onFragmentDetached ${f.hashCode()}")
    }

    companion object {
        private const val SUB_TAG = "Fragment"
        private const val DEBUG = true
    }
}