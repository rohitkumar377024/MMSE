package com.app.mmse.numbers.five_to_one_reverse

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.extras.audio.AudioUtils
import com.app.mmse.recall.utils.new_utils.DoneButton
import com.app.mmse.recall.utils.new_utils.ReadyButton
import kotlinx.android.synthetic.main.activity_five_to_one_reverse.*

class FiveToOneReverseActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var speech: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private val LOG_TAG = "VoiceRecActivity"

    private lateinit var resultTxtView: TextView

    private lateinit var readyButton: View
    private lateinit var doneButton: View

    //When Results from Voice Recognition are Retrieved
    override fun onResults(results: Bundle) {
        Log.i(LOG_TAG, "onResults")
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        //var text = "" //for (result in matches!!) //text += result + "\n" //textView1?.text = text
        resultTxtView.text = "Voice Recognition: ${matches!![0]}"

        //If results exist
        if (matches[0] != null) {
            //Lets pass match[0] to a more readable variable name
            val voice_results = matches[0]

            val isReverse = ReverseLogic().checkReverseLogic(voice_results)

            //Checking the flags
            if (isReverse) { //Not Reverse 5 --> 1
                Toast.makeText(this, "Reverse!", Toast.LENGTH_SHORT).show()
                five_to_one_reverse_score_txtview.text = "TemporalScore: 1" //1 score for correct
            }
            if (!isReverse) { //Reverse 5 --> 1
                Toast.makeText(this, "Not Reverse!", Toast.LENGTH_SHORT).show()
                five_to_one_reverse_score_txtview.text = "TemporalScore: 0" //0 score for incorrect
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five_to_one_reverse)

        //Calling functions from onCreate()
        createViews()
        createSpeechRecogniser()

        playReverseFiveToOneAudio(
            R.raw.numeric_next_few_questions, R.raw.numeric_counting_backwards,
            R.raw.numeric_press_ready_to_continue, R.raw.numeric_press_done_when_you_are_done, R.raw.beep
        )
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
    override fun onStop() {
        super.onStop(); if (speech != null) {
            speech.destroy();Log.i(LOG_TAG, "destroy"); }
    }

    //Showing error text
    override fun onError(errorCode: Int) {
        val errorMessage = getErrorText(errorCode)
        Log.d(LOG_TAG, "FAILED $errorMessage")
        resultTxtView.text = errorMessage
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
        readyButton = findViewById(R.id.five_to_one_reverse_ready_btn)
        doneButton = findViewById(R.id.five_to_one_reverse_done_btn)
        resultTxtView = findViewById(R.id.five_to_one_result_txtview)
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

    private fun playReverseFiveToOneAudio(audio_1: Int, audio2: Int, audio3: Int, audio4: Int, audio5: Int) {
        //Ready button & done button disabled at start
        ReadyButton().disableReadyButton(readyButton)
        DoneButton().disableDoneButton(doneButton)

        //Plays first audio and assigns it's duration to a val //todo AUDIO INFO -> "THE NEXT FEW QUESTIONS..."
        val startingAudioDuration = AudioUtils().playAudio(this, audio_1)
        Handler().postDelayed({
            //Plays second audio and assigns it's duration to a val //todo AUDIO INFO -> "COUNTING BACKWARDS..."
            val secondDuration = AudioUtils().playAudio(this, audio2)
            Handler().postDelayed({
                //Plays third audio //todo AUDIO INFO -> "PRESS READY TO CONTINUE"
                val thirdDuration = AudioUtils().playAudio(this, audio3)
                Handler().postDelayed({
                    //todo AUDIO INFO -> "PRESS DONE WHEN YOU ARE DONE"
                    val fourthDuration = AudioUtils().playAudio(this, audio4)
                    Handler().postDelayed({

                        //Enabling Ready Button
                        ReadyButton().enableReadyButton(readyButton)

                        readyButton.setOnClickListener {
                            //Play beep sound todo AUDIO INFO -> "BEEP"
                            val fifthDuration = AudioUtils().playAudio(this, audio5)
                            Handler().postDelayed({

                                //Start Speech Recognition after Beep
                                initSpeechRecognition()

                                //Enabling Done Button and Disabling Ready Button
                                ReadyButton().disableReadyButton(readyButton)
                                DoneButton().enableDoneButton(doneButton)

                                //End Speech Recognition after Done Button is clicked
                                doneButton.setOnClickListener {
                                    endSpeechRecognition()
                                    //Disabling done button
                                    DoneButton().disableDoneButton(doneButton)
                                }

                            }, fifthDuration.toLong())
                        }

                    }, fourthDuration.toLong())
                }, thirdDuration.toLong())
            }, secondDuration.toLong())
        }, startingAudioDuration.toLong())
    }

}
