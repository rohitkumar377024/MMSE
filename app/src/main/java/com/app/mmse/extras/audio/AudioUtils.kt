package com.app.mmse.extras.audio

import android.content.Context
import android.media.MediaPlayer

//This class makes it easier to play audio
//IRONY IS THAT ANDROID MAKES IT SO DIFFICULT TO PLAY AUDIO WITHOUT CRASHING ;_; *cries*
class AudioUtils {

    //This function handles the playing of audio, releasing resources on completion and also returns audio duration
    fun playAudio(context: Context, raw: Int) : Int {
        //Create a MediaPlayer
        val mediaPlayer = MediaPlayer.create(context, raw)
        //Take the duration of the audio
        val duration = mediaPlayer.duration
        //Start playing the audio
        mediaPlayer.start()
        //Set a Completion Listener
        mediaPlayer.setOnCompletionListener { mp ->
            //Handling the resource release part
            mp.release()
        }
        //Return the duration of the audio
        return duration
    }

}