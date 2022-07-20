package com.example.numbers.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.numbers.Modals.ModalNumbers
import com.example.numbers.R

class AdapterNumbers(val context: Context, private val num: ArrayList<ModalNumbers>):RecyclerView.Adapter<AdapterNumbers.HolderNums>() {
    class HolderNums(view: View) : RecyclerView.ViewHolder(view){
      val number:TextView=view.findViewById(R.id.txtView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNums {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_number, parent, false)
        return HolderNums(view)
    }

    override fun onBindViewHolder(holder: HolderNums, position: Int) {
         val nums=num[position]
         holder.number.text=nums.number.toString()
    }

    override fun getItemCount(): Int {
        return num.size
    }
}