package com.app.mmse.constructor.question_maker.question_types.mcq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.app.mmse.R
import com.app.mmse.constructor.utils.NextOrDoneButtonUtils

class ConstructorMcqQuestionActivity : AppCompatActivity() {

    lateinit var nextOrDoneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constructor_mcq_question)

        initViewsAndNextDoneButtonLogic()
    }

    private fun initViewsAndNextDoneButtonLogic() {
        nextOrDoneButton = findViewById(R.id.constructor_mcq_next_or_done_btn)
        NextOrDoneButtonUtils().showDoneButtonIfLastQuestion(nextOrDoneButton)
        NextOrDoneButtonUtils().handleNextAndDoneButtonLogic(this, nextOrDoneButton)
    }

}
