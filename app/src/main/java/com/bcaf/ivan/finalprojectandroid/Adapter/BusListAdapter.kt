package com.bcaf.ivan.finalprojectandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.ivan.finalprojectandroid.Controller.MainActivity
import com.bcaf.ivan.finalprojectandroid.Entity.Bus
import com.bcaf.ivan.finalprojectandroid.Helper.CustomActivity
import com.bcaf.ivan.finalprojectandroid.R
import com.bcaf.ivan.finalprojectandroid.ViewHolder.BusListViewHolder


class BusListAdapter(private val list:List<Bus>) : RecyclerView.Adapter<BusListViewHolder>(){
    lateinit var activity:CustomActivity
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListViewHolder {
        activity= CustomActivity(parent.context as MainActivity)
        context= parent.context
        return BusListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_list, parent, false)
        )
    }

    override fun getItemCount(): Int = list?.size

    override fun onBindViewHolder(holder: BusListViewHolder, position: Int) {
        holder.busCode.text = list[position].code
        holder.busMake.text = list[position].make
        holder.busCapacity.text = list[position].capacity
        holder.btn_expand.setOnClickListener { view ->
            if(holder.lyt_detail.visibility==View.GONE){
                rotateImageUp(holder.btn_expand)
                slideDown(holder.lyt_detail)
//                holder.lyt_detail.visibility = View.VISIBLE
                holder.btn_expand.background = ContextCompat.getDrawable(context, R.drawable.ic_shrink)
            }else{
                rotateImageDown(holder.btn_expand)
                slideUp(holder.lyt_detail)
//                holder.lyt_detail.visibility = View.GONE
                holder.btn_expand.background = ContextCompat.getDrawable(context, R.drawable.ic_expand)
            }
//            activity.start(ListBusActivity::class.java,key="busId",value= list[position].id!!)
        }
    }
    fun rotateImageDown(view: View) {


        val animSlideDown = AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_down
        )
        animSlideDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        });
        view.startAnimation(animSlideDown)
    }
    fun rotateImageUp(view: View) {


        val animSlideUp = AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_up
        )
        animSlideUp.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        });
        view.startAnimation(animSlideUp)
    }
    fun slideUp(view: View) {


        val animSlideUp = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_up
        )
        animSlideUp.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        });
        view.startAnimation(animSlideUp)
    }
    fun slideDown(view: View) {
        val animSlideDown: Animation = AnimationUtils.loadAnimation(
            context,
            R.anim.slide_down
        )
        animSlideDown.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
                view.visibility = View.VISIBLE
            }

        });
        view.startAnimation(animSlideDown)
    }

}