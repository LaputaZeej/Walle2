package com.bugull.walle

import com.bugull.walle.action.content.basic.ClearCacheAction
import com.bugull.walle.action.content.basic.DebugAction
import com.bugull.walle.action.content.basic.LocalAction
import com.bugull.walle.action.content.basic.app.AppInfoAction
import com.bugull.walle.action.content.home.Action
import com.bugull.walle.action.content.home.Group
import com.bugull.walle.action.content.logger.LoggerAction
import com.bugull.walle.action.content.basic.phone.PhoneInfoAction

/**
 * Author by xpl, Date on 2021/5/6.
 */
object Configuration {
    internal const val TAG = "_Walle_"

    @JvmStatic
    var overlayType: OverlayType = OverlayType.Overlay

    @JvmStatic
    internal val defaultGroupList: List<Group> = listOf(
        Group.Basic, Group.Logger, Group.Performance, Group.Util, Group.Custom
    )

    @JvmStatic
    internal val defaultActions: Map<Group, List<Action>> = mapOf(
        Group.Basic to listOf(
            PhoneInfoAction(),
            AppInfoAction(),
            LocalAction(),
            ClearCacheAction(),
            DebugAction()
        ),
        Group.Logger to listOf(LoggerAction())
    )

    @JvmStatic
    val customGroupList: MutableList<Group> = mutableListOf()

    @JvmStatic
    val customActions: MutableMap<Group, List<Action>> = mutableMapOf()
}

sealed class OverlayType {
    object Normal : OverlayType()
    object Overlay : OverlayType()
}