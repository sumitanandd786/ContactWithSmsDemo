package com.contactwithsmsdemo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife

class DetailContactActivity : AppCompatActivity() {

    internal lateinit var profileImage: ImageView
    internal lateinit var sendMessageImage: ImageView
    internal lateinit var personNameText: TextView
    internal lateinit var personNumberText: TextView
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)
        init()
    }

    fun init(){
        activity = this@DetailContactActivity
        ButterKnife.bind(activity)
        profileImage = findViewById(R.id.iv_profile) as ImageView
        sendMessageImage = findViewById(R.id.iv_message) as ImageView
        personNameText = findViewById(R.id.tv_name) as TextView
        personNumberText = findViewById(R.id.tv_number) as TextView

        if(intent!=null){
            val profileName=intent.getStringExtra("NAME_KEY")
            val profileNumber=intent.getStringExtra("NUMBER_KEY")
            personNameText!!.setText(profileName)
            personNumberText!!.setText(profileNumber)
        }

    }
}