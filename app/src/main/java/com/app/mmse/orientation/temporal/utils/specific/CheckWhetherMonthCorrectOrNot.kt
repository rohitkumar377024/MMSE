package com.app.mmse.orientation.temporal.utils.specific

import com.app.mmse.orientation.temporal.utils.main.CalendarUtils
import com.app.mmse.orientation.temporal.utils.main.TemporalScore

class CheckWhetherMonthCorrectOrNot {
    //This function checks whether the month clicked is same as the current month
    fun check(clickedMonth: String) {
        if (clickedMonth == CalendarUtils().month()) {
            //If same, increments the score
            TemporalScore().plusScore()
        }
    }
}