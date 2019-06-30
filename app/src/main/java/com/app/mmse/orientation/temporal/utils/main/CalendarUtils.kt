package com.app.mmse.orientation.temporal.utils.main

import java.util.*

//This class acts as a helper to return the date-time details :)
class CalendarUtils {
    fun year() = Calendar.getInstance().get(Calendar.YEAR)                                             //2019
    fun month() = FormatCalendar().formatMonth(Calendar.getInstance().get(Calendar.MONTH)+1)  //6 (June)
    fun date() = Calendar.getInstance().get(Calendar.DATE)                                             //17 (Date)
    fun day() = FormatCalendar().formatDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))         //2 (Tuesday)
    fun hours() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)                                     //17 (5PM)
}