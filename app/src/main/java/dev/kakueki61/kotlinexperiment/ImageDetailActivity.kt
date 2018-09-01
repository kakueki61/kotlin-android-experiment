package dev.kakueki61.kotlinexperiment

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import com.squareup.picasso.Picasso
import dev.kakueki61.kotlinexperiment.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityImageDetailBinding>(this, R.layout.activity_image_detail) }

    companion object {
        fun intent(context: Context, url: String): Intent {
            return Intent(context, ImageDetailActivity::class.java).putExtra("url", url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra("url")
        binding.title = url

        Picasso.with(binding.imageView.context)
                .load(url)
                .into(binding.imageView)

        binding.imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.w("action Down", "y: ${event.getY()}")
                    v.setTag(event.getY())
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.w("action move", "y: ${event.getY()}")
                    Log.w("action move", "rawy: ${event.rawY}")
                    val hoge = event.rawY - (v.getTag() as Float)
                    v.translationY = hoge
                }
                MotionEvent.ACTION_UP -> {
                    Log.w("action up", "y: ${event.getY()}")
                    ObjectAnimator.ofFloat(v, "translationY", v.translationY, 0f)
                            .setDuration(1001)
                            .start()
                }
                else -> {
                }
            }
            false
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}
