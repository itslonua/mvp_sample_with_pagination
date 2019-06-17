package com.ok7apps.farmdropexample.core.recycler

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator


class RecyclerEmptyAnimation : SimpleItemAnimator() {

    init {
        changeDuration = 0L
        supportsChangeAnimations = false
    }

    override fun animateAdd(oldHolder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(oldHolder)
        return false
    }

    override fun runPendingAnimations() {
        //stub
    }

    override fun animateMove(oldHolder: RecyclerView.ViewHolder?, p1: Int, p2: Int, p3: Int, p4: Int): Boolean {
        dispatchMoveFinished(oldHolder)
        return false
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder?,
                               newHolder: RecyclerView.ViewHolder?, p2: Int, p3: Int, p4: Int, p5: Int): Boolean {
        dispatchChangeFinished(oldHolder, false)
        dispatchChangeFinished(newHolder, false)
        return false
    }

    override fun isRunning(): Boolean {
        return false
    }

    override fun endAnimation(holder: RecyclerView.ViewHolder) {
        //stub
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchRemoveFinished(holder)
        return false
    }

    override fun endAnimations() {
        // stub
    }
}