package dev.kakueki61.kotlinexperiment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.app.SharedElementCallback
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import dev.kakueki61.kotlinexperiment.adapters.ImageDetailPagerAdapter
import dev.kakueki61.kotlinexperiment.databinding.ActivityImageViewPagerBinding
import dev.kakueki61.kotlinexperiment.fragments.ImageDetailFragment

class ImageViewPagerActivity : AppCompatActivity() {

    companion object {
        fun intent(context: Context, imageUrls: Array<String>, position: Int): Intent {
            val intent = Intent(context, ImageViewPagerActivity::class.java)
            intent.putExtra("imageUrls", imageUrls)
            intent.putExtra("position", position)
            return intent
        }
    }

    private lateinit var binding: ActivityImageViewPagerBinding
    var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_view_pager)

        val imageUrls = intent.getStringArrayExtra("imageUrls") ?: return
        position = intent.getIntExtra("position", -1)
        binding.viewPager.adapter = ImageDetailPagerAdapter(supportFragmentManager, imageUrls.toList())
        binding.viewPager.currentItem = position
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(pos: Int) {
                Log.w("onPageSelected", "position: $pos")
                position = pos
            }
        })

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                Log.w(ImageViewPagerActivity::class.java.simpleName, "names: ${names.joinToString()}, sharedElements: ${sharedElements.entries.joinToString()}")
                val fragment = binding.viewPager.adapter?.instantiateItem(binding.viewPager, position) as ImageDetailFragment
                fragment.view?.findViewById<View>(R.id.image_view)?.let {
                    sharedElements.put(names[0], it)
                }
                Log.w(ImageViewPagerActivity::class.java.simpleName, "names: ${names.joinToString()}, sharedElements: ${sharedElements.entries.joinToString()}")
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent().apply { putExtra("current", position) }
        setResult(Activity.RESULT_OK, intent)
        supportFinishAfterTransition()
    }
}
