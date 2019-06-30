package com.app.mmse.recall.first_recall.first_screen

import android.content.Context
import android.os.Handler
import android.view.View
import com.app.mmse.recall.first_recall.utils.FirstRecallRunThroughSystem
import com.app.mmse.recall.utils.new_utils.ReadyButton
import com.app.mmse.extras.audio.AudioUtils

class PlayFirstScreenAudio {

    fun playAudioForFirstScreen(ctx: Context, readyButton: View, audio_1: Int, audio_2: Int) {
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
                    //todo -> Using Run through System Here!
                    FirstRecallRunThroughSystem().adaptRunThroughSystem(ctx)
                }
            }, secondDuration.toLong())
        }, startingAudioDuration.toLong())
    }

}