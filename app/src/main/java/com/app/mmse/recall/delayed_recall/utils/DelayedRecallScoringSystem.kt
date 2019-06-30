package com.app.mmse.recall.delayed_recall.utils

import android.widget.TextView

class DelayedRecallScoringSystem {
    //Scoring System Core
    //todo -> will return true if all correct and false if not, for now
    //todo -> later on, check with all matches -- not just matches[0]
    fun setupScoringSystem(finalSpeechResult: String, scoreTxtFinal: TextView,
                           firstWord: String, secondWord: String, thirdWord: String): Int {
        //TemporalScore Tracker //0 at start
        var score = 0

        //Scoring System Safety Checks
        var firstDone = false //Apple
        var secondDone = false //Pencil
        var thirdDone = false //Table

        if (finalSpeechResult.contains(firstWord, true)          //First Word [Apple]
            || finalSpeechResult.contains("${firstWord}s", true)
            || finalSpeechResult.contains("$firstWord's", true)) {
            if (!firstDone) { score++;  firstDone = true }; if (firstDone) {}
        }
        if (finalSpeechResult.contains(secondWord, true)         //Second Word [Pencil]
            || finalSpeechResult.contains("${secondWord}s", true)
            || finalSpeechResult.contains("$firstWord's", true)) {
            if (!secondDone) { score++; secondDone = true }; if (secondDone) {}
        }
        if (finalSpeechResult.contains(thirdWord, true)          //Third Word [Table]
            || finalSpeechResult.contains("${thirdWord}s", true)
            || finalSpeechResult.contains("$firstWord's", true)) {
            if (!thirdDone) { score++; thirdDone = true }; if (thirdDone) {}
        }
        scoreTxtFinal.text = "TemporalScore: $score"

        //todo -> If yes, returns true. Thanks for the simpler logic JetBrains! :D
        return score
    }
}