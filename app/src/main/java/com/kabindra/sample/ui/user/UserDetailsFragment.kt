package com.kabindra.sample.ui.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kabindra.sample.R
import com.kabindra.sample.db.model.UserData
import com.kabindra.sample.util.TAG
import com.kabindra.sample.util.loggerDebug
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(data: UserData) = UserDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("user_data_row", data)
            }
        }
    }

    private var user: UserData? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments?.getParcelable("user_data_row")
        loggerDebug(TAG + user?.name.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mRootView = inflater.inflate(
            R.layout.fragment_user_details, container, false
        )
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        enableBackButton()
        user_name.text = user!!.name!!.official
        var currency: String = ""
        user?.currency?.forEach {
            currency = "$it "
        }
        user_currency.text = currency

        user_region.text = user?.region
        user_subregion.text = user?.subregion
        user_language.text = user?.nativeLanguage
        user_captial.text = user?.capital
    }

    private fun enableBackButton() {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(true)
    }

}