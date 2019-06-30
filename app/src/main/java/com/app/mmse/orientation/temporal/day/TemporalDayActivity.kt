package com.app.mmse.orientation.temporal.day

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_temporal_day.*

class TemporalDayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal_day)

        val temporalDayList = arrayListOf("Monday",
            "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        temporal_day_recyclerview.layoutManager = LinearLayoutManager(this)
        temporal_day_recyclerview.adapter = TemporalDayAdapter(temporalDayList)
    }
}
