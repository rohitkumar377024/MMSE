package com.app.mmse.constructor.utils

import android.content.Context
import android.content.Intent
import android.widget.Button
import com.app.mmse.constructor.TestCompletionActivity
import com.app.mmse.constructor.introduction.ConstructorIntroductionActivity
import com.app.mmse.constructor.question_maker.choose_type.ConstructorQuestionMakerActivity

class NextOrDoneButtonUtils {

    fun showDoneButtonIfLastQuestion(view: Button) {
        //Changes text from Next to Done if this is last question
        if (QuesUtils.currentQuestionCounter == QuesUtils.questionCount - 1) { view.text = "Done" }
    }

    fun handleNextAndDoneButtonLogic(ctx: Context, view: Button) {
        view.setOnClickListener {
            //Handle button clicks
            if (QuesUtils.currentQuestionCounter < QuesUtils.questionCount - 1) {
                //If current question is going on and more than one are still left
                //Increment the current question counter
                QuesUtils.currentQuestionCounter++
                //Restart this activity for the next question process
                ctx.startActivity(Intent(ctx, ConstructorQuestionMakerActivity::class.java))
            } else {
                //If current question is going on and this is the last one
                //todo change this later
                //Take to the next activity
                ctx.startActivity(Intent(ctx, TestCompletionActivity::class.java))
                //reset the values of no. of questions and current question counter
                QuesUtils().reset()
            }
        }
    }

}