package com.hackathon.util

import com.hackathon.di.ILogger

/**
 * Simple Unit Test Logger for usage instead of Android's Log.* functions
 */
class UnitTestLogger: ILogger {
    private fun printTyped(type: String, msg: String) {
        println("$type: $msg")
    }
    override fun v(msg: String) = printTyped("V", msg)

    override fun d(msg: String) = printTyped("D", msg)

    override fun w(msg: String) = printTyped("W", msg)

    override fun e(msg: String) = printTyped("E", msg)

}