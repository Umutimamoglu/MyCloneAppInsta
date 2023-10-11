package com.hope.kotlininstaclone10.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hope.kotlininstaclone10.R
import com.hope.kotlininstaclone10.adapter.FeedRecylerAdapter
import com.hope.kotlininstaclone10.databinding.ActivityFeeedBinding
import com.hope.kotlininstaclone10.model.Post

class FeeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var feedAdapter : FeedRecylerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<Post>()

        getData()

        binding.recyclerViewFeed.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecylerAdapter(postArrayList)
        binding.recyclerViewFeed.adapter = feedAdapter

    }

    private fun getData(){
        db.collection( "Posts").addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents
                        for(document in documents){

                            val comment = document.get("comment") as String // casting işlemi  as string
                            val userEmail = document.get("userEmail") as String
                            val dowloadUrl = document.get("dowloadUrl") as String

                            val post = Post(userEmail,comment,dowloadUrl)
                            postArrayList.add(post)



                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.instamenu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post) {
            val intent = Intent(this@FeeedActivity, uploadActiivity::class.java)
            startActivity(intent)

        } else if (item.itemId == R.id.sign_out) {
            auth.signOut()
            val intent = Intent(this@FeeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}