package com.contactwithsmsdemo.interfaces

import com.contactwithsmsdemo.model.Sms

interface ISmsClickListener {
    fun itemOnClick(position: Sms)

}