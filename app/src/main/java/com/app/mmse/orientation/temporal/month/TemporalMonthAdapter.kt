package com.app.mmse.orientation.temporal.month

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mmse.R
import com.app.mmse.orientation.temporal.utils.specific.CheckWhetherMonthCorrectOrNot
import kotlinx.android.synthetic.main.row_temporal.view.*

class TemporalMonthAdapter(val temporalMonthList: ArrayList<String>): RecyclerView.Adapter<TemporalMonthAdapter.TemporalMonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemporalMonthViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_temporal, parent, false)
        return TemporalMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: TemporalMonthViewHolder, position: Int) {
        val temporalMonthListItem = temporalMonthList[position]
        holder.itemView.row_temporal_txtview.text = temporalMonthListItem

        holder.itemView.row_temporal_txtview.setOnClickListener {
            val clickedMonth = holder.itemView.row_temporal_txtview.text.toString()
            Log.d("temporal->month", clickedMonth)
            //Check whether ->> Clicked month == Actual month going on!
            CheckWhetherMonthCorrectOrNot().check(clickedMonth)
        }
    }

    override fun getItemCount() = temporalMonthList.size

    class TemporalMonthViewHolder(view: View) : RecyclerView.ViewHolder(view)
}