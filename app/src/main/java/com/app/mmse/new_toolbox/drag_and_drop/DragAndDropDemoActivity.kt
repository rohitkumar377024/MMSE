package com.app.mmse.new_toolbox.drag_and_drop

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import kotlinx.android.synthetic.main.activity_drag_and_drop_demo.*

class DragAndDropDemoActivity : AppCompatActivity(), View.OnTouchListener, View.OnDragListener {

    //When touched text gets dropped into either text4 or text5 or text6 then this method will be called
    override fun onDrag(v: View, event: DragEvent): Boolean {
        if (event.action == DragEvent.ACTION_DROP) {
            //handle the dragged view being dropped over a target view
            val dropped = event.localState as TextView
            val dropTarget = v as TextView
            //stop displaying the view where it was before it was dragged
            dropped.visibility = View.INVISIBLE

            //if an item has already been dropped here, there will be different string
            when (dropTarget.text.toString()) {
                //if there is already an item here, set it back visible in its original place
                text1.text.toString() -> text1.visibility = View.VISIBLE
                text2.text.toString() -> text2.visibility = View.VISIBLE
                text3.text.toString() -> text3.visibility = View.VISIBLE
            }

            //update the text and color in the target view to reflect the data being dropped
            dropTarget.text = "${dropped.text}: Dropped here!" //todo --> changed here
            dropTarget.setBackgroundColor(Color.BLUE)
        }
        return true
    }

    //When text1 or text2 or text3 gets clicked or touched then this method will be called
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return if (event.action == MotionEvent.ACTION_DOWN) {
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDrag(null, shadowBuilder, v, 0)
            true
        } else
            false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_and_drop_demo)

        /* Setting Touch and Drag Listeners */
        text1.setOnTouchListener(this)

        text2.setOnDragListener(this)
        text3.setOnDragListener(this)
        text4.setOnDragListener(this)
        text5.setOnDragListener(this)
        text6.setOnDragListener(this)
        text7.setOnDragListener(this)
        text8.setOnDragListener(this)
        text9.setOnDragListener(this)
        text10.setOnDragListener(this)
        text11.setOnDragListener(this)
        text12.setOnDragListener(this)
        text13.setOnDragListener(this)
        text14.setOnDragListener(this)
        text15.setOnDragListener(this)
    }
}