package com.app.mmse.username_screen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.mmse.R
import com.app.mmse.extras.database.TinyDB
import com.app.mmse.username_screen.model.User
import com.app.mmse.username_screen.utils.UsernameIntentUtils
import com.app.mmse.username_screen.utils.UsernameSharedPrefUtils
import kotlinx.android.synthetic.main.activity_username_new.*

class UsernameNewActivity : AppCompatActivity() {

    //Flag for checking if username already exists in the list
    var usernameAlreadyExists = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_new)

        //Add Username to the List once LoginUtils Button is Clicked
        username_login_btn.setOnClickListener {
            addUsernameToList()
        }
    }

    //This function adds username (entered in the field) to the list
    private fun addUsernameToList() {
        //Get the value of new username written in the field
        val newUser = username_edittext.text.toString()

        //Get the user list at "user" key
        val userListExisting = TinyDB(this)
            .getListObject(UsernameSharedPrefUtils().getUsernameListKey(), User::class.java)

        //Check if entered username in field already exists in the list
        for (result in userListExisting) {
            val user = result as User //Get result in User object form
            val existingUsername = user.username //Get username
            if (existingUsername == newUser) { //If Both Match
                usernameAlreadyExists = true //Change Flag
                break //Break the loop
            }
        }

        //Flag checking for username existence
        if (!usernameAlreadyExists) {
            //Add the user to user list
            userListExisting.add(User(newUser))
            //Put back the user list at the same key
            TinyDB(this).putListObject(UsernameSharedPrefUtils().getUsernameListKey(), userListExisting)
            //Put username value in username selected key
            TinyDB(this).putString(UsernameSharedPrefUtils()
                .getUsernameSelectedKey(), username_edittext.text.toString())
            //Takes to the First Screen Activity
            UsernameIntentUtils().goToFirstScreenActivity(this)
        } else {
            //Already Exists
            Toast.makeText(this, "'$newUser' username " +
                    "already exists! Try another one!", Toast.LENGTH_LONG).show()
        }

        //Resetting it back //So that when user changes username, detection works like above
        usernameAlreadyExists = false
    }

}
