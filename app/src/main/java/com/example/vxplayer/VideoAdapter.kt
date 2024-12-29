package com.example.vxplayer

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vxplayer.databinding.VideoViewBinding

class VideoAdapter(private val context: Context, private val videoList: ArrayList<Video>):
    RecyclerView.Adapter<VideoAdapter.MyHolder>() {
    class MyHolder(binding: VideoViewBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvVideoName
        val folderName = binding.tvVideoLoc
        val duration = binding.VideoDuration
        val image = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(VideoViewBinding.inflate(LayoutInflater.from(context), parent, false)   )
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = videoList[position].title
        holder.folderName.text = videoList[position].folderName
        holder.duration.text = DateUtils.formatElapsedTime(videoList[position].duration/1000)
        Glide.with(context)
            .asBitmap()
            .load(videoList[position].artUri)
            .apply(RequestOptions().placeholder(R.mipmap.vedio_player_launcher).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

}