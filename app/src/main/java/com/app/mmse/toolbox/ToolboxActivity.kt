package com.app.mmse.toolbox

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_toolbox.*

class ToolboxActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbox)

        //Main Parent ->> ScrollView
        val scrollView = findViewById<ScrollView>(R.id.toolbar_scroll_view)

        //Add A Vertical Linear Layout to ScrollView
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        scrollView.addView(linearLayout)

        //ClickListeners
        toolbox_text_txtview.setOnClickListener { addTextView(linearLayout) }
        toolbox_input_txtview.setOnClickListener { addEditText(linearLayout) }
        toolbox_btn_txtview.setOnClickListener { addButton(linearLayout) }
    }

    //Add TextView
    @SuppressLint("SetTextI18n") private fun addTextView(linearLayout: LinearLayout) {
        val tv = TextView(applicationContext)
        tv.text = "New Text"
        tv.gravity = Gravity.CENTER
        tv.textSize = 25f
        linearLayout.addView(tv)
    }

    //Add EditText
    @SuppressLint("SetTextI18n") private fun addEditText(linearLayout: LinearLayout) {
        val et = EditText(applicationContext)
        et.hint = "Field - Edit Me"
        et.gravity = Gravity.CENTER
        linearLayout.addView(et)
    }

    //Add Button
    @SuppressLint("SetTextI18n") private fun addButton(linearLayout: LinearLayout) {
        val btn = Button(this)
        btn.text = "Button"
        btn.gravity = Gravity.CENTER
        linearLayout.addView(btn)
    }

}
