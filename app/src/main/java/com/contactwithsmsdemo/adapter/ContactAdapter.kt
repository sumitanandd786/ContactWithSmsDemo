package com.contactwithsmsdemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contactwithsmsdemo.model.ContactDTO
import com.contactwithsmsdemo.R
import com.contactwithsmsdemo.interfaces.IClickListener
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactAdapter(items : List<ContactDTO>, ctx: Context, listner: IClickListener) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var list = items
    private var context = ctx
    private var callBackListner = listner

    override fun getItemCount(): Int {
        return list.size
    }



    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.number.text = list[position].number
        holder.linearlayout.setOnClickListener{
            callBackListner.itemOnClick(list[position])
        }
        /*if(list[position].image != null)
            holder.profile.setImageBitmap(list[position].image)
        else
            holder.profile.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.ic_launcher_round))*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_list_item,parent,false))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val name = v.person_name_tv!!
        val number = v.person_number_tv!!
        val linearlayout = v.title_ll!!

        //val profile = v.iv_profile!!
    }


}