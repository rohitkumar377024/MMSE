package com.app.mmse.recall.first_recall.utils

import android.content.Context
import android.util.Log
import com.app.mmse.recall.utils.new_utils.IntentUtils
import com.app.mmse.recall.utils.new_utils.RunThroughUtils
import com.app.mmse.recall.utils.old_utils.OldSharedPrefUtils

class FirstRecallRunThroughSystem {
    //Go to ApplePencilTable/OrangePaperWindow/LemonRulerFlower activity as per the Run Through System
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
            0 -> IntentUtils().goToApplePencilTableActivity(ctx)
            //One Run Through Done Before
            1 -> IntentUtils().goToOrangePaperWindowActivity(ctx)
            //Two Run Throughs Done Before
            2 -> IntentUtils().goToLemonRulerFlowerActivity(ctx)
        }
    }

}