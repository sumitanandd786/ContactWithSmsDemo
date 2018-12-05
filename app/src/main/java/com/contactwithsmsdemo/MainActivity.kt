package com.contactwithsmsdemo

import android.Manifest
import android.app.Activity
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.contactwithsmsdemo.adapter.TabPagerAdapter
import com.contactwithsmsdemo.fragment.ContactListFragment
import com.contactwithsmsdemo.fragment.SmsListFragment
import android.Manifest.permission
import android.Manifest.permission.READ_CONTACTS
import android.os.Build
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.util.Log
import bolts.Continuation
import bolts.Task
import com.contactwithsmsdemo.utility.phonebook.Contact
import com.contactwithsmsdemo.utility.phonebook.Contacts
import com.contactwithsmsdemo.utility.phonebook.Contacts.getQuery
import com.contactwithsmsdemo.utility.phonebook.Query
import com.google.gson.GsonBuilder
import java.util.concurrent.Callable


class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: TabPagerAdapter? = null

    //@BindView(R.id.toolbar)
    private lateinit var toolbar: Toolbar

    //@BindView(R.id.tabs)
    private lateinit var tabLayout: TabLayout

    // @BindView(R.id.container)
    private lateinit var mViewPager: ViewPager

    lateinit var activity: Activity
    var contactFragment = ContactListFragment()
    var smsFragment = SmsListFragment()
    private val READ_CONTACT_PERMISSION_REQUEST_CODE = 76
    private val TAG = MainActivity::class.java!!.getSimpleName()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        initLayout()
    }

    fun initLayout() {
        activity = this@MainActivity
        ButterKnife.bind(activity)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        tabLayout = findViewById(R.id.tabs) as TabLayout
        mViewPager = findViewById(R.id.container) as ViewPager
        setSupportActionBar(toolbar)
        //  toolbar!!.setTitle(getString(R.string.app_name))

        if (mViewPager != null) {
            mViewPager!!.setCurrentItem(0)
        }

        mSectionsPagerAdapter = TabPagerAdapter(supportFragmentManager)
        mSectionsPagerAdapter!!.addFragment(contactFragment, getString(R.string.contact_list))
        mSectionsPagerAdapter!!.addFragment(smsFragment, getString(R.string.sms_list))


        mViewPager!!.setAdapter(mSectionsPagerAdapter)
        mViewPager!!.setOffscreenPageLimit(mSectionsPagerAdapter!!.count)
        tabLayout!!.setupWithViewPager(mViewPager, true)

        setupTabStyle()
    }

    private fun setupTabStyle() {
        for (i in 0 until tabLayout!!.getTabCount()) {

            val tab = tabLayout!!.getTabAt(i)
            if (tab != null) {

                val tabTextView = TextView(this)
                tabTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
                tab.customView = tabTextView

                tabTextView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                tabTextView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

                tabTextView.text = tab.text

                if (i == 0) {
                    tabTextView.setTypeface(null, Typeface.BOLD)
                }

            }

        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                mViewPager!!.setCurrentItem(tab.position)

                val text = tab.customView as TextView?

                if (text != null) {
                    text.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                    text.setTypeface(null, Typeface.BOLD)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val text = tab.customView as TextView?

                if (text != null) {
                    text.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                    text.setTypeface(null, Typeface.NORMAL)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            queryContacts()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                        READ_CONTACT_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == READ_CONTACT_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            queryContacts()
        }
    }

    private fun queryContacts() {
        Task.callInBackground(object : Callable<Void> {
            @Throws(Exception::class)
            override fun call(): Void? {
                val q = Contacts.getQuery()
                q.include(Contact.Field.ContactId, Contact.Field.DisplayName, Contact.Field.PhoneNumber, Contact.Field.PhoneNormalizedNumber, Contact.Field.Email)
                val q1 = Contacts.getQuery()
                q1.whereEqualTo(Contact.Field.DisplayName, "Sumit Anand")
                q1.hasPhoneNumber()

                val q2 = Contacts.getQuery()
                q2.whereStartsWith(Contact.Field.ContactId, "791")
                q2.hasPhoneNumber()
                val queries = ArrayList<Query>()
                queries.add(q1)
                queries.add(q2)
                q.or(queries)

                val contacts = q.find()
                Log.e(TAG, GsonBuilder().setPrettyPrinting().create().toJson(contacts))
                return null
            }
        }).continueWith(object : Continuation<Void, Void> {
            @Throws(Exception::class)
            override fun then(task: Task<Void>): Void? {
                if (task.isFaulted()) {
                    Log.e(TAG, "find failed", task.getError())
                }
                return null
            }
        })
    }
}
