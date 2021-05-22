package com.bugull.android.basic

/**
 * Author by xpl, Date on 2021/5/7.
 */

fun <T> MutableList<T>.removeCondition(
    condition: T.() -> Boolean = { true },
    block: (T.() -> Unit)? = null
) {
    val iterator = this.iterator()
    while (iterator.hasNext()) {
        val next = iterator.next()
        if (condition(next)) {
            iterator.remove()
            block?.invoke(next)
        }
    }
}