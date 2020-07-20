package com.lightricks.feedexercise.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lightricks.feedexercise.R

/**
 * This is the main entry point into the app. This activity shows the [FeedFragment].
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "Main"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        Log.d(TAG, "onCreate: set button")
    }

    private fun handleResponse(feedResponse: String) {
        Log.d(TAG, "handleResponse: " + feedResponse)
    }

    private fun handleNetworkError(error: String) {
        Log.d(TAG, "handleNetworkError: " + error)
    }
}