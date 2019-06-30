package com.app.mmse.constructor.question_maker.choose_type

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.mmse.R
import com.app.mmse.constructor.question_maker.question_types.mcq.ConstructorMcqQuestionActivity
import com.app.mmse.constructor.question_maker.question_types.yes_or_no.ConstructorYesNoQuestionActivity
import com.app.mmse.constructor.utils.QuesUtils
import kotlinx.android.synthetic.main.activity_constructor_question_maker.*

class ConstructorQuestionMakerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constructor_question_maker)

        //todo --> Intents working for now
        yes_no_question_btn.setOnClickListener { startActivity(
            Intent(this, ConstructorYesNoQuestionActivity::class.java)) }
        mcq_type_question_btn.setOnClickListener { startActivity(
            Intent(this, ConstructorMcqQuestionActivity::class.java)) }

        showQuestionNumberAtTop()
    }

    @SuppressLint("SetTextI18n") private fun showQuestionNumberAtTop() {
        //We do +1, because the value increments once the next/done button is clicked
        //so we add that 1 here to show which question number it actually is!
        constructor_question_maker_title_txtview.text = "Question ${QuesUtils.currentQuestionCounter + 1}"
    }

}
