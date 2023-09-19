package com.example.bitirmeprojesi.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.databinding.PhotoListItemBinding

class PhotoAdapter(private val context: Context, private val photos:List<Photo>, val onItemClick: (photo: Photo) -> Unit, val onItemLongClick: (photo: Photo) -> Unit):RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    class MyViewHolder(binding: PhotoListItemBinding):ViewHolder(binding.root){
        val ivPhoto = binding.ivPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PhotoListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
     return photos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(photos[position]){
            holder.ivPhoto.load(src.medium)

            holder.itemView.setOnLongClickListener {
                val animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_90)
                animation.fillAfter = true
                holder.ivPhoto.startAnimation(animation)
                onItemLongClick(this)
                return@setOnLongClickListener true
            }

            holder.itemView.setOnClickListener {
                onItemClick(this)
            }
        }
    }
}