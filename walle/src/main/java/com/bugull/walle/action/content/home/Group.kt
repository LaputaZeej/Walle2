package com.bugull.walle.action.content.home

import com.bugull.walle.R

/**
 * Author by xpl, Date on 2021/5/6.
 */
sealed class Group(val name: String, val icon: Int) {
    object Basic : Group("基础功能", R.drawable.ic_enter_logo)
    object Performance : Group("性能监测", R.drawable.ic_enter_logo)
    object Util : Group("工具", R.drawable.ic_enter_logo)
    object Logger : Group("日志", R.drawable.ic_enter_logo)
    object Custom : Group("自定义", R.drawable.ic_enter_logo)
}