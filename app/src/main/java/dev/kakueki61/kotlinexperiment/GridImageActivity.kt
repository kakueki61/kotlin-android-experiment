package dev.kakueki61.kotlinexperiment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.core.app.SharedElementCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.util.Log
import android.view.View
import dev.kakueki61.kotlinexperiment.adapters.GridImageAdapter
import dev.kakueki61.kotlinexperiment.databinding.ActivitySharedElementTransitionBinding

class GridImageActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySharedElementTransitionBinding>(this, R.layout.activity_shared_element_transition) }
    private val adapter by lazy { GridImageAdapter { v, p -> onItemClick(v, p) } }
    private var forViewPagerTransition: Boolean = false
    private var current = -1

    companion object {
        fun intent(context: Context, forViewPagerTransition: Boolean): Intent {
            return Intent(context, GridImageActivity::class.java).apply {
                putExtra("forViewPagerTransition", forViewPagerTransition)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.exitTransition = Explode()

        forViewPagerTransition = intent.getBooleanExtra("forViewPagerTransition", false)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = binding.recyclerView.width / 2
            adapter.setItemHeight(height)
        }

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                Log.w(GridImageActivity::class.java.simpleName, "names: ${names.joinToString()}, sharedElements: ${sharedElements.entries.joinToString()}")
                Log.w(GridImageActivity::class.java.simpleName, "current: $current")
                if (current != -1) {
                    val holder = binding.recyclerView.findViewHolderForAdapterPosition(current) as? GridImageAdapter.VH
                    holder?.binding?.imageView?.let {
                        Log.w(GridImageActivity::class.java.simpleName, "got it!!")
                        sharedElements.put(names[0], it)
                    }
                }
                Log.w(GridImageActivity::class.java.simpleName, "names: ${names.joinToString()}, sharedElements: ${sharedElements.entries.joinToString()}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 9999) {
            Log.w("onActivityResult", "data: $data")
            data?.let {
                current = it.getIntExtra("current", -1)
                Log.w("onActivityResult", "extra: ${it.getIntExtra("current", -1)}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onItemClick(view: View, position: Int) {
        if (forViewPagerTransition) {
            startActivityForResult(ImageViewPagerActivity.intent(this, GridImageAdapter.images.toTypedArray(), position), 9999,
                    ActivityOptions.makeSceneTransitionAnimation(this, view, GridImageAdapter.images[position]).toBundle())
        } else {
            startActivity(ImageDetailActivity.intent(this, GridImageAdapter.images[position]),
                    ActivityOptions.makeSceneTransitionAnimation(this, view, "image").toBundle())
        }
    }
}
