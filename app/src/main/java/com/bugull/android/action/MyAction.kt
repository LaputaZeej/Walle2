package com.bugull.android.action

import android.content.Context
import com.bugull.walle.action.content.home.Action
import com.bugull.walle.action.content.home.Group

/**
 * Author by xpl, Date on 2021/5/6.
 */
class MyAction(override val group: Group, override val name: Int, override val icon: Int,
               override val step: Action.Step = Action.Step.NAN
) : Action {
    override fun doAction(context: Context) {
        TODO("Not yet implemented")
    }

    override fun init(context: Context) {
        TODO("Not yet implemented")
    }
}

