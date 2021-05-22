package com.bugull.walle.ext

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.util.regex.Pattern

/**
 * Author by xpl, Date on 2021/5/7.
 */
fun Context.gotoOverlay() {
    startActivity(
        Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
    )
}

fun Context.canOverlay() = Settings.canDrawOverlays(this)
val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

// 不包含statusBar
val Context.screenSizeNoStatusBar: Point
    get() = Point().also {
        mWindowManager.defaultDisplay.getSize(it)
    }

val Context.screenSize: Point
    get() = Point().also {
        mWindowManager.defaultDisplay.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) getRealSize(it) else getSize(
                it
            )
        }
    }

val Context.longSideLength: Int
    get() = if (isPortrait) screenSizeNoStatusBar.y else screenSizeNoStatusBar.x

val Context.shortSideLength: Int
    get() = if (isPortrait) screenSizeNoStatusBar.x else screenSizeNoStatusBar.y


inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModels(factory: ViewModelProvider.Factory? = null) =
    factory?.let {
        ViewModelProvider(this, it).get(T::class.java)
    } ?: ViewModelProvider(this).get(T::class.java)


fun dp2px(dp: Float): Int = (Resources.getSystem().displayMetrics.density * dp + 0.5f).toInt()
fun px2dp(px: Float): Int = (px / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
fun sp2px(sp: Float): Int = (Resources.getSystem().displayMetrics.scaledDensity * sp + 0.5f).toInt()
fun px2sp(px: Float): Int = (px / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

val Context.mWindowManager: WindowManager
    get() = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager


fun setTextStyle(text: String?, tag: String?, tv: TextView) {
    if (text == null || tag == null || text.isBlank()) {
        tv.text = if (TextUtils.isEmpty(text)) "-" else text
    } else {
        tv.text = textStyle3(text, tag.trim(), true) {
//            arrayOf(ForegroundColorSpan(Color.parseColor("#1F9AD6")))
            arrayOf(BackgroundColorSpan(Color.parseColor("#1F9AD6")))
        }
    }
}

/**
 * 一个字符串内指定的字符串设置变色加粗下滑线等效果
 * @param text 所有文字
 * @param tag 变效果文字
 * @param case 是否忽略大小写 true:忽略；false:不忽略
 * @param spans 返回各种效果array的函数
 * @return SpannableStringBuilder
 */
fun textStyle3(text: String, tag: String, case: Boolean = false, spans: () -> Array<Any>): SpannableStringBuilder = SpannableStringBuilder(text).apply {
    val pattern = if (case) Pattern.compile(tag, Pattern.CASE_INSENSITIVE) else Pattern.compile(tag)
    val matcher = pattern.matcher(this)
    while (matcher.find()) {
        val start = matcher.start()
        val end = matcher.end()
        spans().forEach {
            setSpan(it, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }
    }
}
