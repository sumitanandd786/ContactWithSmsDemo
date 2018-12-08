package com.contactwithsmsdemo.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.contactwithsmsdemo.MainActivity
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.utility.Constant
import com.contactwithsmsdemo.model.Sms
import android.support.v7.widget.RecyclerView
import com.contactwithsmsdemo.adapter.SendSmsAdapter
import com.contactwithsmsdemo.database.SMSDatabase
import com.contactwithsmsdemo.interfaces.ISmsClickListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class SmsListFragment : Fragment(), ISmsClickListener {

    internal var smsList: RecyclerView? = null

    private var activity: MainActivity? = null

    private var status: String? = ""
    private var listner: ISmsClickListener? = null
    private var smsListData: List<Sms>? = null
    var smsDatabase: SMSDatabase? = null
    //private var adapter: SendSmsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = getActivity() as MainActivity?
        val view = inflater.inflate(R.layout.fragment_sms_list, container, false)
        ButterKnife.bind(this, view)
        smsList = view.findViewById(R.id.send_sms_list) as RecyclerView
        listner = this
        smsDatabase = SMSDatabase.getInstance(activity)
        //listner = this
        if (arguments != null) {
            status = arguments!!.getString(Constant.TYPE)
        }

        postSmsData()!!.execute()
       // setDataAdapter(smsListData!!)

        return view
    }


    override fun itemOnClick(position: Sms) {

    }

    fun setDataAdapter(dataList: List<Sms>) {
        if (dataList != null) {
            if (dataList!!.size > 0) {
                smsList!!.layoutManager = LinearLayoutManager(activity)
                smsList!!.adapter = SendSmsAdapter(dataList!!, activity!!, listner!!)
                smsList!!.adapter!!.notifyDataSetChanged()
            }
        }
    }

    private inner class postSmsData : AsyncTask<Any, Any, List<Sms>>() {
        override fun doInBackground(vararg params: Any?): List<Sms>? {
            if (smsDatabase!!.daoAccess().sendSmsDataCount > 0) {
                val smsDbModelList = smsDatabase!!.daoAccess().smsDataList
                if (smsDbModelList.size > 0) {
                    setDataAdapter(smsDbModelList!!)
                }
            }
            return null
        }

        override fun onPostExecute(result: List<Sms>?) {
            super.onPostExecute(result)


        }

    }

    override fun onResume() {
        super.onResume()
        postSmsData()!!.execute()
    }

}



