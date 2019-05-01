package com.raj.sharephoto.ui.profile.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.data.model.UploadedPostData
import com.raj.sharephoto.databinding.PhotoGridItemBinding
import com.raj.sharephoto.databinding.PostGridItemBinding

class MyPostAdapter(val urls:ArrayList<UploadedPostData>) :RecyclerView.Adapter<MyPostItemViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        MyPostItemViewHolder(
            PostGridItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun getItemCount()=urls.size

    override fun onBindViewHolder(holder: MyPostItemViewHolder, position: Int) {
           holder.bind(urls.get(position))
    }


    fun appendData(uris: List<UploadedPostData>) {
        val oldCount = itemCount
        this.urls.addAll(uris)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount > 0 && currentCount > oldCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }

}