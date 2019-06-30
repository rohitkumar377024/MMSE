package com.app.mmse.recall.utils.new_utils

import android.content.Context
import android.content.Intent
import com.app.mmse.recall.delayed_recall.alternative_set_1.DelayedRecallOrangePaperWindowActivity
import com.app.mmse.recall.delayed_recall.alternative_set_2.DelayedRecallLemonRulerFlowerActivity
import com.app.mmse.recall.delayed_recall.first_screen.DelayedRecallFirstScreenActivity
import com.app.mmse.recall.delayed_recall.original.DelayedRecallApplePencilTableActivity
import com.app.mmse.recall.first_recall.alternative_set_1.OrangePaperWindowActivity
import com.app.mmse.recall.first_recall.alternative_set_2.LemonRulerFlowerActivity
import com.app.mmse.recall.next_question.NextQuestionActivity
import com.app.mmse.recall.first_recall.original_set.ApplePencilTableActivity

class IntentUtils {
    fun goToApplePencilTableActivity(ctx: Context) = ctx.startActivity(Intent(ctx, ApplePencilTableActivity::class.java))
    fun goToOrangePaperWindowActivity(ctx: Context) = ctx.startActivity(Intent(ctx, OrangePaperWindowActivity::class.java))
    fun goToLemonRulerFlowerActivity(ctx: Context) = ctx.startActivity(Intent(ctx, LemonRulerFlowerActivity::class.java))

    fun goToDelayedRecallApplePencilTableActivity(ctx: Context) = ctx.startActivity(Intent(ctx, DelayedRecallApplePencilTableActivity::class.java))
    fun goToDelayedRecallOrangePaperWindowActivity(ctx: Context) = ctx.startActivity(Intent(ctx, DelayedRecallOrangePaperWindowActivity::class.java))
    fun goToDelayedRecallLemonRulerFlowerActivity(ctx: Context) = ctx.startActivity(Intent(ctx, DelayedRecallLemonRulerFlowerActivity::class.java))

    fun goToDelayedRecallFirstActivity(ctx: Context) = ctx.startActivity(Intent(ctx, DelayedRecallFirstScreenActivity::class.java))
    fun goToNextQuestionActivity(ctx: Context) = ctx.startActivity(Intent(ctx, NextQuestionActivity::class.java))
}