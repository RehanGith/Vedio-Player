package com.example.vxplayer

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vxplayer.databinding.ActivityFoldersBinding

class FoldersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolNavTheme)
        enableEdgeToEdge()
        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val position = intent.getIntExtra("position", 0)
        val tempList = ArrayList<Video>()
        tempList.add(MainActivity.videoLIst[0])
        tempList.add(MainActivity.videoLIst[1])
        tempList.add(MainActivity.videoLIst[2])
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title  = MainActivity.folderList[position].folder
        binding.rvVideoView.setItemViewCacheSize(10)
        binding.rvVideoView.setHasFixedSize(true)
        binding.rvVideoView.layoutManager = LinearLayoutManager(this@FoldersActivity)
        binding.rvVideoView.adapter = VideoAdapter(this@FoldersActivity, tempList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}