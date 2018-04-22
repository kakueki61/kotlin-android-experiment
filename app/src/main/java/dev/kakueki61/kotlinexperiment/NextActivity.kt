package dev.kakueki61.kotlinexperiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import dev.kakueki61.kotlinexperiment.viewModels.NextViewModel
import kotlinx.android.synthetic.main.activity_next.*
import javax.inject.Inject

class NextActivity : AppCompatActivity() {

    @Inject lateinit var vm: NextViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(imageView)

        vm.hoge()
    }
}
