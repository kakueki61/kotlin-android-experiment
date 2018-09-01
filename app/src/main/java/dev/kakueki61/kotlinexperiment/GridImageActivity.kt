package dev.kakueki61.kotlinexperiment

import android.app.ActivityOptions
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import dev.kakueki61.kotlinexperiment.adapters.GridImageAdapter
import dev.kakueki61.kotlinexperiment.databinding.ActivitySharedElementTransitionBinding

class GridImageActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySharedElementTransitionBinding>(this, R.layout.activity_shared_element_transition) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.exitTransition = Explode()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = GridImageAdapter { v, p -> onItemClick(v, p) }
    }

    private fun onItemClick(view: View, position: Int) {
        startActivity(ImageDetailActivity.intent(this, GridImageAdapter.images[position]),
                ActivityOptions.makeSceneTransitionAnimation(this, view, "image").toBundle())
    }
}
