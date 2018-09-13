package dev.kakueki61.kotlinexperiment.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import dev.kakueki61.kotlinexperiment.fragments.ImageDetailFragment

/**
 * Created by kakueki61 on 2018/09/14.
 */
class ImageDetailPagerAdapter(fm: FragmentManager, private val imageUrls: List<String>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int) = ImageDetailFragment.newInstance(imageUrls[position])

    override fun getCount() = imageUrls.size

}