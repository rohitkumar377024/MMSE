package com.app.mmse.recall.first_recall.alternative_set_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.mmse.R
import com.app.mmse.recall.first_recall.utils.FirstRecallScoringSystem
import com.app.mmse.recall.utils.new_utils.*
import com.app.mmse.extras.audio.AudioUtils
import kotlinx.android.synthetic.main.activity_orange_paper_window.*

class OrangePaperWindowActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var speech: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private val LOG_TAG = "VoiceRecActivity"

    private lateinit var readyButton: View
    private lateinit var scoreTxtFinal: TextView

    private lateinit var goBackButton: View

    private lateinit var doneButton: View

    //When Results from Voice Recognition are Retrieved
    override fun onResults(results: Bundle) {
        Log.i(LOG_TAG, "onResults")
        val matches = results
            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        //var text = "" //for (result in matches!!) //text += result + "\n" //textView1?.text = text
        orange_paper_window_vr_txtview?.text = matches!![0]

        //If results exist
        if (matches[0] != null) {
            Toast.makeText(this, "${TrialsCounter.trials} Trials done", Toast.LENGTH_SHORT).show()

            //Gets value of how many answers are correct out of 3
            val scoreOf3 = FirstRecallScoringSystem().setupScoringSystem(matches[0],
                scoreTxtFinal, "orange", "paper", "window")

            //If 3 trials are done --> Go to next question
            if (TrialsCounter.trials == 3) {
                IntentUtils().goToDelayedRecallFirstActivity(this)
            }

            //If all 3 words are answered correctly, go to next question
            if (scoreOf3) {
                IntentUtils().goToDelayedRecallFirstActivity(this)
            }

            //If not all answers are correct & trials are left
            if (TrialsCounter.trials <= 2 && !scoreOf3) {
                //Trial +1
                TrialsCounter.trials++
                //Restart this activity after finishing itself
                finish()
                IntentUtils().goToOrangePaperWindowActivity(this)
                //Play sound of wrong answer //Different [error] beep
                AudioUtils().playAudio(this, R.raw.wrong_answer)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orange_paper_window)

        //Calling functions from onCreate()
        createViews()
        createSpeechRecogniser()
        playOrangePaperWindowAudio(R.raw.word_repeat_instruction, R.raw.ready_to_continue,
            R.raw.numeric_press_done_when_you_are_done, R.raw.orange_paper_window, R.raw.beep)
    }

    //Initialize speech recognition
    private fun initSpeechRecognition() {
        speech.startListening(recognizerIntent)
    }

    //End speech recognition
    private fun endSpeechRecognition() {
        speech.stopListening()
    }

    //SpeechRecogniser Additional Methods
    override fun onBeginningOfSpeech() { Log.i(LOG_TAG, "onBeginningOfSpeech") }
    override fun onBufferReceived(buffer: ByteArray) { Log.i(LOG_TAG, "onBufferReceived: $buffer") }
    override fun onEndOfSpeech() { Log.i(LOG_TAG, "onEndOfSpeech") }
    override fun onEvent(arg0: Int, arg1: Bundle) { Log.i(LOG_TAG, "onEvent") }
    override fun onPartialResults(arg0: Bundle) { Log.i(LOG_TAG, "onPartialResults") }
    override fun onReadyForSpeech(arg0: Bundle) { Log.i(LOG_TAG, "onReadyForSpeech") }
    override fun onRmsChanged(rmsdB: Float) { Log.i(LOG_TAG, "onRmsChanged: $rmsdB") }

    //Lifecycle Methods
    public override fun onResume() { super.onResume() }
    public override fun onPause() { super.onPause() }
    override fun onStop() { super.onStop(); if (speech != null) { speech.destroy();Log.i(LOG_TAG, "destroy"); } }

    //Showing error text
    override fun onError(errorCode: Int) {
        val errorMessage = getErrorText(errorCode)
        Log.d(LOG_TAG, "FAILED $errorMessage")
        orange_paper_window_vr_txtview?.text = errorMessage
    }

    //Error text passing to onError() method
    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }

    //Setup of views required
    private fun createViews() {
        scoreTxtFinal = findViewById(R.id.orange_paper_window_score_txt_final)
        readyButton = findViewById(R.id.orange_paper_window_ready_btn)
        goBackButton = findViewById(R.id.orange_paper_window_go_back_btn)
        doneButton = findViewById(R.id.orange_paper_window_done_btn)
    }

    //Creates speech recogniser
    private fun createSpeechRecogniser() {
        speech = SpeechRecognizer.createSpeechRecognizer(this)
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this))
        speech.setRecognitionListener(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
    }

    private fun playOrangePaperWindowAudio(audio_1: Int, audio2: Int, audio3: Int, audio4: Int, audio5: Int) {
        //Ready button disabled at start
        ReadyButton().disableReadyButton(readyButton)

        //Go back button disabled at start
        GoBackButton().disableGoBackButton(goBackButton)

        //Done button disabled at start
        DoneButton().disableDoneButton(doneButton)

        //Plays first audio and assigns it's duration to a val //todo AUDIO INFO -> "YOU ARE GOING TO HEAR 3 SIMPLE..."
        val startingAudioDuration = AudioUtils().playAudio(this, audio_1)
        Handler().postDelayed({
            //Plays second audio and assigns it's duration to a val //todo AUDIO INFO -> "PRESS READY TO CONTINUE"
            val secondDuration = AudioUtils().playAudio(this, audio2)
            Handler().postDelayed({
                //Plays third audio //todo AUDIO INFO -> "PRESS READY AGAIN WHEN..."
                val thirdDuration = AudioUtils().playAudio(this, audio3)
                Handler().postDelayed({
                    //Enabling ready button
                    ReadyButton().enableReadyButton(readyButton)

                    //Enabling go back button
                    GoBackButton().enableGoBackButton(goBackButton)
                    goBackButton.setOnClickListener {
                        //When Go Back Button is clicked, recall the function to repeat instructions and functionality
                        playOrangePaperWindowAudio(R.raw.word_repeat_instruction, R.raw.ready_to_continue,
                            R.raw.ready_again_when_done, R.raw.orange_paper_window, R.raw.beep)
                    }

                    //When Ready Button clicked -> play 3 words [apple, pencil, table] and start Voice Recognition
                    readyButton.setOnClickListener {
                        //play 3 words [apple, pencil, table] //todo AUDIO INFO -> "ORANGE PAPER WINDOW"
                        val fourthDuration = AudioUtils().playAudio(this, audio4)
                        //Disabling ready button
                        ReadyButton().disableReadyButton(readyButton)
                        GoBackButton().disableGoBackButton(goBackButton)
                        Handler().postDelayed({
                            //Play beep sound todo AUDIO INFO -> "BEEP"
                            val fifthDuration = AudioUtils().playAudio(this, audio5)
                            Handler().postDelayed({
                                //Enabling ready button
                                //ReadyButton().enableReadyButton(readyButton) //todo -- this was needed to be commented
                                //todo -> MOST IMPORTANT ---- SPEECH RECOGNITION BEGINS HERE
                                initSpeechRecognition()

                                //Enable back done button
                                DoneButton().enableDoneButton(doneButton)
                                //End speech recognition
                                doneButton.setOnClickListener {
                                    endSpeechRecognition()
                                    DoneButton().disableDoneButton(doneButton)
                                }

                            }, fifthDuration.toLong() + 1000) //1 second extra delay for better experience
                        }, fourthDuration.toLong())
                    }
                }, thirdDuration.toLong())
            }, secondDuration.toLong())
        }, startingAudioDuration.toLong())
    }

}
