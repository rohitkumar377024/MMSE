package com.app.mmse.recall.utils.new_utils

import android.view.View

//This class helps in enabling and disabling GoBackButton with one line of code
//and keeping all related code centrally here.
class GoBackButton {
    fun disableGoBackButton(view: View) {
        //Makes Go Back Button translucent and un-clickable
        view.alpha = 0.5f
        view.isClickable = false
    }
    fun enableGoBackButton(view: View) {
        //Makes Go Back Button opaque and clickable
        view.alpha = 1f
        view.isClickable = true
    }
}