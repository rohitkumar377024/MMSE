package com.app.mmse.recall.utils.new_utils

import android.content.Context
import com.app.mmse.extras.database.TinyDB
import com.app.mmse.username_screen.utils.UsernameSharedPrefUtils

//Returns the key for run through count of a specific user
class RunThroughUtils(ctx: Context) {
    //Gets username selected
    val username = TinyDB(ctx).getString(UsernameSharedPrefUtils().getUsernameSelectedKey())
    //Gets run through count of that username selected
    fun getUsernameRunThroughsKey() = "${username}_runthroughs_done"
    //Gets the default value for run throughs!
    fun getDefaultValueForRunThroughs() = 0.toString()
}

//todo -> <> <> <> <> <> <> <> <> <> <> <> <> RUNTHROUGH WORKING NOTES BELOW!!!! <> <> <> <> <> <> <> <> <> <> <> <>

//todo -> key-value pair ->> "${username}_runthroughs_done", 0
//todo -> key will look like ->> "rishika_runthroughs_done"
//todo -> If 0 -> apple pencil table; If 1 -> orange paper window; If 2 -> lemon ruler flower
//todo -> When test ends >-> key-value pair ->> "$username_runthroughs", value + 1

