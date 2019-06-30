package com.app.mmse.orientation.temporal.month

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_temporal_month.*

class TemporalMonthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal_month)

        val temporalMonthList = arrayListOf("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December")
        temporal_month_recyclerview.layoutManager = LinearLayoutManager(this)
        temporal_month_recyclerview.adapter = TemporalMonthAdapter(temporalMonthList)
    }
}
