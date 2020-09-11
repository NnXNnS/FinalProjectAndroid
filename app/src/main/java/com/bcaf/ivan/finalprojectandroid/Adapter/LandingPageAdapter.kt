package com.bcaf.ivan.finalprojectandroid.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.ViewHolder.LandingPageViewHolder
import com.bcaf.ivan.finalprojectandroid.R

class LandingPageAdapter(private val list:List<Bus>) : RecyclerView.Adapter<LandingPageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandingPageViewHolder {
        return LandingPageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list, parent, false)
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: LandingPageViewHolder, position: Int) {
        holder.busCode.text = list?.get(position)?.code
        holder.busMake.text = list?.get(position)?.make
        holder.busCard.setOnClickListener { view ->
            Toast.makeText(view.context,holder.busCode.text,Toast.LENGTH_LONG).show()
        }
    }

}