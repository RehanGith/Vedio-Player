package com.example.vxplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.vxplayer.databinding.ActivityFoldersBinding

class FoldersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolNavTheme)
        enableEdgeToEdge()
        val binding = ActivityFoldersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val position = intent.getIntExtra("position", 0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title  = MainActivity.folderList[position].folder
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}