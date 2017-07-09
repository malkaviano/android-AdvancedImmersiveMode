/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.advancedimmersivemode

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ViewAnimator

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * [android.support.v4.app.Fragment] which can display a view.
 *
 *
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
class MainActivity : FragmentActivity() {

    // Whether the Log Fragment is currently shown
    private var mLogShown: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = AdvancedImmersiveModeFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val logToggle = menu.findItem(R.id.menu_toggle_log)
        logToggle.isVisible = findViewById(R.id.sample_output) is ViewAnimator
        logToggle.setTitle(if (mLogShown) R.string.sample_hide_log else R.string.sample_show_log)

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_toggle_log -> {
                mLogShown = !mLogShown
                val output = findViewById(R.id.sample_output) as ViewAnimator
                if (mLogShown) {
                    output.displayedChild = 1
                } else {
                    output.displayedChild = 0
                }
                supportInvalidateOptionsMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    /** Create a chain of targets that will receive log data  */
    fun initializeLogging() {
        Log.i(TAG, "Ready")
    }

    companion object {

        val TAG = "MainActivity"
    }
}
