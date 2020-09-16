package com.bcaf.ivan.finalprojectandroid.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.ViewHolder.BusListViewHolder
import com.bcaf.ivan.finalprojectandroid.R

class BusListAdapter(private val list:List<Bus>) : RecyclerView.Adapter<BusListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListViewHolder {
        return BusListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list, parent, false)
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: BusListViewHolder, position: Int) {
        holder.busCode.text = list?.get(position)?.code
        holder.busMake.text = list?.get(position)?.make
        holder.busCard.setOnClickListener { view ->
            Toast.makeText(view.context,holder.busCode.text,Toast.LENGTH_LONG).show()
        }
    }

}