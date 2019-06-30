package com.app.mmse.recall.delayed_recall.utils

import android.content.Context
import android.util.Log
import com.app.mmse.recall.utils.new_utils.IntentUtils
import com.app.mmse.recall.utils.new_utils.RunThroughUtils
import com.app.mmse.recall.utils.old_utils.OldSharedPrefUtils

class DelayedRecallRunThroughSystem {

    //Go to ApplePencilTable/OrangePaperWindow/LemonRulerFlower *DELAYED RECALL* activity as per the Run Through System
    fun adaptRunThroughSystem(ctx: Context) {
        val runThroughKey = RunThroughUtils(ctx).getUsernameRunThroughsKey()
        Log.d("RunThroughKey->adapt", runThroughKey)

        //todo --> 0 is default value being set from here
        val runThroughsDoneStr = OldSharedPrefUtils()
            .loadPreferences(ctx, runThroughKey, RunThroughUtils(ctx).getDefaultValueForRunThroughs())
        Log.d("RunThroughDneVal->adapt", runThroughsDoneStr)

        val runThroughsDone = runThroughsDoneStr?.toInt()

        when (runThroughsDone?.rem(3)) { //Modulus by 3
            //Zero Run Throughs Done Before
            0 -> IntentUtils().goToDelayedRecallApplePencilTableActivity(ctx)
            //One Run Through Done Before
            1 -> IntentUtils().goToDelayedRecallOrangePaperWindowActivity(ctx)
            //Two Run Throughs Done Before
            2 -> IntentUtils().goToDelayedRecallLemonRulerFlowerActivity(ctx)
        }
    }

    fun plusOneRunThroughNow(ctx: Context) {
        //get the key
        val runThroughKey = RunThroughUtils(ctx).getUsernameRunThroughsKey()
        //get the runthroughs done count
        val runThroughsDone = OldSharedPrefUtils()
            .loadPreferences(ctx, runThroughKey, RunThroughUtils(ctx).getDefaultValueForRunThroughs())
        //plus 1
        val plusOneDone = runThroughsDone?.toInt()?.plus(1)
        //put back the runthroughs done count at same key
        OldSharedPrefUtils().savePreferences(ctx, runThroughKey, plusOneDone.toString())
    }

}