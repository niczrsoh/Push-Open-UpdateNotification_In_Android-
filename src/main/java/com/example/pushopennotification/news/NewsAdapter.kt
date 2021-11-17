package com.example.pushopennotification.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pushopennotification.R
import com.example.pushopennotification.annoucement.AnnoucementAdapter
import com.squareup.picasso.Picasso


class NewsAdapter(private val newsList: ArrayList<News>) :RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position:Int)
    }

    fun setonItemClickListener(listener: onItemClickListener){
        mListener =listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.news_item,
            parent,false)

        return NewsViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val newsitem = newsList[position]
        holder.newsUrl.text = newsitem.Url
        Picasso.with(holder.newsImage.context)
            .load(newsitem.image)
            .resize(120,80)
            .into(holder.newsImage)
        holder.newsTitle.text = newsitem.title


    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView : View, listener: NewsAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val newsUrl :TextView = itemView.findViewById(R.id.tvUrl)
        val newsTitle : TextView = itemView.findViewById(R.id.tvNews_title)
        val newsImage : ImageView = itemView.findViewById<ImageView>(R.id.news_image)
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
    
    
    
}