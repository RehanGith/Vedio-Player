package com.example.vxplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vxplayer.databinding.FolderViewBinding

class FolderAdapter(private val context: Context, private val folderList: ArrayList<String>):
    RecyclerView.Adapter<FolderAdapter.FolderHolder>() {
    class FolderHolder(binding: FolderViewBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.folderName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        return FolderHolder(FolderViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        holder.title.text = folderList[position]
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}