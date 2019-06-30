package com.app.mmse.new_toolbox

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.new_toolbox.tools.Label
import com.app.mmse.new_toolbox.tools.PlayAudioFile
import kotlinx.android.synthetic.main.activity_new_toolbox.*


class NewToolboxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_toolbox)

        //Initializing Tools
        //Label(this).init()
        //PlayAudioFile(this, this).init()

        val containerLL: LinearLayout = findViewById(R.id.container_ll)

        containerLL.setOnClickListener {

//            val newLinearLayout = LinearLayout(this)
//            val textView = TextView(this)
//            textView.text = "Test 1 2 3 4 5 6 7 8 9 10"
//            newLinearLayout.addView(textView)
//            containerLL.addView(newLinearLayout)

//            val newLinearLayout = LinearLayout(this)
//            val child = layoutInflater.inflate(R.layout.tool_label_layout, null)
//            newLinearLayout.addView(child)
//            containerLL.addView(newLinearLayout)

            //val textView = TextView(this)
            //textView.text = "Test 1 2 3 4 5 6 7 8 9 10"
            //val child = layoutInflater.inflate(R.layout.tool_label_layout, null)

            //containerLL.addView(child)

            Label(this).init() //Let's see if this inflates the layout itself
        }
    }

    //Handling Activity Results for Select Audio Clip --> 'Play Audio File' Tool
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PlayAudioFile(this, this).onActivityResult(requestCode, resultCode, data)
    }

}
