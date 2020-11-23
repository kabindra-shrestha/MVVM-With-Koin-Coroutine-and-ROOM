package com.kabindra.sample.ui.main

import android.os.Bundle
import android.view.MenuItem
import com.kabindra.sample.R
import com.kabindra.sample.base.BaseActivity
import com.kabindra.sample.ui.user.UserFragment
import com.kabindra.sample.util.replaceFragment

class MainActivity(override val layoutResId: Int = R.layout.activity_main) :
    BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(findViewById(R.id.toolbar), "User")
        addUserFragment()
    }

    private fun addUserFragment() {
        /* Display First Fragment initially */
        replaceFragment(
            UserFragment(),
            R.id.fragment_container, "MainActivity", 0, ""
        )
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
