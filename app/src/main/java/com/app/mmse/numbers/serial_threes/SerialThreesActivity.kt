package com.app.mmse.numbers.serial_threes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.app.mmse.R
import com.app.mmse.extras.audio.AudioUtils
import com.app.mmse.recall.utils.new_utils.DoneButton
import com.app.mmse.recall.utils.new_utils.ReadyButton
import kotlinx.android.synthetic.main.activity_serial_threes.*
import android.widget.ArrayAdapter

class SerialThreesActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var speech: SpeechRecognizer
    private lateinit var recognizerIntent: Intent
    private val LOG_TAG = "VoiceRecActivity"

    private lateinit var resultTxtView: TextView

    private lateinit var readyButton: View
    private lateinit var doneButton: View

    private lateinit var outputListView: ListView
    var outputListItems = ArrayList<String>()
    var outputItemsFinalList = ArrayList<String>()
    var outputListAdapter: ArrayAdapter<String>? = null

    //Tracks Input Count for displaying in List
    var inputCount = 0

    private var firstInputChecker = 0
    private var secondInputChecker = 0
    private var thirdInputChecker = 0
    private var fourthInputChecker = 0
    private var fifthInputChecker = 0

    //When Partial Results from Voice Recognition are Retrieved
    override fun onPartialResults(arg0: Bundle) {
        Log.i(LOG_TAG, "onPartialResults")

        val matches = arg0.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        resultTxtView.text = "Partial Results: ${matches!![0]}"
        //Lets pass match[0] to a more readable variable name and todo *REMOVED ITS BLANK SPACES*
        //this .replace(...) function below removes all the blank spaces
        val partial_voice_results = matches[0].replace("\\s".toRegex(), "")
        resultTxtView.text = "Partial Results: $partial_voice_results"
        //Size of partial results of voice recognition
        val partial_voice_size = partial_voice_results.length

        //<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
        //If first number [TENS place] is 61
        //If only first number digit exists
        if (partial_voice_size >= 2) {
            //and the first number said by user is 61, play 'wrong answer' audio
            if (partial_voice_results[0] == '6' && partial_voice_results[1] == '1') {
                //Play audio for wrong answer!
                AudioUtils().playAudio(this, R.raw.wrong_answer)
                //todo ->> Restarting audio system [skipping first audio clip, starting from second audio clip]
                //let's restart audio system!
                playSerialThreesAudioMini(R.raw.numeric_first_number_you_say,
                    R.raw.numeric_press_ready_to_continue, R.raw.numeric_press_done_when_you_are_done, R.raw.beep)
            }
        }

        //*********Recognising each spoken input and displaying as output*********
        //todo <> ->> full logic below <<- <>
        //If it has atleast 1 number [Tens digit] && it has numbers of TENS place only! [mod operator checking this]
        if (partial_voice_size >= 2 && partial_voice_size % 2 == 0) {
            //Add all elements to the listview //take all elements one by one below
            //Loop all chars and iterate by 2
            for (idx in 0 until partial_voice_size step 2) {
                //combine char with next char ['5' + '8' = "58""
                val combinedTenDigit = "${partial_voice_results[idx]}${partial_voice_results[idx+1]}"
                Log.d("charzy", combinedTenDigit)
                //If voice said is not a number
                if (NumericUtils().isNumeric(combinedTenDigit)) {
                    //Numeric
                    //If the number said by the user -> matches ->> with the number they said earlier
                    if (outputListItems.contains(combinedTenDigit)) {
                        //Don't add to the list
                    } else {
                        //Adding number said by user to the arraylist for listview
                        outputListItems.add(combinedTenDigit)
                        //Incrementing the Input Count here
                        inputCount++
                        //todo --> we created another arraylist to avoid 'multiple occurance' problem
                        outputItemsFinalList.add("Input $inputCount: ${outputListItems[outputListItems.size - 1]}")
                        //Notify the adapter that the data set has been changed >-> IF ADAPTER NOT NULL
                        if (outputListAdapter != null) { outputListAdapter?.notifyDataSetChanged() }
                        //todo -> at the end, adding a sound --> to show ->> INPUT ACCEPTED!
                        //Playing an audio whenever the input from user is accepted
                        AudioUtils().playAudio(this, R.raw.series_three_input_accepted)
                    }
                }
            }
        }

        //*********Check if mistake in spoken input:*********
        //todo <> ->> full logic below <<- <>
        if (partial_voice_size == 2 && (partial_voice_results[0] != '5' || partial_voice_results[1] != '8')) {
            //todo --> Value != 58 // Helps below function to call only once ->> todo -> solves multiple audio
            if (firstInputChecker == 0) {
                Log.d("ic_1",inputCount.toString()); mistakeInInput(); firstInputChecker = 1
            }
        }

        if (partial_voice_size == 4 && (partial_voice_results[2] != '5' || partial_voice_results[3] != '5')) {
            //todo --> Value != 55 // Helps below function to call only once ->> todo -> solves multiple audio
            if (secondInputChecker == 0) {
                Log.d("ic_2",inputCount.toString()); mistakeInInput(); secondInputChecker = 1
            }
        }

        if (partial_voice_size == 6 && (partial_voice_results[4] != '5' || partial_voice_results[5] != '2')) {
            //todo --> Value != 52 // Helps below function to call only once ->> todo -> solves multiple audio
            if (thirdInputChecker == 0) {
                Log.d("ic_3",inputCount.toString()); mistakeInInput(); thirdInputChecker = 1
            }
        }

        if (partial_voice_size == 8 && (partial_voice_results[6] != '4' || partial_voice_results[7] != '9')) {
            //todo --> Value != 49 // Helps below function to call only once ->> todo -> solves multiple audio
            if (fourthInputChecker == 0) {
                Log.d("ic_4",inputCount.toString()); mistakeInInput(); fourthInputChecker = 1
            }
        }

        if (partial_voice_size == 10 && (partial_voice_results[8] != '4' || partial_voice_results[9] != '6')) {
            //todo --> Value != 46 // Helps below function to call only once ->> todo -> solves multiple audio
            if (fifthInputChecker == 0) {
                Log.d("ic_5",inputCount.toString()); mistakeInInput(); fifthInputChecker = 1
            }
        }

    }













    private fun mistakeInInput() {
        //Play audio ->> wrong answer!
        val oneDuration = AudioUtils().playAudio(this, R.raw.wrong_answer)
        Handler().postDelayed({
//            //Play audio ->> 'The correct answer is'
//            val twoDuration = AudioUtils().playAudio(this, R.raw.numeric_correct_answer_is)
//            Handler().postDelayed({
//                //Play audio ->> 'The next number is three back...'
//                val threeDuration = AudioUtils().playAudio(this, R.raw.numeric_the_next_number)
//                Handler().postDelayed({
//                    //Play audio ->> 'Press ready to continue'
//                    val fourDuration = AudioUtils().playAudio(this, R.raw.numeric_press_ready_to_continue)
//                    Handler().postDelayed({
//
//                        //todo ->> build a system now to go ahead from here
//
//                    }, fourDuration.toLong())
//                }, threeDuration.toLong())
//            }, twoDuration.toLong())
        }, oneDuration.toLong())
    }





























    //When Results from Voice Recognition are Retrieved
    override fun onResults(results: Bundle) {
        Log.i(LOG_TAG, "onResults")
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        //var text = "" //for (result in matches!!) //text += result + "\n" //textView1?.text = text

        //<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>
        //If results exist
        if (matches!![0] != null) {
            //Lets pass match[0] to a more readable variable name and todo *REMOVED ITS BLANK SPACES*
            //this .replace(...) function below removes all the blank spaces
            val voice_results = matches[0].replace("\\s".toRegex(), "")
            resultTxtView.text = "Voice Recognition: $voice_results"

            //Serial 3s Logic todo --> [for now]
            //Keeps track of boolean value ->> whether correct answer or not
            var isCorrect = false

            //Answer cannot be correct if the length is less than 10 [5 tens place digits = 5 * 2 = 10] ror mo
            if (voice_results.length < 10 || voice_results.length > 10) {
                isCorrect = false
            } else {
                //Check when ->> length is 10 --> if values do match with 58 55 52 49 46
                   if ((voice_results[0] == '5' && voice_results[1] == '8')             //58
                    && (voice_results[2] == '5' && voice_results[3] == '5')             //55
                    && (voice_results[4] == '5' && voice_results[5] == '2')             //52
                    && (voice_results[6] == '4' && voice_results[7] == '9')             //49
                    && (voice_results[8] == '4' && voice_results[9] == '6')) {          //46
                       //Change the boolean value to true
                       isCorrect = true
                }
            }

            //Checking flags
            if (isCorrect) {
                //Correct answer
                serial_threes_score_txtview.text = "Correct!"
            } else if (!isCorrect) {
                //Incorrect answer
                serial_threes_score_txtview.text = "Incorrect!"
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_threes)

        //Calling functions from onCreate()
        createViews()
        createSpeechRecogniser()
        initOutputList()

        playSerialThreesAudio(
            R.raw.numeric_count_backwards_int_threes, R.raw.numeric_first_number_you_say,
            R.raw.numeric_press_ready_to_continue, R.raw.numeric_press_done_when_you_are_done, R.raw.beep
        )
    }

    private fun initOutputList() {
        //todo --> initializing and setting array adapter here
        outputListAdapter = ArrayAdapter(applicationContext,
            android.R.layout.simple_list_item_1, outputItemsFinalList)
        outputListView.adapter = outputListAdapter
    }

    //Initialize speech recognition
    private fun initSpeechRecognition() {
        speech.startListening(recognizerIntent)
    }

    //End speech recognition
    private fun endSpeechRecognition() {
        speech.stopListening()
        DoneButton().disableDoneButton(doneButton)
    }

    //Ending speech recognition from here calling function
    override fun onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech")
        endSpeechRecognition()
    }

    //SpeechRecogniser Additional Methods
    override fun onBeginningOfSpeech() { Log.i(LOG_TAG, "onBeginningOfSpeech") }
    override fun onBufferReceived(buffer: ByteArray) { Log.i(LOG_TAG, "onBufferReceived: $buffer") }
    override fun onEvent(arg0: Int, arg1: Bundle) { Log.i(LOG_TAG, "onEvent") }
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
        readyButton = findViewById(R.id.serial_threes_ready_btn)
        doneButton = findViewById(R.id.serial_threes_done_btn)
        resultTxtView = findViewById(R.id.serial_threes_result_txtview)
        outputListView = findViewById(R.id.output_listview)
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
        //todo -> may work in enabling partial results
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    private fun playSerialThreesAudio(audio_1: Int, audio2: Int, audio3: Int, audio4: Int, audio5: Int) {
        //Ready button & done button disabled at start
        ReadyButton().disableReadyButton(readyButton)
        DoneButton().disableDoneButton(doneButton)

        //Plays first audio and assigns it's duration to a val //todo AUDIO INFO -> "PLEASE COUNT BACKWARDS AGAIN, BUT THIS.."
        val startingAudioDuration = AudioUtils().playAudio(this, audio_1)
        Handler().postDelayed({
          playSerialThreesAudioMini(audio2, audio3, audio4, audio5)
        }, startingAudioDuration.toLong())
    }

    //Created for '61 AT FIRST' and also used as a subset in Main Audio System of Serial 3s
    private fun playSerialThreesAudioMini(audio2: Int, audio3: Int, audio4: Int, audio5: Int) {
        //todo -> repeated here because this function will be called seperately when '61 AT FIRST'
        //Ready button & done button disabled at start
        ReadyButton().disableReadyButton(readyButton)
        DoneButton().disableDoneButton(doneButton)

        //Plays second audio and assigns it's duration to a val //todo AUDIO INFO -> "THE FIRST NUMBER YOU SAY NOW IS..."
        val secondDuration = AudioUtils().playAudio(this, audio2)
        Handler().postDelayed({
            //Plays third audio //todo AUDIO INFO -> "PRESS READY TO CONTINUE"
            val thirdDuration = AudioUtils().playAudio(this, audio3)
            Handler().postDelayed({
                //todo AUDIO INFO -> "PRESS DONE WHEN YOU ARE DONE"
                val fourthDuration = AudioUtils().playAudio(this, audio4)
                Handler().postDelayed({
                    //Enable Ready Button
                    ReadyButton().enableReadyButton(readyButton)

                    //Ready Button Clicked
                    readyButton.setOnClickListener {
                        //todo AUDIO INFO -> "BEEP"
                        val fifthDuration = AudioUtils().playAudio(this, audio5)
                        Handler().postDelayed({

                            //Disable Ready Button
                            ReadyButton().disableReadyButton(readyButton)

                            //Start Speech Recognition
                            initSpeechRecognition()

                            //Resetting
                            reset()

                            //Enable Done Button
                            DoneButton().enableDoneButton(doneButton)

                            //Done Button Clicked
                            doneButton.setOnClickListener {
                                //End Speech Recognition
                                endSpeechRecognition()
                            }

                        }, fifthDuration.toLong())
                    }

                }, fourthDuration.toLong())
            }, thirdDuration.toLong())
        }, secondDuration.toLong())
    }

    //This function resets the items of output list, empties the voice recognition and score text :)
    private fun reset() {
        //todo ->> clearing output items array list here
        outputListItems.clear()
        outputItemsFinalList.clear()
        outputListAdapter!!.notifyDataSetChanged()
        //todo ->> clearing txtview for voice recognition and score
        resultTxtView.text = ""
        serial_threes_score_txtview.text = ""
        //todo ->> resetting input count
        inputCount = 0
    }

}
