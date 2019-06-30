package com.app.mmse.constructor.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.mmse.constructor.question_maker.choose_type.ConstructorQuestionMakerActivity
import com.app.mmse.R
import com.app.mmse.constructor.utils.QuesUtils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_constructor_introduction.*

class ConstructorIntroductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constructor_introduction)

        handleNextButtonLogic()
    }

    private fun handleNextButtonLogic() {
        //When Next Button clicked
        constructor_introduction_next_btn.setOnClickListener {
            //Get EditText Value
            val numberOfQuestionsInput = constructor_introduction_number_of_questions_edittext.text.toString()
            //Boolean to see whether input is empty or not
            val isEmpty = numberOfQuestionsInput.isEmpty()

            //Checking all conditions now
            //Value is empty
            if (isEmpty) { Toasty.warning(this, "Enter a number!", Toast.LENGTH_SHORT).show() }
            //Value is 0
            if (numberOfQuestionsInput == 0.toString()) {
                Toasty.warning(this, "Enter a number greater than 0!", Toast.LENGTH_SHORT).show()
            }
            //Value != 0 and value != empty
            if (numberOfQuestionsInput != 0.toString() && numberOfQuestionsInput.isNotEmpty()) {
                //Not Empty
                //Change value of Question Count Centrally
                QuesUtils.questionCount = numberOfQuestionsInput.toInt()
                //Take to the next activity
                startActivity(Intent(this, ConstructorQuestionMakerActivity::class.java))
            }
        }
    }

}
