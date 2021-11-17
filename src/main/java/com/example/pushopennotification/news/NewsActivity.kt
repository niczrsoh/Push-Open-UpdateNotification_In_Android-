package com.example.pushopennotification.news

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pushopennotification.R
import com.google.firebase.database.*
import com.example.pushopennotification.ViewWebsite

class NewsActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var newsRecyclerview : RecyclerView
    private lateinit var newsArrayList : ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsRecyclerview = findViewById(R.id.newsList)
        val manager: LinearLayoutManager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true
        manager.reverseLayout = true
        newsRecyclerview.layoutManager = manager
        newsRecyclerview.setHasFixedSize(true)
        newsArrayList = arrayListOf<News>()
        getNewsData()

        val actionBar = supportActionBar
        actionBar!!.title = "My News"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


    }
    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }
    fun searchWeb(message : String){
        var i = Intent(this, ViewWebsite::class.java)
        i.putExtra("url",message)
        startActivity(i)
    }
    private fun getNewsData() {
        var fd: FirebaseDatabase = FirebaseDatabase.getInstance(" https://pushandopennotification-default-rtdb.asia-southeast1.firebasedatabase.app")
        dbref =
            fd.getReference().child("news")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    //remove all notification before any update
                    newsArrayList.removeAll(newsArrayList)
                    for (notificationSnapshot in snapshot.children) {
                        val notification = notificationSnapshot.getValue<News>(News::class.java)
                        // add all the notification to the list
                        newsArrayList.add(notification!!)
                    }
                        // add all the notification to the list

                    var adapter = NewsAdapter(newsArrayList)
                    newsRecyclerview.adapter = adapter
                    adapter.setonItemClickListener(object: NewsAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            searchWeb(newsArrayList[position].Url.toString())
                        }
                    })
            }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

        })

    }
}