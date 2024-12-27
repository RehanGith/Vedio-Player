package com.example.vxplayer

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vxplayer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.coolNavTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setFragment(VideosFragment())
        binding.buttonNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.allVideos -> setFragment(VideosFragment())
                R.id.allFolders -> setFragment(FoldersFragment())
            }
            return@setOnItemSelectedListener true
        }
    }
    private fun setFragment(fragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

}