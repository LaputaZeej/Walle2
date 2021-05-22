package com.bugull.walle.action.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

class RequestOverlayViewModel : ViewModel() {
    private val _success: MutableLiveData<Boolean> = MutableLiveData()
    val success: LiveData<Boolean> = _success

    fun result(success: Boolean) {
        _success.value = success
    }
}

fun Fragment.requestOverlayPermission(requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${requireActivity().packageName}")
            ), requestCode
        )
    }
}

