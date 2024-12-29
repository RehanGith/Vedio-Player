package com.example.vxplayer

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vxplayer.databinding.ActivityMainBinding
import java.io.File

const val REQUEST_CODE_FOR_WRITE = 17
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.coolNavTheme)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if(requestRuntimePermission()) {
            Toast.makeText(this@MainActivity, "Read Permission granted", Toast.LENGTH_SHORT).show()
        }
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
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.feedbackNav -> {
                    Toast.makeText(this@MainActivity, "Feedback selected", Toast.LENGTH_SHORT).show() }
                R.id.themesNav -> {
                    Toast.makeText(this@MainActivity, "Themes Selected", Toast.LENGTH_SHORT).show()}
                R.id.sortOrderNav -> {
                    Toast.makeText(this@MainActivity, "Sort Order selected", Toast.LENGTH_SHORT).show()
                }
                R.id.aboutNav-> {
                    Toast.makeText(this@MainActivity, "About selected", Toast.LENGTH_SHORT).show()
                }
                R.id.exitNav -> {
                    Toast.makeText(this@MainActivity, "Exit selected", Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }
    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
    private fun requestRuntimePermission(): Boolean {
        if(ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), REQUEST_CODE_FOR_WRITE)
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
                ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), REQUEST_CODE_FOR_WRITE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    @SuppressLint("Recycle", "Range")
    private fun getAllVideos(): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val projection = arrayOf(MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION )
        val cursor = this.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null,
            MediaStore.Video.Media.DATE_ADDED + " DESC")
        if(cursor != null)
            if(cursor.moveToNext())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val folderNameC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = Video(id = idC, title = titleC, duration = durationC, folderName = folderNameC,
                            size = sizeC,path=  pathC, artUri = artUriC)
                        if(file.exists())
                            tempList.add(video)
                    } catch (_:Exception) { }

                }while (cursor.moveToNext())
                cursor?.close()
        return tempList
    }
}
