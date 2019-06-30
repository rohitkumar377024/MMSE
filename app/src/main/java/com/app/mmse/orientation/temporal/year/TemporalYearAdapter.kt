package com.app.mmse.orientation.temporal.year

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mmse.R
import com.app.mmse.orientation.temporal.utils.specific.CheckWhetherYearCorrectOrNot
import kotlinx.android.synthetic.main.row_temporal.view.*

class TemporalYearAdapter(val temporalYearList: ArrayList<Int>)
    : RecyclerView.Adapter<TemporalYearAdapter.TemporalYearViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemporalYearViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_temporal, parent, false)
        return TemporalYearViewHolder(view)
    }

    override fun onBindViewHolder(holder: TemporalYearViewHolder, position: Int) {
        val temporalYearListItem = temporalYearList[position]
        holder.itemView.row_temporal_txtview.text = temporalYearListItem.toString()

        holder.itemView.row_temporal_txtview.setOnClickListener {
            val clickedYear = holder.itemView.row_temporal_txtview.text.toString()
            Log.d("temporal->year", clickedYear)
            //Check whether ->> Clicked month == Actual month going on!
            CheckWhetherYearCorrectOrNot().check(clickedYear)
        }
    }

    override fun getItemCount() = temporalYearList.size

    class TemporalYearViewHolder(view: View) : RecyclerView.ViewHolder(view)
}