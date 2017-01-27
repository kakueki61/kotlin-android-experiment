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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        findViewById(R.id.camera_button).setOnClickListener {
            val capture = createCaptureFile()
            val captureUri = FileProvider.getUriForFile(applicationContext, "jp.port_medical.tv.fileprovider", capture)
            Log.i(TAG, "contentUri: $captureUri")

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureUri)
            startActivityForResult(intent, 1000)
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
}
