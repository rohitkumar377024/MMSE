package com.app.mmse.orientation.temporal.utils.specific

import com.app.mmse.orientation.temporal.utils.main.CalendarUtils
import com.app.mmse.orientation.temporal.utils.main.TemporalScore

class CheckWhetherDayCorrectOrNot {
    //This function checks whether the day clicked is same as the current day
    fun check(clickedDay: String) {
        if (clickedDay == CalendarUtils().day()) {
            //If same, increments the score
            TemporalScore().plusScore()
        }
    }
}