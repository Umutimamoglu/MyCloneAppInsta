package com.hope.kotlininstaclone10.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hope.kotlininstaclone10.databinding.RecylerRowBinding
import com.hope.kotlininstaclone10.model.Post
import com.squareup.picasso.Picasso

class FeedRecylerAdapter(private val postList:ArrayList<Post>) : RecyclerView.Adapter<FeedRecylerAdapter.PostHolder>() {
    class PostHolder (val binding : RecylerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecylerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerEmailText.text = postList.get(position).email
        holder.binding.recyclerCommentText.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).dowloadUrl).into(holder.binding.recyclerImageView)

    }
}