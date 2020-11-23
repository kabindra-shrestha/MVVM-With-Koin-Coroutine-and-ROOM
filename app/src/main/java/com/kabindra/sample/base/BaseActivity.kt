package com.kabindra.sample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.kabindra.sample.util.TAG
import com.kabindra.sample.util.loggerDebug

abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        loggerDebug(TAG + "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
    }

    override fun onResume() {
        loggerDebug(TAG + "onResume")
        super.onResume()
    }

    override fun onPause() {
        loggerDebug(TAG + "onPause")
        super.onPause()
    }

    override fun onDestroy() {
        loggerDebug(TAG + "onDestroy")
        super.onDestroy()
    }

    /**
     * TODO setups toolbar
     */
    fun setupToolbar(toolbar: Toolbar, title: String) {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        // toolbar.setNavigationOnClickListener { onBackPressed() }

        setTitle(title)
    }

    /**
     * TODO add code to handle fragment within the activity
     */
}
