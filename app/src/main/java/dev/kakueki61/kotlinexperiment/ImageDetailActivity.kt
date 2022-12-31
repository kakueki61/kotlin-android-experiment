package dev.kakueki61.kotlinexperiment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.squareup.picasso.Picasso
import dev.kakueki61.kotlinexperiment.databinding.ActivityImageDetailBinding

class ImageDetailActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityImageDetailBinding>(this, R.layout.activity_image_detail) }
    private var targetView: View? = null

    companion object {
        val THRESHOLD = 300
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

        binding.scrollView.setOnTouchListener(onTouchListener)
        targetView = binding.root
    }

    private val onTouchListener = object : View.OnTouchListener {
        var available = false
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            targetView?.let {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.w("action Down", "y: ${event.y}, rawY: ${event.rawY}")
                        available = v.scrollY == 0
                        it.tag = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        Log.w("action move", "y: ${event.y}, rawY: ${event.rawY}, translationY: ${it.translationY}")
                        if (!available) return false

                        val translationY = event.rawY - (it.tag as Float)
                        if (event.rawY > it.tag as Float) {
                            it.translationY = translationY
                            val scale = translationY / THRESHOLD
                            it.scaleX = Math.max(1 - scale, 0.5.toFloat())
                            it.scaleY = Math.max(1 - scale, 0.5.toFloat())
                            it.alpha = Math.max(1 - scale, 0.5.toFloat())
                        } else {
                            return false
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        when {
                            !available -> return false
                            it.translationY > THRESHOLD -> supportFinishAfterTransition()
                            it.translationY > 0 -> AnimatorSet().apply {
                                playTogether(
                                        ObjectAnimator.ofFloat(it, "translationY", it.translationY, 0f).setDuration(1000),
                                        ObjectAnimator.ofFloat(it, "scaleX", it.scaleX, 1f).setDuration(1000),
                                        ObjectAnimator.ofFloat(it, "scaleY", it.scaleY, 1f).setDuration(1000),
                                        ObjectAnimator.ofFloat(it, "alpha", it.alpha, 1f).setDuration(1000)
                                )
                                start()
                            }
                            else -> return false
                        }
                    }
                    else -> {
                    }
                }
                return true
            }
            return false
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAfterTransition()"))
    override fun onBackPressed() {
        finishAfterTransition()
    }
}
