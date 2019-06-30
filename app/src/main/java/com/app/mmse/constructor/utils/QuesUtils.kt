package com.app.mmse.constructor.utils

class QuesUtils {
    //Creating a centralized location to access the question count [being saved at Introduction Screen]
    companion object {
        var questionCount = 0 //Initialisation with 0
        var currentQuestionCounter = 0 //Counts which question number is currently being created
    }

    //Resetting both values back to 0
    fun reset() {
        questionCount = 0
        currentQuestionCounter = 0
    }
}