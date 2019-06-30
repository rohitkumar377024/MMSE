package com.app.mmse.recall.utils.new_utils

import android.view.View

//This class helps in enabling and disabling ReadyButton with one line of code
//and keeping all related code centrally here.
class ReadyButton {
    fun disableReadyButton(view: View) {
        //Makes Ready Button translucent and un-clickable
        view.alpha = 0.5f
        view.isClickable = false
    }
    fun enableReadyButton(view: View) {
        //Makes Ready Button opaque and clickable
        view.alpha = 1f
        view.isClickable = true
    }
}