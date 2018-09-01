package dev.kakueki61.kotlinexperiment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import dev.kakueki61.kotlinexperiment.databinding.ActivityDataBindingBinding

class DataBindingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding)

        val binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(this, R.layout.activity_data_binding)

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.inflateMenu(R.menu.data_binding)
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_back)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setOnMenuItemClickListener {
            Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show()
            true
        }
        binding.toolbar.menu.findItem(R.id.one).isVisible = false
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        Log.i(this.javaClass.simpleName, "onCreateOptionsMenu")
//        menuInflater.inflate(R.menu.data_binding_second, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.i(this.javaClass.simpleName, "onOptionsItemSelected")
        return super.onOptionsItemSelected(item)
    }
}
