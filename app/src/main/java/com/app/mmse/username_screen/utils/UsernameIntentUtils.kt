package com.app.mmse.username_screen.utils

import android.content.Context
import android.content.Intent
import com.app.mmse.recall.first_recall.first_screen.FirstScreenActivity
import com.app.mmse.username_screen.activities.UsernameChooseActivity
import com.app.mmse.username_screen.activities.UsernameNewActivity

//A Utils class for Intents in Username Package
class UsernameIntentUtils {
    //Takes to the First Screen Activity
    fun goToFirstScreenActivity(ctx: Context) {
        ctx.startActivity(Intent(ctx, FirstScreenActivity::class.java))
    }
    //Takes to the Username Choose Activity
    fun goToUsernameChooseActivity(ctx: Context) {
        ctx.startActivity(Intent(ctx, UsernameChooseActivity::class.java))
    }
    //Takes to the New Username Activity
    fun goToNewUsernameActivity(ctx: Context) {
        ctx.startActivity(Intent(ctx, UsernameNewActivity::class.java))
    }
}