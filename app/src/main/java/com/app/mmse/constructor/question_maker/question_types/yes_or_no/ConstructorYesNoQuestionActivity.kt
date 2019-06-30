package com.app.mmse.constructor.question_maker.question_types.yes_or_no

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.constructor.utils.NextOrDoneButtonUtils
import kotlinx.android.synthetic.main.activity_constructor_yes_no_question.*
import lib.kingja.switchbutton.SwitchMultiButton


class ConstructorYesNoQuestionActivity : AppCompatActivity() {

    lateinit var nextOrDoneButton: Button
    lateinit var switchMultiButton: SwitchMultiButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constructor_yes_no_question)

        initViewsAndNextDoneButtonLogic()
        handleChangeYesNoTextButtonsLogic()

        yes_or_now_go_to_preview_screen.setOnClickListener {
            startActivity(Intent(this, ConstructorYesOrNoPreviewActivity::class.java))
        }
    }

    private fun initViewsAndNextDoneButtonLogic() {
        nextOrDoneButton = findViewById(R.id.constructor_yes_or_no_next_or_done_btn)
        switchMultiButton = findViewById(R.id.switchmultibutton)
        NextOrDoneButtonUtils().showDoneButtonIfLastQuestion(nextOrDoneButton)
        NextOrDoneButtonUtils().handleNextAndDoneButtonLogic(this, nextOrDoneButton)
    }

    private fun handleChangeYesNoTextButtonsLogic() { //Handles showing and hiding of these
        if (switchMultiButton.selectedTab == 0) { hideChangeYesNoTextButtons() }  //'No Change To Yes/No Text' Enabled
        else if (switchMultiButton.selectedTab == 1) { showChangeYesNoTextButtons() }  //'Change Yes/No Text' Enabled

        switchMultiButton.setOnSwitchListener { position, tabText ->
            if (position == 1) { showChangeYesNoTextButtons() }  //'Change Yes/No Text' Enabled
            else { hideChangeYesNoTextButtons() } }  //'No Change To Yes/No Text' Enabled
    }

    private fun showChangeYesNoTextButtons() { //Hides them
        yes_or_no_yes_btn_new_text_edittxt.visibility = View.VISIBLE
        yes_or_no_no_btn_new_text_edittxt.visibility = View.VISIBLE
    }

    private fun hideChangeYesNoTextButtons() { //Shows them
        yes_or_no_yes_btn_new_text_edittxt.visibility = View.GONE
        yes_or_no_no_btn_new_text_edittxt.visibility = View.GONE
    }

}
