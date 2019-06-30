package com.app.mmse.orientation.temporal.questions.morning_afternoon_evening

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mmse.R
import com.app.mmse.orientation.temporal.utils.specific.CheckWhetherMorningAfternoonEvening
import kotlinx.android.synthetic.main.row_temporal.view.*

class MorningAfternoonEveningAdapter(val morningAfternoonEveningList: ArrayList<String>): RecyclerView.Adapter
    <MorningAfternoonEveningAdapter.MorningAfternoonEveningViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MorningAfternoonEveningViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_temporal, parent, false)
        return MorningAfternoonEveningViewHolder(view)
    }

    override fun onBindViewHolder(holder: MorningAfternoonEveningViewHolder, position: Int) {
        val morningAfterEveningListItem = morningAfternoonEveningList[position]
        holder.itemView.row_temporal_txtview.text = morningAfterEveningListItem

        holder.itemView.row_temporal_txtview.setOnClickListener {
            val clickedText = holder.itemView.row_temporal_txtview.text.toString()
            Log.d("temporal", clickedText)
            //Check whether ->> Morning, Afternoon, Evening
            CheckWhetherMorningAfternoonEvening().check(clickedText)
        }
    }

    override fun getItemCount() = morningAfternoonEveningList.size

    class MorningAfternoonEveningViewHolder(view: View) : RecyclerView.ViewHolder(view)
}