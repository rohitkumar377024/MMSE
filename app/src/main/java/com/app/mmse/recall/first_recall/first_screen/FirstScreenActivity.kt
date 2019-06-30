package com.app.mmse.recall.first_recall.first_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.mmse.R
import com.app.mmse.extras.database.TinyDB
import com.app.mmse.username_screen.utils.UsernameSharedPrefUtils

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var readyButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        init()
        setupWelcomeText()
    }

    private fun init() {
        readyButton = findViewById(R.id.first_screen_ready_btn)
        PlayFirstScreenAudio().playAudioForFirstScreen(this,
            readyButton, R.raw.ready_for_next_question, R.raw.ready_to_continue)
    }

    private fun setupWelcomeText() {
        val usernameSelected = TinyDB(this)
            .getString(UsernameSharedPrefUtils().getUsernameSelectedKey())

        Toast.makeText(this, "Hi $usernameSelected!", Toast.LENGTH_SHORT).show()
    }

}
