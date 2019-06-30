package com.app.mmse.orientation.temporal.utils.main

import android.util.Log

class TemporalScore {
    companion object {
        //Temporal score initially --> 0
        var score = 0
    }

    fun plusScore() {
        //Increment the score
        score++
        Log.d("temporal_score", score.toString())
    }

}