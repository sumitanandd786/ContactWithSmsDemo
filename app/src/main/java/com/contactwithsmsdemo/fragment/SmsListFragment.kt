package com.contactwithsmsdemo.fragment

import android.app.Activity
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
import android.content.ContentResolver
import android.net.Uri
import com.contactwithsmsdemo.model.Sms
import android.provider.Telephony
import android.os.Build
import android.content.Intent




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

    /*fun getAllSms(): List<Sms> {
        val lstSms = ArrayList<Sms>()
        var objSms = Sms()
        val message = Uri.parse("content://sms/")
        val cr = activity.getContentResolver()

        val c = cr.query(message, null, null, null, null)
        activity!!.startManagingCursor(c)
        val totalSMS = c!!.getCount()

        if (c!!.moveToFirst()) {
            for (i in 0 until totalSMS) {

                objSms = Sms()
                objSms.setId(c!!.getString(c!!.getColumnIndexOrThrow("_id")))
                objSms.setAddress(c!!.getString(c!!
                        .getColumnIndexOrThrow("address")))
                objSms.setMsg(c!!.getString(c!!.getColumnIndexOrThrow("body")))
                objSms.setReadState(c!!.getString(c!!.getColumnIndex("read")))
                objSms.setTime(c!!.getString(c!!.getColumnIndexOrThrow("date")))
                if (c!!.getString(c!!.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox")
                } else {
                    objSms.setFolderName("sent")
                }

                lstSms.add(objSms)
                c!!.moveToNext()
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c!!.close()

        return lstSms
    }*/

    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
    {
        final String myPackageName = getPackageName();
        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {

            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName);
            startActivityForResult(intent, 1);
        } else {
            List<Sms> lst = getAllSms ();
        }
    }else
    {
        List<Sms> lst = getAllSms ();
    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val myPackageName = activity.getPackageName()
                    if (Telephony.Sms.getDefaultSmsPackage(activity) == myPackageName) {

                        val lst = getAllSms()
                    }
                }
            }
        }
    }*/
}