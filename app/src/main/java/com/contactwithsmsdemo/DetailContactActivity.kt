package com.contactwithsmsdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife

class DetailContactActivity : AppCompatActivity(),View.OnClickListener {


    internal lateinit var profileImage: ImageView
    internal lateinit var sendMessageButton: Button
    internal lateinit var personNameText: TextView
    internal lateinit var personNumberText: TextView
    lateinit var activity: Activity
    lateinit var profileName: String
    lateinit var profileNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this@DetailContactActivity
        setContentView(R.layout.activity_contact_detail)

        ButterKnife.bind(activity)
        init()
    }

    fun init() {

        profileImage = findViewById(R.id.iv_profile) as ImageView
        sendMessageButton = findViewById(R.id.btn_submit) as Button
        personNameText = findViewById(R.id.tv_name) as TextView
        personNumberText = findViewById(R.id.tv_number) as TextView

        if (intent != null) {
            profileName = intent.getStringExtra("NAME_KEY")
            profileNumber = intent.getStringExtra("NUMBER_KEY")
            personNameText!!.setText(profileName)
            personNumberText!!.setText(profileNumber)
        }

        sendMessageButton.setOnClickListener(this)
        }

    override fun onClick(v: View?) {
        val intent = Intent(activity, ComposeMessageActivity::class.java)
        intent.putExtra("NAME_KEY", profileName.toString());
        intent.putExtra("NUMBER_KEY", profileNumber.toString())
        var b = Bundle()
        intent.putExtras(b)
        startActivity(intent)
    }



}