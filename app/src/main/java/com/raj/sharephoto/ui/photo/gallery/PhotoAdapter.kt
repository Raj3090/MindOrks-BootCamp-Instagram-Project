package com.raj.sharephoto.ui.photo.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raj.sharephoto.databinding.PhotoGridItemBinding

class PhotoAdapter(val uris:ArrayList<String>) :RecyclerView.Adapter<PhotoItemViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        PhotoItemViewHolder(
            PhotoGridItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun getItemCount()=uris.size

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
           holder.bind(uris.get(position))
    }


    fun appendData(uris: List<String>) {
        val oldCount = itemCount
        this.uris.addAll(uris)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount > 0 && currentCount > oldCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }

}