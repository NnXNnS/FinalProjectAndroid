package com.bcaf.ivan.finalprojectandroid.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.R

open class LandingPageViewHolder : RecyclerView.ViewHolder {
    var busCode: TextView
    var busMake: TextView
    var busCard:CardView

    constructor(v: View) : super(v) {
        busCode = v.findViewById(R.id.txt_busCode)
        busMake = v.findViewById(R.id.txt_busMake)
        busCard = v.findViewById(R.id.card_bus)
    }
}