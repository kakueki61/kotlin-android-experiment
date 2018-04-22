package dev.kakueki61.kotlinexperiment.viewModels

import android.util.Log
import javax.inject.Inject

/**
 * Created by kodama-t on 2017/12/05.
 */
class NextViewModel @Inject constructor() {
    fun hoge() {
        Log.d("NextViewModel", "hogehoge")
    }
}