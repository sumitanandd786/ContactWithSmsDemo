package com.contactwithsmsdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.interfaces.ISmsClickListener
import com.contactwithsmsdemo.model.Sms
import kotlinx.android.synthetic.main.sms_list_item.view.*
import java.text.SimpleDateFormat

class SendSmsAdapter(items : List<Sms>, ctx: Context, listner: ISmsClickListener) : RecyclerView.Adapter<SendSmsAdapter.ViewHolder>() {

    private var list = items
    private var context = ctx
    private var callBackListner = listner

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SendSmsAdapter.ViewHolder, position: Int) {
        holder.name.text = list[position]._personName
        holder.msg_date.text = list[position]._time
        holder.msg.text = list[position]._msg

        holder.linearlayout.setOnClickListener{
            callBackListner.itemOnClick(list[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendSmsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.sms_list_item,parent,false))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val profile = v.iv_profile!!
        val name = v.tv_name!!
        val msg_date = v.tv_message_date!!
        val msg = v.tv_otp_message!!
        val linearlayout = v.title_ll!!

        //val profile = v.iv_profile!!
    }

    fun changeDateFormat(currentFormat: String,
                         neededFormat: String, dateString: String): String {

        var outputDateStr = dateString
        try {
            val inputFormat = SimpleDateFormat(currentFormat)
            val outputFormat = SimpleDateFormat(neededFormat)
            val date = inputFormat.parse(dateString)
            outputDateStr = outputFormat.format(date)
        } catch (e: Exception) {

            e.printStackTrace()
        }

        return outputDateStr

    }
}