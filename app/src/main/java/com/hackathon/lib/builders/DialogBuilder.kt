package com.hackathon.lib.builders

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

@DslMarker
annotation class DialogDsl

@DialogDsl
class DialogBuilder(val context: Context) {

    private val builder = AlertDialog.Builder(context)

    var title: String = ""
        set(value) {
            builder.setTitle(value)
        }
    var content: String = ""
        set(value) {
            builder.setMessage(value)
        }
    var cancelable: Boolean = true
        set(value) {
            builder.setCancelable(value)
        }

    private fun button(fn: ButtonBuilder.() -> Unit): ButtonBuilder = ButtonBuilder(context).apply(fn)

    fun negativeButton(fn: ButtonBuilder.() -> Unit) {
        val buttonBuilder = button(fn)
        builder.setNegativeButton(buttonBuilder.content, buttonBuilder.onClick)
    }

    fun positiveButton(fn: ButtonBuilder.() -> Unit) {
        val buttonBuilder = button(fn)
        builder.setPositiveButton(buttonBuilder.content, buttonBuilder.onClick)
    }

    fun build(): AlertDialog {
        return builder.create()
    }
}

@DialogDsl
class ButtonBuilder(val context: Context) {
    var content: String = ""
    var onClick: (DialogInterface, Int) -> Unit = { _, _ -> }
}

inline fun dialog(context: Context, fn: DialogBuilder.() -> Unit): AlertDialog =
    DialogBuilder(context).apply(fn).build()