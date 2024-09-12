package com.example.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, list: MutableList<Person>):
    ArrayAdapter<Person>(context, R.layout.list_item, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) {
            view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_item, parent, false)
        }

        val nameItemList = view?.findViewById<TextView>(R.id.nameItemList)
        val surnameItemList = view?.findViewById<TextView>(R.id.surnameItemList)
        val ageItemList = view?.findViewById<TextView>(R.id.ageItemList)
        val postItemList = view?.findViewById<TextView>(R.id.postItemList)

        nameItemList?.text = person?.name
        surnameItemList?.text = person?.surname
        ageItemList?.text = person?.age
        postItemList?.text = person?.post
        return view!!
    }
}