package com.bcaf.ivan.finalprojectandroid.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Controller.ListBusActivity
import com.bcaf.ivan.finalprojectandroid.Controller.MainActivity
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.ViewHolder.BusListViewHolder
import com.bcaf.ivan.finalprojectandroid.R

class BusListAdapter(private val list:List<Bus>) : RecyclerView.Adapter<BusListViewHolder>(){
    lateinit var activity:CustomActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListViewHolder {
        activity= CustomActivity(parent.context as MainActivity)
        return BusListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list, parent, false)
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: BusListViewHolder, position: Int) {
        holder.busCode.text = list[position].code
        holder.busMake.text = list[position].make
        holder.busCard.setOnClickListener { view ->
            activity.start(ListBusActivity::class.java,key="busId",value= list[position].id!!)
        }
    }

}