package com.app.mmse.new_toolbox

import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R


class DragAndDropDemoActivity : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {

    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var text5: TextView
    private lateinit var text6: TextView

    //When touched text gets dropped into either text4 or text5 or text6 then this method will be called
    override fun onDrag(v: View, event: DragEvent): Boolean {
        if (event.action == DragEvent.ACTION_DROP) {
            //handle the dragged view being dropped over a target view
            val dropped = event.localState as TextView
            val dropTarget = v as TextView
            //stop displaying the view where it was before it was dragged
            dropped.visibility = View.INVISIBLE

            //if an item has already been dropped here, there will be different string
            val text = dropTarget.text.toString()
            //if there is already an item here, set it back visible in its original place
            if (text == text1.text.toString())
                text1.visibility = View.VISIBLE
            else if (text == text2.text.toString())
                text2.visibility = View.VISIBLE
            else if (text == text3.text.toString()) text3.visibility = View.VISIBLE

            //update the text and color in the target view to reflect the data being dropped
            dropTarget.text = "${dropped.text}: Dropped here!" //todo --> changed here
            dropTarget.setBackgroundColor(Color.BLUE)
        }
        return true
    }

    //When text1 or text2 or text3 gets clicked or touched then this method will be called
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDrag(null, shadowBuilder, v, 0)
            return true
        } else
            return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_and_drop_demo)

        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)
        text3 = findViewById(R.id.text3)
        text4 = findViewById(R.id.text4)
        text5 = findViewById(R.id.text5)
        text6 = findViewById(R.id.text6)

        //Setting touch and drag listeners
        text1.setOnTouchListener(this)
        text2.setOnTouchListener(this)
        text3.setOnTouchListener(this)
        text4.setOnDragListener(this)
        text5.setOnDragListener(this)
        text6.setOnDragListener(this)
    }
}