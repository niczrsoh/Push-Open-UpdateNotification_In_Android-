package com.example.pushopennotification

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class NotificationlistActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var notificationRecyclerview : RecyclerView
    private lateinit var notificationArrayList : ArrayList<Notification>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)

        notificationRecyclerview = findViewById(R.id.notificationList)
        val manager:LinearLayoutManager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        manager.stackFromEnd = true
        manager.reverseLayout = true
        notificationRecyclerview.layoutManager = manager
        notificationRecyclerview.setHasFixedSize(true)
        notificationArrayList = arrayListOf<Notification>()
        getnotificationData()

        val actionBar = supportActionBar
        actionBar!!.title = "My notification"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }
    fun searchbrowser(message : String){
        var i = Intent(Intent.ACTION_VIEW, Uri.parse(message))
        startActivity(i)
        finish()
    }
    private fun getnotificationData() {

        dbref = FirebaseDatabase.getInstance("https://pushandopennotification-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("notifications")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    //remove all notification before any update
                    notificationArrayList.removeAll(notificationArrayList)
                    for (notificationSnapshot in snapshot.children) {
                        val notification = notificationSnapshot.getValue(Notification::class.java)
                        // add all the notification to the list
                        notificationArrayList.add(notification!!)
                    }

                    var adapter = MyAdapter(notificationArrayList)
                    notificationRecyclerview.adapter = adapter
                    adapter.setonItemClickListener(object:MyAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                           val intent = Intent(this@NotificationlistActivity, Social::class.java)
                            startActivity(intent)
                            finish()
                        }


                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }


        })

    }
}
