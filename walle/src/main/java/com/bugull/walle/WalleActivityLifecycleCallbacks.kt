package com.bugull.walle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bugull.walle.ext.logv

/**
 * Author by xpl, Date on 2021/5/6.
 */
internal class WalleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private val mFragmentLifecycleCallbacks: WalleFragmentLifecycleCallbacks =
        WalleFragmentLifecycleCallbacks()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (DEBUG) logv("$SUB_TAG::onActivityCreated ${activity.hashCode()}")

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                mFragmentLifecycleCallbacks,
                true
            )

            /*if (Configuration.isFloat) {
                checkOverlayPermission(activity)
            }*/
        }
    }

   /* private fun checkOverlayPermission(activity: FragmentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.viewModels<RequestOverlayViewModel>().apply {
                success.observe(activity) {
                    when (it) {
                        true -> {
                            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            Toast.makeText(activity, "false", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            activity.supportFragmentManager.beginTransaction()
                .add(RequestOverlayFragment.newInstance(), "checkOverlay")
                .commitAllowingStateLoss()
        }
    }*/

    override fun onActivityStarted(activity: Activity) {
        if (DEBUG) logv("$SUB_TAG::onActivityStarted ${activity.hashCode()}")
    }

    override fun onActivityResumed(activity: Activity) {
        if (DEBUG) logv("$SUB_TAG::onActivityResumed ${activity.hashCode()}")
    }

    override fun onActivityPaused(activity: Activity) {
        if (DEBUG) logv("$SUB_TAG::onActivityPaused ${activity.hashCode()}")
    }

    override fun onActivityStopped(activity: Activity) {
        if (DEBUG) logv("$SUB_TAG::onActivityStopped ${activity.hashCode()}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        if (DEBUG) logv("$SUB_TAG::onActivitySaveInstanceState ${activity.hashCode()}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (DEBUG) logv("$SUB_TAG::onActivityDestroyed ${activity.hashCode()}")

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                mFragmentLifecycleCallbacks
            )
        }
    }

    companion object {
        private const val SUB_TAG = "Activity"
        private const val DEBUG = true
    }
}