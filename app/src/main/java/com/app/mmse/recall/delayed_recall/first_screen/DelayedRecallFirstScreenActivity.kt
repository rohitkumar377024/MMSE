package com.app.mmse.recall.delayed_recall.first_screen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.app.mmse.R
import com.app.mmse.recall.delayed_recall.utils.DelayedRecallRunThroughSystem
import com.app.mmse.recall.utils.new_utils.ReadyButton
import com.app.mmse.extras.audio.AudioUtils

class DelayedRecallFirstScreenActivity : AppCompatActivity() {

    private lateinit var readyButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delayed_recall_first_screen)

        init()
    }

    private fun init() {
        readyButton = findViewById(R.id.delayed_recall_first_screen_ready_btn)
        playAudioForDelayedRecallFirstScreen(this, readyButton,
                R.raw.ready_for_next_question, R.raw.ready_to_continue)
    }

    private fun playAudioForDelayedRecallFirstScreen(ctx: Context, readyButton: View, audio_1: Int, audio_2: Int) {
        //Ready button disabled at start
        ReadyButton().disableReadyButton(readyButton)
        //Plays first audio and assigns it's duration to a val //todo AUDIO INFO -> "ARE YOU READY FOR THE NEXT QUESTION"
        val startingAudioDuration = AudioUtils().playAudio(ctx, audio_1)
        Handler().postDelayed({
            //Plays second audio and assigns it's duration to a val //todo AUDIO INFO -> "PRESS READY TO CONTINUE"
            val secondDuration = AudioUtils().playAudio(ctx, audio_2)
            Handler().postDelayed({
                //Enabling ready button
                ReadyButton().enableReadyButton(readyButton)
                //When Ready Button clicked, go to SecondScreenActivity
                readyButton.setOnClickListener {
                    ReadyButton().disableReadyButton(readyButton)
                    //todo -> Using Delayed Recall Run through System Here!
                    DelayedRecallRunThroughSystem().adaptRunThroughSystem(ctx)
                }
            }, secondDuration.toLong())
        }, startingAudioDuration.toLong())
    }

}
