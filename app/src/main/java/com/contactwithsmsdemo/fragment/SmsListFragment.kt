package com.contactwithsmsdemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.contactwithsmsdemo.MainActivity
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.utility.Constant

class SmsListFragment:Fragment() {

    @BindView(R.id.sms_tv)
    internal var smsView: TextView? = null

    private var activity: MainActivity? = null

    private var status: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sms_list, container, false)
        ButterKnife.bind(this, view)
        activity = getActivity() as MainActivity?
        if (arguments != null) {
            status = arguments!!.getString(Constant.TYPE)
        }

        return view
    }

}