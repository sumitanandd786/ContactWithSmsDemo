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
import kotlinx.android.synthetic.main.activity_main.view.*

class ContactListFragment: Fragment() {

    @BindView(R.id.contact_tv)
    internal var contactView: TextView? = null

    private var activity: MainActivity? = null

    private var status: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        ButterKnife.bind(this, view)
        activity = getActivity() as MainActivity?
        if (arguments != null) {
            status = arguments!!.getString(Constant.TYPE)
        }

        return view
    }
}