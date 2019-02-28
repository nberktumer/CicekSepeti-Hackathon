package com.hackathon.lib.event

/**
 * A simple event structure with no data
 * @property hasBeenHandled Whether the event has been handled
 */
sealed class Event {
    var hasBeenHandled = false
        protected set

    /**
     * Run a function if the event is not handled and mark the event handled while doing so
     */
    fun <T> runIfNotHandled(fn: () -> T): T? = when (hasBeenHandled) {
        true -> null
        else -> {
            hasBeenHandled = true
            fn()
        }
    }
}

class EmptyEvent : Event()
class DataEvent<out T>(
    private val content: T
) : Event() {
    fun <U> runWithDataIfNotHandled(fn: (T) -> U): U? = when (hasBeenHandled) {
        true -> null
        else -> {
            hasBeenHandled = true
            fn(content)
        }
    }

    fun peekContent(): T {
        return content
    }
}