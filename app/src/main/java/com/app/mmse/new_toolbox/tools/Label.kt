package com.app.mmse.new_toolbox.tools

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.app.mmse.R

class Label(activity: Activity) {

    private val mActivity = activity
    private lateinit var labelLayout: View
    private lateinit var playAudioFileLayout: View
    private lateinit var toolboxLabel: ImageView
    private lateinit var labelTopLL: LinearLayout
    private lateinit var labelBottomLL: LinearLayout
    private lateinit var labelInputEditText: EditText
    private lateinit var labelShowTextView: TextView
    private lateinit var labelDoneButton: Button
    private lateinit var labelDescriptionTextView: TextView
    private var isChangeVisible: Boolean = false

    fun init() {
        findViewByIds()
        showLabelTool()
        handleRealtimeLabelPreview()
        handleButtonStates()
    }

    private fun findViewByIds() { /* Connecting All UI Views with their respective IDs */
        labelLayout = mActivity.findViewById(R.id.label_layout)
        playAudioFileLayout = mActivity.findViewById(R.id.play_audio_file_layout)
        toolboxLabel = mActivity.findViewById(R.id.tool_box_label)
        labelInputEditText = mActivity.findViewById(R.id.label_user_input_edittext)
        labelShowTextView = mActivity.findViewById(R.id.label_show_text_txtview)
        labelDoneButton = mActivity.findViewById(R.id.label_done_btn)
        labelDescriptionTextView = mActivity.findViewById(R.id.label_description_txtview)
        labelTopLL = mActivity.findViewById(R.id.label_top_ll)
        labelBottomLL = mActivity.findViewById(R.id.label_bottom_ll)
    }

    private fun showLabelTool() {
        toolboxLabel.setOnClickListener { //Showing up the 'Label Layout'
            show(labelLayout)
            //todo ->> Remove this later on
            hide(playAudioFileLayout)
        }
    }

    private fun handleRealtimeLabelPreview() { /* Lets user preview how their label will look ->> In Realtime */
        labelInputEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when {
                    //If Empty, Set text to 'Sample Label' default
                    s.toString().trim().isEmpty() -> labelShowTextView.text = "Sample Label"
                    //Else, Set the label text as currently typed text in EditText >>- REALTIME -->
                    else -> labelShowTextView.text = s.toString()
                }
            }
        })
    }

    private fun handleButtonStates() {
        labelDoneButton.setOnClickListener { //Handles switching between 'Done' and 'Change Label Text'
            if (!isChangeVisible) showDoneState()
            else if (isChangeVisible) showChangeLabelTextState()
        }
    }

    private fun showDoneState() { /* Shows Done Button State - Means Editable */
        hideKeyboard() //Hide the keyboard /* first priority */
        labelDoneButton.text = "Change Label Text" //Change Text
        hide(labelTopLL) //Hide The Top Half
        show(labelBottomLL) //Only show Bottom Half ->> Finalized Label + Change Label Text
        isChangeVisible = true //Change Boolean State
    }

    private fun showChangeLabelTextState() { /* Shows Change Button State - Means Changeable */
        showKeyboard() //Show the keyboard /* first priority */
        labelDoneButton.text = "Done" //Change Text
        show(labelTopLL) //Show Top Half ->> Allows user to make changes to Label
        show(labelBottomLL) //Also show bottom Half with Done Text
        isChangeVisible = false //Change Boolean State
    }

    private fun hideKeyboard() { /* Hides Android's Soft Keyboard */
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mActivity.currentFocus?.windowToken, 0)
    }

    private fun showKeyboard() { /* Shows Android's Soft Keyboard */
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hide(view: View) { view.visibility = View.GONE } /* Helper method to Hide Specific View */
    private fun show(view: View) { view.visibility = View.VISIBLE } /* Helper method to Show Specific View */

}