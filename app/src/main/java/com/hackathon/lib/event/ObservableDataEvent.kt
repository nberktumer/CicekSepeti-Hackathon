package com.hackathon.lib.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * An observable event that can be triggered and run, also checks whether it was handled
 *
 * This contains data unlike ObservableEvent, for example for returning a string message from event
 */
open class ObservableDataEvent<T> : LiveData<DataEvent<T>>() {
    /**
     * Trigger the event once with given data
     * @param content Event Data
     */
    fun trigger(content: T) {
        this.value = DataEvent(content)
    }

    operator fun invoke(content: T) {
        this.value = DataEvent(content)
    }

    /**
     * Creates an observer that runs the function once when the event is triggered
     */
    inline fun runWhenTriggered(owner: LifecycleOwner, crossinline fn: (T) -> Unit) {
        super.observe(owner, Observer { event ->
            event?.runWithDataIfNotHandled { fn(it) }
        })
    }
}