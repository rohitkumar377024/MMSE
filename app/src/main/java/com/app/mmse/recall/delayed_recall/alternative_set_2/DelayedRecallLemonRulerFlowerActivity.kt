package com.app.mmse.recall.delayed_recall.alternative_set_2

import android.content.Context
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
import com.app.mmse.recall.delayed_recall.utils.DelayedRecallRunThroughSystem
import com.app.mmse.recall.delayed_recall.utils.DelayedRecallScoringSystem
import com.app.mmse.recall.utils.new_utils.*
import com.app.mmse.extras.audio.AudioUtils
import kotlinx.android.synthetic.main.activity_delayed_recall_lemon_ruler_flower.*

class DelayedRecallLemonRulerFlowerActivity : AppCompatActivity(), RecognitionListener {

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
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        //var text = "" //for (result in matches!!) //text += result + "\n" //textView1?.text = text
        delayed_recall_lemon_ruler_flower_vr_txtview?.text = matches!![0]

        //If results exist
        if (matches[0] != null) {
            Toast.makeText(this, "${TrialsCounter.trials} Trials done", Toast.LENGTH_SHORT).show()

            //Gets value of how many answers are correct out of 3
            val score = DelayedRecallScoringSystem().setupScoringSystem(matches[0],
                scoreTxtFinal, "lemon", "ruler", "flower")

            //If not all answers are correct
            if (score != 3) {
                AudioUtils().playAudio(this, R.raw.wrong_answer)
            }

            //Only one trial in Delayed recall
            //That's why just finishing it and going to the next activity
            //todo -> also +1 to run through count
            DelayedRecallRunThroughSystem().plusOneRunThroughNow(this)
            finish()
            IntentUtils().goToNextQuestionActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delayed_recall_lemon_ruler_flower)

        //Resetting value of trials
        TrialsCounter.trials = 1 //initial value of trials -> 1

        createViews()
        createSpeechRecogniser()
        playAudioForDelayedRecall(this, readyButton,
            R.raw.delayed_recall_instructions, R.raw.ready_to_continue, R.raw.numeric_press_done_when_you_are_done)
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
        delayed_recall_lemon_ruler_flower_vr_txtview?.text = errorMessage
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
        scoreTxtFinal = findViewById(R.id.delayed_recall_lemon_ruler_flower_score_txt_final)
        readyButton = findViewById(R.id.delayed_recall_lemon_ruler_flower_ready_btn)
        goBackButton = findViewById(R.id.delayed_recall_lemon_ruler_flower_go_back_btn)
        doneButton = findViewById(R.id.delayed_recall_lemon_ruler_flower_done_button)
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

    private fun playAudioForDelayedRecall(ctx: Context, readyButton: View, audio_1: Int, audio_2: Int, audio_3: Int) {
        //Ready button disabled at start
        ReadyButton().disableReadyButton(readyButton)

        //Go back button disabled at start
        GoBackButton().disableGoBackButton(goBackButton)

        //Done button disabled at start
        DoneButton().disableDoneButton(doneButton)

        //Plays first audio and assigns it's duration to a val //todo AUDIO INFO -> "EARLY ON IN THIS SET OF TESTS..."
        val startingAudioDuration = AudioUtils().playAudio(ctx, audio_1)
        Handler().postDelayed({
            //Plays second audio and assigns it's duration to a val //todo AUDIO INFO -> "PRESS READY TO CONTINUE"
            val secondDuration = AudioUtils().playAudio(ctx, audio_2)
            Handler().postDelayed({
                //Plays third audio and assigns it's duration to a val //todo AUDIO INFO -> "PRESS READY AGAIN WHEN..."
                val thirdDuration = AudioUtils().playAudio(ctx, audio_3)
                Handler().postDelayed({
                    //Enabling ready button
                    ReadyButton().enableReadyButton(readyButton)

                    //Enabling ready button
                    GoBackButton().enableGoBackButton(goBackButton)
                    goBackButton.setOnClickListener {
                        //When Go Back Button is clicked, recall the function to repeat instructions and functionality
                        playAudioForDelayedRecall(this, readyButton,
                            R.raw.delayed_recall_instructions, R.raw.ready_to_continue, R.raw.ready_again_when_done)
                    }

                    //When Ready Button clicked, go to SecondScreenActivity
                    readyButton.setOnClickListener {
                        ReadyButton().disableReadyButton(readyButton)
                        GoBackButton().disableGoBackButton(goBackButton)
                        //todo ->> <><><> *Starts speech recognition for Delayed Recall* <><><> <<-
                        initSpeechRecognition()

                        //Enable back done button
                        DoneButton().enableDoneButton(doneButton)
                        //End speech recognition
                        doneButton.setOnClickListener {
                            endSpeechRecognition()
                            DoneButton().disableDoneButton(doneButton)
                        }

                    }
                }, thirdDuration.toLong())
            }, secondDuration.toLong())
        }, startingAudioDuration.toLong())
    }

}
