package com.bugull.walle.action.content.logger

import android.os.Handler
import android.os.Message
import java.util.concurrent.*

/**
 * Author by xpl, Date on 2021/5/19.
 */
object LoggerManager {
    private var loggerRunnable: LoggerRunnable? = null
    private val sExecutorService: ExecutorService = ThreadPoolExecutor(
        1,
        5,
        60L,
        TimeUnit.SECONDS,
        SynchronousQueue(),
        ThreadPoolExecutor.AbortPolicy()
    )

    private val listeners: MutableList<OnLogcatListener> = mutableListOf()

    interface OnLogcatListener {
        fun onCatch(line: LogLine)
    }

    @JvmStatic
    fun addOnLogcatListener(listener: OnLogcatListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    @JvmStatic
    fun removeOnLogcatListener(listener: OnLogcatListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    @JvmStatic
    fun start() {
        stop()
        loggerRunnable = LoggerRunnable()
        sExecutorService.execute(loggerRunnable)
//        sExecutorService.execute(takeRunnable)
    }

    @JvmStatic
    fun stop() {
        loggerRunnable?.stop()
        buffer.clear()
        loggerRunnable = null
    }

    @JvmStatic
    fun cancel() {
        listeners.clear()
        buffer.clear()
        stop()
    }


    private var buffer: LinkedBlockingQueue<LogLine> = LinkedBlockingQueue()

   /* private val takeRunnable = {
        while (loggerRunnable?.running == true) {
            try {
                val logLine = buffer.take()
                Thread.sleep(100)
                listeners.forEach {
                    it.onCatch(logLine)
                }
            }catch (e:Throwable){

            }
        }
    }*/

    class LoggerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
//            buffer.add(msg.obj as LogLine)
            if (loggerRunnable?.running == true) {
                try {
                    listeners.forEach {
                        it.onCatch(msg.obj as LogLine)
                    }
                }catch (e:Throwable){

                }
            }

        }
    }


}