package com.app.mmse.orientation.temporal.questions.morning_afternoon_evening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_morning_afternoon_evening.*

class MorningAfternoonEveningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morning_afternoon_evening)

        val morningAfternoonEveningList = arrayListOf("Morning", "Afternoon", "Evening")
        morning_afternoon_evening_recyclerview.layoutManager = LinearLayoutManager(this)
        morning_afternoon_evening_recyclerview.adapter = MorningAfternoonEveningAdapter(morningAfternoonEveningList)
    }
}
