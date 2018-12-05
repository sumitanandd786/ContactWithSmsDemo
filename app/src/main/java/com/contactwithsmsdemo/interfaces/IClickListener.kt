package com.contactwithsmsdemo.interfaces

import com.contactwithsmsdemo.model.ContactDTO

interface IClickListener {
    fun itemOnClick(position: ContactDTO)
}