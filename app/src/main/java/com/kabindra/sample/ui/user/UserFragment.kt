package com.kabindra.sample.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kabindra.sample.R
import com.kabindra.sample.adapter.UserRecyclerViewAdapter
import com.kabindra.sample.base.BaseActivity
import com.kabindra.sample.base.initRecycler
import com.kabindra.sample.db.model.UserData
import com.kabindra.sample.util.TAG
import com.kabindra.sample.util.loggerDebug
import com.kabindra.sample.util.replaceFragment
import com.kabindra.sample.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {

    private val userViewModel by viewModel<UserViewModel>()
    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mRootView = inflater.inflate(
            R.layout.fragment_user, container, false
        )
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // removeBackButton()
        userViewModel.getUser()
        userViewModel.userList.observe(viewLifecycleOwner, Observer {
            loggerDebug("$TAG@@user ${it.size}")
            if (it.isNotEmpty() && it != null) {
                setView(it)
            }
        })

        userViewModel.showError.observe(viewLifecycleOwner, Observer {
            loggerDebug("$TAG@@showError $it")
            if (it != null && it.isNotEmpty()) {
                rv_user.visibility = View.GONE
                error.visibility = View.VISIBLE

                error.text = it
            } else {
                rv_user.visibility = View.VISIBLE
                error.visibility = View.GONE
            }
        })

        userViewModel.showLoading.observe(viewLifecycleOwner, Observer {
            loggerDebug("$TAG@@showLoading $it")
            if (it) {
                progressbar.visibility = View.VISIBLE
            } else {
                progressbar.visibility = View.GONE
            }
        })

    }

    private fun removeBackButton() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(false)
    }

    private fun setView(list: List<UserData>) {
        userRecyclerViewAdapter = UserRecyclerViewAdapter(context) { userData: UserData ->
            (activity as BaseActivity).replaceFragment(
                UserDetailsFragment.newInstance(userData),
                R.id.fragment_container, "userdetails", R.id.user_name, "transitionName"
            )
        }
        userRecyclerViewAdapter.items = list
        // userAdapter.setUser(list)
        rv_user.apply {
            initRecycler(context, false, 0, spacing = 0)
            adapter = userRecyclerViewAdapter
        }
    }
}
