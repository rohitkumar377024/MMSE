package com.app.mmse.recall.next_question

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.mmse.R
import com.app.mmse.recall.utils.new_utils.RunThroughUtils
import com.app.mmse.recall.utils.new_utils.TrialsCounter
import com.app.mmse.recall.utils.old_utils.OldSharedPrefUtils
import com.app.mmse.username_screen.activities.UsernameMainActivity
import kotlinx.android.synthetic.main.activity_next_question.*

class NextQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_question)

        //Resetting value of trials
        TrialsCounter.trials = 1 //initial value of trials -> 1
        //todo -- earlier the above value was 0 which was causing the bug

        val runThroughKey = RunThroughUtils(this).getUsernameRunThroughsKey()
        Log.d("RunThroughKey->nextq", runThroughKey)

        //todo --> 0 is default value being set from here
        val runThroughsDone = OldSharedPrefUtils()
                .loadPreferences(this, runThroughKey,
                RunThroughUtils(this).getDefaultValueForRunThroughs())
        Log.d("RunThroughDneVal->nextq", runThroughsDone)

        //Another PlayThrough Button
        //todo -> add this to IntentUtils later on!
        button998.setOnClickListener { startActivity(Intent(this, UsernameMainActivity::class.java)) }
    }

    //todo ->> uncomment it only if trial value needs to be checked.
    //Toast.makeText(this, "${TrialsCounter.trials} Trials Done", Toast.LENGTH_SHORT).show()
}
