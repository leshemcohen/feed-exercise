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
    private val tmp = "bla2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        Log.d(TAG, "onCreate: set button")
    }

}