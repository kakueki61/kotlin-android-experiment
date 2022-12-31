package dev.kakueki61.kotlinexperiment.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import dev.kakueki61.kotlinexperiment.databinding.FragmentImageDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ImageDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageDetailFragment : Fragment() {

    private var imageUrl: String? = null
    private lateinit var binding: FragmentImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            imageUrl = arguments!!.getString(ARG_IMAGE_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        binding.text.text = imageUrl
        binding.imageView.transitionName = imageUrl

        val picasso = Picasso.with(context)
        picasso.load(imageUrl).into(binding.imageView)
        return binding.root
    }

    companion object {
        private val ARG_IMAGE_URL = "imageUrl"

        fun newInstance(imageUrl: String): ImageDetailFragment {
            val fragment = ImageDetailFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, imageUrl)
            fragment.arguments = args
            return fragment
        }
    }

}
