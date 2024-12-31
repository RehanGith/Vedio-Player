package com.example.vxplayer

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vxplayer.databinding.ActivityMainBinding
import java.io.File

const val REQUEST_CODE_FOR_READ = 17

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var permission: Array<String>
    companion object {
        lateinit var videoLIst: ArrayList<Video>
        lateinit var folderList: ArrayList<Folder>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(R.style.coolNavTheme)
        permission = if(Build.VERSION.SDK_INT >= 33) {
            arrayOf(READ_MEDIA_VIDEO, READ_MEDIA_IMAGES, READ_MEDIA_AUDIO)

        } else {
            arrayOf(READ_EXTERNAL_STORAGE)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (requestRuntimePermission()) {
            Toast.makeText(this, "Permission Granted: Main Activity", Toast.LENGTH_SHORT).show()
            folderList = ArrayList()
            videoLIst = getAllVideos()
            setFragment(VideosFragment())
        }
        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayour,
            R.string.open,
            R.string.close
        )
        binding.drawerLayour.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.buttonNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.allVideos -> setFragment(VideosFragment())
                R.id.allFolders -> setFragment(FoldersFragment())
            }
            return@setOnItemSelectedListener true
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.feedbackNav -> {
                    Toast.makeText(this@MainActivity, "Feedback selected", Toast.LENGTH_SHORT)
                        .show()
                }

                R.id.themesNav -> {
                    Toast.makeText(this@MainActivity, "Themes Selected", Toast.LENGTH_SHORT).show()
                }

                R.id.sortOrderNav -> {
                    Toast.makeText(this@MainActivity, "Sort Order selected", Toast.LENGTH_SHORT)
                        .show()
                }

                R.id.aboutNav -> {
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
        val check = checkPar()
        if (!check) {
            ActivityCompat.requestPermissions(
                this,
                permission,
                REQUEST_CODE_FOR_READ
            )
            Log.i("Permission", "requestRuntimePermission")
            return false
        }
        return true
    }
    private fun checkPar(): Boolean {
        Log.i("Permission", "checkPar")
        if(permission.size > 1) {
            val recVi = ActivityCompat.checkSelfPermission(this, READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
            val recIm = ActivityCompat.checkSelfPermission(this, READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
            val recAu = ActivityCompat.checkSelfPermission(this, READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
            Log.i("Permission", "checkPar: $recVi $recIm $recAu")
            return recIm && recVi && recAu
        }else {
            Log.i("Permission", "checkPar: 1")
            return ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }
    private fun checkResult(grantResults: IntArray): Boolean {
        if(grantResults.size >= 2) {
            return grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED
        } else {
            return grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        Log.i("Permission", "onRequestPermissionsResult")
        if (requestCode == REQUEST_CODE_FOR_READ) {
            Log.i("Permission", "onRequestPermissionsResult: request code")
            if (checkResult(grantResults))
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(
                    this,
                    permission,
                    REQUEST_CODE_FOR_READ
                )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("Recycle", "Range")
    private fun getAllVideos(): ArrayList<Video> {
        val tempList = ArrayList<Video>()
        val tempFolderList = ArrayList<String>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DURATION
        )
        val cursor = this.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null,
            MediaStore.Video.Media.DATE_ADDED + " DESC"
        )
        if (cursor != null)
            if (cursor.moveToNext())
                do {
                    val titleC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val durationC =
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                    val folderNameC =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME))
                        val folderIdC=  cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID))
                    val sizeC = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                    try {
                        val file = File(pathC)
                        val artUriC = Uri.fromFile(file)
                        val video = Video(
                            id = idC,
                            title = titleC,
                            duration = durationC,
                            folderName = folderNameC,
                            size = sizeC,
                            path = pathC,
                            artUri = artUriC
                        )
                        if (file.exists())
                            tempList.add(video)
                        // For adding folder
                        val folderD = Folder(id = folderIdC, folder = folderNameC)
                        if(!tempFolderList.contains(folderNameC)){
                            tempFolderList.add(folderNameC)
                            folderList.add(folderD)
                        }
                    } catch (_: Exception) {
                    }

                } while (cursor.moveToNext())
        cursor?.close()
        return tempList
    }
}
