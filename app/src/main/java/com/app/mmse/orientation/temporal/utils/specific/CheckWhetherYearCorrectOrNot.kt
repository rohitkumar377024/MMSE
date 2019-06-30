package com.app.mmse.orientation.temporal.utils.specific

import com.app.mmse.orientation.temporal.utils.main.CalendarUtils
import com.app.mmse.orientation.temporal.utils.main.TemporalScore

class CheckWhetherYearCorrectOrNot {
    //This function checks whether the year clicked is same as the current year
    fun check(clickedYear: String) {
        if (clickedYear.toInt() == CalendarUtils().year()) {
            //If same, increments the score
            TemporalScore().plusScore()
        }
    }
}