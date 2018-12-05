package com.contactwithsmsdemo.adapter

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.contactwithsmsdemo.fragment.ContactListFragment
import com.contactwithsmsdemo.fragment.SmsListFragment
import com.contactwithsmsdemo.utility.Constant
import java.util.ArrayList

/*(fm: FragmentManager, private var tabCount: Int, private val context: Context) :
FragmentPagerAdapter(fm)*/

class TabPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    //private val mFragmentCounterList = ArrayList<Int>()
    //private var counter: Int? = 0

    override fun getItem(position: Int): Fragment? {
        val bundle = Bundle()
        bundle.putString(Constant.TYPE, mFragmentTitleList[position])
        val fragment = mFragmentList[position]
        fragment.arguments = bundle
        return fragment
    }

    fun getFragmentList(): ArrayList<Fragment> {
        return mFragmentList
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
        //mFragmentCounterList.add(0)
    }

    fun setFragmentTitleCount(position: Int, count: Int) {
        /*if (position > -1 && position < mFragmentTitleList.size && mFragmentCounterList.size > position) {
            mFragmentCounterList.set(position, count)
            notifyDataSetChanged();
        }*/
        if (position > -1 && position < mFragmentTitleList.size) {
            //mFragmentCounterList.set(position, count)
            notifyDataSetChanged();
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
        //+ " (" + mFragmentCounterList[position] + ")"
    }


    override fun getCount(): Int {
        return return mFragmentList.size
    }

    override fun saveState(): Parcelable? {
        return null
    }

}