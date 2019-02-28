package com.hackathon.lib.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * An observable event that can be triggered and run, also checks whether it was handled
 */
class ObservableEvent : LiveData<EmptyEvent>() {
    /**
     * Trigger the event once
     */
    fun trigger() {
        this.value = EmptyEvent()
    }

    operator fun invoke() {
        this.value = EmptyEvent()
    }

    /**
     * Creates an observer that runs the function once when the event is triggered
     */
    inline fun runWhenTriggered(owner: LifecycleOwner, crossinline fn: () -> Unit) {
        super.observe(owner, Observer {
            it?.runIfNotHandled { fn() }
        })
    }
}