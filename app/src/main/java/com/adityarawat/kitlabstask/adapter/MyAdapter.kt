package com.adityarawat.kitlabstask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adityarawat.kitlabstask.R
import com.adityarawat.kitlabstask.model.FamilyItem
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val itemList : ArrayList<FamilyItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image : ShapeableImageView = itemView.findViewById(R.id.photoiv)
        val position : TextView = itemView.findViewById(R.id.position)
        val phone : TextView = itemView.findViewById(R.id.phoneno)
        val status : TextView = itemView.findViewById(R.id.choice)
        val gender : TextView = itemView.findViewById(R.id.gender)

        val cardgen : CardView = itemView.findViewById(R.id.cardviewgender)
        val cardstatus : CardView = itemView.findViewById(R.id.cardviewstatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.image.setImageResource(currentItem.image)
        holder.position.text = currentItem.position
        holder.gender.text = currentItem.gender
        holder.phone.text = currentItem.phone
        holder.status.text = currentItem.status

        // Set text color based on gender
        if (currentItem.gender.equals("male", ignoreCase = true)) {
            holder.cardgen.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue)) // Assuming you have defined blue color in your resources
        } else if (currentItem.gender.equals("female", ignoreCase = true)) {
            holder.cardgen.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.pink)) // Assuming you have defined pink color in your resources
        }

        if (currentItem.status.equals("Accepted", ignoreCase = true)) {
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dgreen))
            holder.cardstatus.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green)) // Assuming you have defined blue color in your resources
        } else if (currentItem.status.equals("InProgress", ignoreCase = true)) {
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dorange))
            holder.cardstatus.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.orange))
        } else if (currentItem.status.equals("Closed", ignoreCase = true)) {
            holder.cardstatus.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
            holder.status.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dred))
        }

        holder.gender.text = currentItem.gender
    }

}