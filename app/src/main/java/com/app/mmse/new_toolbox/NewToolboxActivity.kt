package com.app.mmse.new_toolbox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.new_toolbox.drag_and_drop.DragAndDropDemoActivity
import com.app.mmse.new_toolbox.tools.Label
import com.app.mmse.new_toolbox.tools.PlayAudioFile
import kotlinx.android.synthetic.main.activity_new_toolbox.*


class NewToolboxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_toolbox)

        //Initializing Tools
        Label(this).init()
        PlayAudioFile(this, this).init()

        //todo --> remove later once drag and drop demo done
        button2473x2.setOnClickListener {
            startActivity(Intent(this, DragAndDropDemoActivity::class.java))
        }
    }

    //Handling Activity Results for Select Audio Clip --> 'Play Audio File' Tool
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PlayAudioFile(this, this).onActivityResult(requestCode, resultCode, data)
    }

}
