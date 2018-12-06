package com.contactwithsmsdemo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.*
import butterknife.ButterKnife
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.contactwithsmsdemo.utility.Utility
import java.util.*
import android.content.IntentFilter
import android.widget.Toast
import android.content.BroadcastReceiver
import android.app.PendingIntent
import android.content.Context
import com.contactwithsmsdemo.database.SMSDatabase


class ComposeMessageActivity : AppCompatActivity(), View.OnClickListener {


    internal lateinit var profileImage: ImageView
    internal lateinit var sendMessageImage: ImageView
    internal lateinit var personNameText: TextView
    internal lateinit var personNumberText: TextView
    internal lateinit var msgText: EditText
    internal lateinit var sendSmsButton: Button
    lateinit var activity: Activity
    private val PERMISSION_SEND_SMS = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this@ComposeMessageActivity
        setContentView(R.layout.activity_compose_sms)
        ButterKnife.bind(activity)
        init()
    }

    fun init() {
        profileImage = findViewById(R.id.iv_profile) as ImageView
        msgText = findViewById(R.id.et_text_msg) as EditText
        sendSmsButton = findViewById(R.id.btn_submit) as Button
        personNameText = findViewById(R.id.tv_name) as TextView
        personNumberText = findViewById(R.id.tv_number) as TextView

        if (intent != null) {
            val profileName = intent.getStringExtra("NAME_KEY")
            val profileNumber = intent.getStringExtra("NUMBER_KEY")
            personNameText!!.setText(profileName)
            personNumberText!!.setText(profileNumber)
        }
        msgText!!.setText("Hi, your OTP is: "+GenerateRandomNumber())
        //val smsDatabase = SMSDatabase.getInstance(activity)


        sendSmsButton.setOnClickListener(this)

    }

    fun GenerateRandomNumber(): String {
        return (if (6 < 1)
            0
        else
            Random().nextInt(9 * Math.pow(10.0, (6 - 1).toDouble()).toInt() - 1) + Math.pow(10.0, (6 - 1).toDouble()).toInt()).toString()
    }


    override fun onClick(v: View?) {
        requestSmsPermission()
    }

    fun requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.SEND_SMS),
                    PERMISSION_SEND_SMS)
        } else {
            // permission already granted run sms send
            if (!TextUtils.isEmpty(msgText.text.toString())) {
                sendSms(personNumberText.text.toString(), msgText.text.toString())
                finish()
            } else {
                Toast.makeText(activity, "Please enter message", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_SEND_SMS -> {

                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    if (!TextUtils.isEmpty(msgText.text.toString())) {
                        sendSms(personNumberText.text.toString(), msgText.text.toString())
                        finish()
                    } else {
                        Toast.makeText(activity, "Please enter message", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // permission denied
                }
                return
            }
        }
    }


    fun sendSms(phoneNumber: String, message: String) {
        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"

        val sentPI = PendingIntent.getBroadcast(this, 0,
                Intent(SENT), 0)

        val deliveredPI = PendingIntent.getBroadcast(this, 0,
                Intent(DELIVERED), 0)

        //---when the SMS has been sent---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS sent",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(baseContext, "Generic failure",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(baseContext, "No service",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(baseContext, "Null PDU",
                            Toast.LENGTH_SHORT).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(baseContext, "Radio off",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }, IntentFilter(SENT))

        //---when the SMS has been delivered---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS delivered",
                            Toast.LENGTH_SHORT).show()
                    Activity.RESULT_CANCELED -> Toast.makeText(baseContext, "SMS not delivered",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }, IntentFilter(DELIVERED))

        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, null, null)

    }


}


