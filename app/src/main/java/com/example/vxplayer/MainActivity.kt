package com.example.vxplayer

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vxplayer.databinding.ActivityMainBinding

const val REQUEST_CODE_FOR_WRITE = 17
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.coolNavTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        requestRuntimePermission()
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
    private fun requestRuntimePermission(): Boolean {
        if(ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_CODE_FOR_WRITE)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, deviceId: Int){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if(requestCode == REQUEST_CODE_FOR_WRITE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_CODE_FOR_WRITE)
        }
    }
}