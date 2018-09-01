package dev.kakueki61.kotlinexperiment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    var captureUri: Uri? = null

    val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databinding_button.setOnClickListener {
            startActivity(Intent(applicationContext, DataBindingActivity::class.java))
        }

        findViewById(R.id.camera_button).setOnClickListener {
            val capture = createCaptureFile()
            captureUri = FileProvider.getUriForFile(applicationContext, "dev.kakueki61.kotlinexperiment.fileprovider", capture)
            Log.i(TAG, "contentUri: $captureUri")

            grantUriPermission("com.google.android.GoogleCamera", captureUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri)
            intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            startActivityForResult(intent, 1000)
        }

        binding.button.setOnClickListener {
            startActivity(Intent(applicationContext, MotionLayoutActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG,"onActivityResult: $requestCode, $resultCode, $data")
        Log.i("", "data: ${data?.data}")
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createCaptureFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "port-medical_$timeStamp"

        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val capture = File.createTempFile(fileName, ".jpg", dir)
        return capture

    }

    private fun enableTransition() {
        // inside your activity (if you did not enable transitions in your theme)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        // set an exit transition
        window.exitTransition = Explode()
        window.sharedElementExitTransition = Explode()
        window.sharedElementExitTransition = Slide()
        window.sharedElementExitTransition = Fade()
    }
}
