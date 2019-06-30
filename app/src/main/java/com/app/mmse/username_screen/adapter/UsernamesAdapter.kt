package com.app.mmse.username_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mmse.R
import com.app.mmse.extras.database.TinyDB
import com.app.mmse.username_screen.model.User
import com.app.mmse.username_screen.utils.UsernameIntentUtils
import com.app.mmse.username_screen.utils.UsernameSharedPrefUtils
import kotlinx.android.synthetic.main.row_username_list.view.*

//Takes Array List from UsernameChooseActivity [Line 29]
class UsernamesAdapter(private val users: ArrayList<Any>) : RecyclerView.Adapter<UsernamesAdapter.UsernameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsernameViewHolder {
        //Setup Username Row
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_username_list, parent, false)
        return UsernameViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsernameViewHolder, position: Int) {
        //Take User from The List to Bind at a Position
        val user = users[position] as User //This conversion saved us from *ANY* problems ;-;
        //Set the username onto the TextView
        holder.itemView.username_row_textview.text = user.username

        //Setup click listener
        holder.itemView.setOnClickListener {
            //Put Value in the username selected key
            TinyDB(holder.itemView.context)
                .putString(UsernameSharedPrefUtils()
                    .getUsernameSelectedKey(), user.username)
            //Go to First Screen Activity
            UsernameIntentUtils().goToFirstScreenActivity(holder.itemView.context)
        }
    }

    //Pass the size of array list
    override fun getItemCount() = users.size

    //ViewHolder basic setup
    class UsernameViewHolder(view: View): RecyclerView.ViewHolder (view)
}