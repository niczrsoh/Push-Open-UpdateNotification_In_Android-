package com.example.pushopennotification

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivities
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val notifList : ArrayList<Notification>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position:Int)
    }

    fun setonItemClickListener(listener: onItemClickListener){
        mListener =listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_item,
            parent,false)

        return MyViewHolder(itemView, mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = notifList[position]

        holder.title.text = currentitem.title
        holder.body.text = currentitem.body

    }

    override fun getItemCount(): Int {

        return notifList.size
    }


    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.tvtitle)
        val body : TextView = itemView.findViewById(R.id.tvbody)
        init {
            itemView.setOnClickListener{
               listener.onItemClick(adapterPosition)
            }
        }



    }

}