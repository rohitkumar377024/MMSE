package com.app.mmse.orientation.temporal.year

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_temporal_year.*

class TemporalYearActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal_year)

        val temporalYearList = arrayListOf<Int>()
        for (x in 1985..2035) { temporalYearList.add(x) } //Adding 1985 to 2035 using Range
        temporal_year_recyclerview.layoutManager = LinearLayoutManager(this)
        temporal_year_recyclerview.adapter = TemporalYearAdapter(temporalYearList)
    }
}
