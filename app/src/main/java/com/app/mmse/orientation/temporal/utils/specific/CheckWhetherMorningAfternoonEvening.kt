package com.app.mmse.orientation.temporal.utils.specific

import com.app.mmse.orientation.temporal.utils.main.CalendarUtils
import com.app.mmse.orientation.temporal.utils.main.TemporalScore

class CheckWhetherMorningAfternoonEvening {
    //Checks whether it is morning, afternoon or evening
    fun check(clickedText: String) {
        when {
            //Morning if < 1PM
            clickedText == "Morning" && CalendarUtils().hours() < 13 -> TemporalScore().plusScore()
            //Afternoon if > 11AM
            clickedText == "Afternoon" && CalendarUtils().hours() > 11 -> TemporalScore().plusScore()
            //Evening if > 5PM
            clickedText == "Evening" && CalendarUtils().hours() > 17 -> TemporalScore().plusScore()
        }
    }
}