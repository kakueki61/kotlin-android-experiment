package dev.kakueki61.kotlinexperiment.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dev.kakueki61.kotlinexperiment.R
import dev.kakueki61.kotlinexperiment.databinding.LayoutGridImageItemBinding

/**
 * Created by kakueki61 on 2018/08/26.
 */
class GridImageAdapter(private val onItemClick: (view: View, position: Int) -> Unit) : RecyclerView.Adapter<GridImageAdapter.VH>() {
    companion object {
        val images = listOf(
                "http://placehold.jp/24/cc9999/993333/150x100.png",
                "http://placehold.jp/24/cc9999/993333/150x150.png",
                "http://placehold.jp/24/cc9999/993333/250x200.png",
                "http://placehold.jp/24/cc9999/993333/350x300.png",
                "http://placehold.jp/24/cc9999/993333/450x400.png",
                "http://placehold.jp/24/cc9999/993333/250x250.png",
                "http://placehold.jp/24/cc9999/993333/350x350.png",
                "http://placehold.jp/24/cc9999/993333/250x100.png",
                "http://placehold.jp/24/cc9999/993333/350x100.png",
                "http://placehold.jp/24/cc9999/993333/450x100.png")
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Picasso.with(holder.binding.imageView.context)
                .load(images[position])
                .into(holder.binding.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_grid_image_item, parent, false)
        return VH(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class VH(view: View, onItemClick: (view: View, position: Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val binding: LayoutGridImageItemBinding = DataBindingUtil.bind(view)

        init {
            binding.root.apply {
                setOnClickListener { onItemClick(this, adapterPosition) }
            }
        }
    }
}