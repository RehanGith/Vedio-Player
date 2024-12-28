package com.example.vxplayer

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vxplayer.databinding.ActivityMainBinding

const val REQUEST_CODE_FOR_WRITE = 17
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.coolNavTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        requestRuntimePermission()
        setFragment(VideosFragment())
        toggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayour, R.string.open, R.string.close)
        binding.drawerLayour.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 13)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, deviceId: Int){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if(requestCode == 13) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE), 13)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            Toast.makeText(this, "You have selected option", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
