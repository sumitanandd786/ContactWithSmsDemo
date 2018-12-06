package com.contactwithsmsdemo.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.contactwithsmsdemo.MainActivity
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.utility.Constant
import android.net.Uri
import com.contactwithsmsdemo.model.Sms
import android.provider.Telephony
import android.os.Build
import android.content.Intent
import android.database.Cursor
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.contactwithsmsdemo.adapter.SendSmsAdapter
import com.contactwithsmsdemo.interfaces.ISmsClickListener
import android.provider.ContactsContract
import android.Manifest.permission_group.SMS
import android.util.Log
import android.R.attr.phoneNumber
import android.Manifest.permission_group.SMS
import android.content.ContentResolver
import java.util.*


class SmsListFragment : Fragment(),ISmsClickListener {
    /*LoaderManager.LoaderCallbacks<Cursor>*/

    //@BindView(R.id.sms_tv)
    internal var smsList: RecyclerView? = null

    private var activity: MainActivity? = null

    private var status: String? = ""
    private var listner: ISmsClickListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = getActivity() as MainActivity?
        val view = inflater.inflate(R.layout.fragment_sms_list, container, false)
        ButterKnife.bind(this, view)
        smsList = view.findViewById(R.id.send_sms_list) as RecyclerView
        listner = this

        //listner = this
        if (arguments != null) {
            status = arguments!!.getString(Constant.TYPE)
        }
     //   activity!!.getSupportLoaderManager().initLoader(1, null, this);


            loadSMSList()


        return view
    }

   /* companion object {
        val TASK_LOADER = 0 //the loader id
        var adapter: SendSmsAdapter? = null
        var smsList: RecyclerView? = null
    }
*/
    override fun itemOnClick(position: Sms) {

    }

    fun loadSMSList(){
        smsList!!.layoutManager = LinearLayoutManager(activity)
        var lst : MutableList<Sms> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val myPackageName = activity!!.getPackageName()
            if (Telephony.Sms.getDefaultSmsPackage(activity) != myPackageName) {

                val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, myPackageName)
                startActivityForResult(intent, 1)

            } else {
               // val lst = getAllSms()
                lst = getAllSms() as MutableList<Sms>
                smsList!!.adapter = SendSmsAdapter(lst, this!!.activity!!,listner!!)
            }
        } else {
           // val lst = getAllSms()
            lst = getAllSms()as MutableList<Sms>
            smsList!!.adapter = SendSmsAdapter(lst, this!!.activity!!,listner!!)
        }
    }

    fun getAllSms(): List<Sms> {
        val lstSms = ArrayList<Sms>()
        var objSms = Sms()
        val message = Uri.parse("content://sms/sent")
        val cr = activity!!.getContentResolver()

        val c = cr.query(message, null, null, null, null)
       // activity!!.startManagingCursor(c)
        val totalSMS = c!!.getCount()
        while (c!=null && c.moveToNext()){
            objSms = Sms()
            objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")))
            objSms.setPersonName(c.getString(c.getColumnIndexOrThrow("_name")))
            objSms.setAddress(c.getString(c
                    .getColumnIndexOrThrow("address")))
            objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")))
            objSms.setReadState(c.getString(c.getColumnIndex("read")))
            objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")))
            if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                objSms.setFolderName("inbox")
            } else {
                objSms.setFolderName("sent")
            }
            objSms.setFolderName("sent")
            lstSms.add(objSms)

        }

       /* if (c!!.moveToFirst()) {
            for (i in 0 until totalSMS) {

                objSms = Sms()

                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")))
                objSms.setPersonName(c.getString(c.getColumnIndexOrThrow("_name")))
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")))
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")))
                objSms.setReadState(c.getString(c.getColumnIndex("read")))
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")))
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox")
                } else {
                    objSms.setFolderName("sent")
                }
                objSms.setFolderName("sent")

                lstSms.add(objSms)
                c.moveToNext()
            }
        }*/
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c!!.close()

        return lstSms
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val myPackageName = activity!!.getPackageName()
                    if (Telephony.Sms.getDefaultSmsPackage(activity) == myPackageName) {
                        var lst : MutableList<Sms> = ArrayList()
                        //val lst = getAllSms()
                        lst = getAllSms()as MutableList<Sms>
                        smsList!!.adapter = SendSmsAdapter(lst, this!!.activity!!,listner!!)
                    }
                }
            }
        }
    }

   /* override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        val SMS_ALL = "content://sms/sent"
        val uri = Uri.parse(SMS_ALL)
        val projection = arrayOf("_id", "_name", "address", "person", "body", "read", "date", "type")
        return CursorLoader(activity!!, uri, projection, null, null, "date desc")
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
        val sms_All : MutableList<Sms> = ArrayList<Sms>()
        val phoneNumbers = ArrayList<String>()
        while (p1!!.moveToNext()) {
            val phoneNumber = p1.getString(p1.getColumnIndexOrThrow("address"))
            //Log.i(phoneNumber)
            val type = p1.getInt(p1.getColumnIndexOrThrow("type"))

            if (!phoneNumbers.contains(phoneNumber) && type !== 3 && phoneNumber.length >= 1) {
                var name: String? = null
                val person = p1.getString(p1.getColumnIndexOrThrow("person"))
                val smsContent = p1.getString(p1.getColumnIndexOrThrow("body"))
                val date = Date(java.lang.Long.parseLong(p1.getString(p1.getColumnIndexOrThrow("date"))))
                val personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, phoneNumber)
                val cr = context.getContentResolver()
                val localCursor = cr.query(personUri,
                        arrayOf(ContactsContract.Contacts.DISPLAY_NAME), null, null, null)//use phonenumber find contact name
                if (localCursor!!.count != 0) {
                    localCursor.moveToFirst()
                    name = localCursor.getString(localCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                }
                //MyLog.v("person:$person  name:$name  phoneNumber:$phoneNumber")
                localCursor.close()
                phoneNumbers.add(phoneNumber)
                //val sms = SMS(name, phoneNumber, smsContent, type, date)
                val sms = SMS
                sms_All.add(sms)
            }
        }
        adapter!!.notifyDataSetChanged();

    }

    override fun onLoaderReset(p0: Loader<Cursor>) {

    }*/



}
