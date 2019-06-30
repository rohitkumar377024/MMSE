package com.app.mmse.orientation.temporal.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.orientation.temporal.day.TemporalDayActivity
import com.app.mmse.orientation.temporal.month.TemporalMonthActivity
import com.app.mmse.orientation.temporal.questions.morning_afternoon_evening.MorningAfternoonEveningActivity
import com.app.mmse.orientation.temporal.utils.main.CalendarUtils
import com.app.mmse.orientation.temporal.year.TemporalYearActivity
import kotlinx.android.synthetic.main.activity_temporal_orientation.*

class TemporalOrientationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal_orientation)

        Log.d("picker-year", CalendarUtils().year().toString())
        Log.d("picker-month", CalendarUtils().month())
        Log.d("picker-date", CalendarUtils().date().toString())
        Log.d("picker-day", CalendarUtils().day())
        Log.d("picker-hoursOfDay", CalendarUtils().hours().toString())

        //todo ->> Maybe removed later if screen flow altered
        temporal_orientation_main_ready_btn.setOnClickListener {
            startActivity(Intent(this, TemporalDayActivity::class.java))
        }
    }

}
