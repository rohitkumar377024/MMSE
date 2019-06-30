package com.app.mmse.username_screen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mmse.R
import com.app.mmse.extras.database.TinyDB
import com.app.mmse.username_screen.adapter.UsernamesAdapter
import com.app.mmse.username_screen.model.User
import com.app.mmse.username_screen.utils.UsernameSharedPrefUtils
import kotlinx.android.synthetic.main.activity_username_choose.*

class UsernameChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_choose)

       //Setup Things Needed For User List and RecyclerView
        setupUserListAndRecyclerView()
    }

    //Sets up the username list  from SharedPreferences
    //Also sets up basic properties of RecyclerView
    private fun setupUserListAndRecyclerView() {
        //Get the user list from Database //todo -> this gets key below -- clean code
        val userList = TinyDB(this)
            .getListObject(UsernameSharedPrefUtils().getUsernameListKey(), User::class.java)
        //Set layout manager and adapter for RecyclerView to display username list
        usernames_recyclerview.layoutManager = LinearLayoutManager(this)
        usernames_recyclerview.adapter = UsernamesAdapter(userList)
    }

}
