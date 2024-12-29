package com.example.vxplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vxplayer.databinding.FolderViewBinding

class FolderAdapter(private val context: Context, private var folderList: ArrayList<Folder>):
    RecyclerView.Adapter<FolderAdapter.FolderHolder>() {
    class FolderHolder(binding: FolderViewBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.folderName
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        return FolderHolder(FolderViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        holder.title.text = folderList[position].folder
        holder.root.setOnClickListener {
            val intent = Intent(context, FoldersActivity::class.java)
            intent.putExtra("position", position)
            ActivityCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}