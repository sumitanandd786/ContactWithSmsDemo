package com.contactwithsmsdemo.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.contactwithsmsdemo.DetailContactActivity
import com.contactwithsmsdemo.model.ContactDTO
import com.contactwithsmsdemo.MainActivity
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.adapter.ContactAdapter
import com.contactwithsmsdemo.interfaces.IClickListener
import com.contactwithsmsdemo.utility.Constant
import android.content.ContentValues
import android.content.ContentUris
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import com.contactwithsmsdemo.AddContactActivity


class ContactListFragment : Fragment(), IClickListener, View.OnClickListener {


    private var listner: IClickListener? = null
    //@BindView(R.id.contact_list)
    internal var contact_list: RecyclerView? = null

    private var activity: MainActivity? = null

    private var status: String? = ""

    private var addContact: FloatingActionButton? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = getActivity() as MainActivity?
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        ButterKnife.bind(this!!.activity!!, view)
        contact_list = view.findViewById(R.id.contact_list) as RecyclerView
        addContact = view.findViewById(R.id.add_contact_fb) as FloatingActionButton
        listner = this
        if (arguments != null) {
            status = arguments!!.getString(Constant.TYPE)
        }
        addContact!!.setOnClickListener(this)
        loadList()

        return view
    }

    fun loadList() {
        contact_list!!.layoutManager = LinearLayoutManager(activity)
        val contactList: MutableList<ContactDTO> = ArrayList()

        val contacts = getActivity()!!.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (contacts.moveToNext()) {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = ContactDTO()
            obj.name = name
            obj.number = number



            /*val photo_uri = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            if (photo_uri != null) {
                obj.image = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo_uri))
            }*/
            contactList.add(obj)

        }
        //val sortedList = contactList.sortedWith(compareBy { it.name })
        contact_list!!.adapter = ContactAdapter(contactList, this!!.activity!!, listner!!)

        //contact_list!!.adapter = ContactAdapter(sortedList, this!!.activity!!,listner!!)
        contacts.close()

    }

    override fun itemOnClick(model: ContactDTO) {
        activity?.let {
            val intent = Intent(it, DetailContactActivity::class.java)
            intent.putExtra("NAME_KEY", model.name);
            intent.putExtra("NUMBER_KEY", model.number)
            var b = Bundle()
            intent.putExtras(b)
            it.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        addContact()
    }

    fun addContact() {
        val intent = Intent(activity, AddContactActivity::class.java)
        startActivity(intent)
    }


}