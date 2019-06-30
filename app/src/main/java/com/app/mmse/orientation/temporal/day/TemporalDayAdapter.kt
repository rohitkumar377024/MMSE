package com.app.mmse.orientation.temporal.day

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mmse.R
import com.app.mmse.orientation.temporal.utils.specific.CheckWhetherDayCorrectOrNot
import kotlinx.android.synthetic.main.row_temporal.view.*

class TemporalDayAdapter(val temporalDayList: ArrayList<String>)
    : RecyclerView.Adapter<TemporalDayAdapter.TemporalDayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemporalDayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_temporal, parent, false)
        return TemporalDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: TemporalDayViewHolder, position: Int) {
        val temporalDayListItem = temporalDayList[position]
        holder.itemView.row_temporal_txtview.text = temporalDayListItem

        holder.itemView.row_temporal_txtview.setOnClickListener {
            val clickedDay = holder.itemView.row_temporal_txtview.text.toString()
            Log.d("temporal->day", clickedDay)
            //Check whether ->> Clicked month == Actual month going on!
            CheckWhetherDayCorrectOrNot().check(clickedDay)
        }
    }

    override fun getItemCount() = temporalDayList.size


    class TemporalDayViewHolder(view: View) : RecyclerView.ViewHolder(view)
}