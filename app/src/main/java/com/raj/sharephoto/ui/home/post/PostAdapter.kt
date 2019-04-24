package com.raj.sharephoto.ui.home.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.data.model.Post
import com.raj.sharephoto.databinding.ItemViewPostBinding

class PostAdapter(private val post:ArrayList<Post>):RecyclerView.Adapter<PostItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder =
        PostItemViewHolder(
            ItemViewPostBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    fun appendData(posts: List<Post>) {
        val oldCount = itemCount
        this.post.addAll(posts)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount > 0 && currentCount > oldCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }


    override fun getItemCount(): Int {
      return post.size
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.bind(post[position])
    }

}