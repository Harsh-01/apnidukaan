package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.personaldistributor.yourpersonaldistributor.models.Book
import com.personaldistributor.yourpersonaldistributor.models.Post

import com.squareup.picasso.Picasso

class PostAdapter(val context: Context, val itemList:ArrayList<Post> ) : RecyclerView.Adapter<PostAdapter.Postviewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Postviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_single_row,parent, false)
        return Postviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Postviewholder, position: Int) {

        val product = itemList[position]
        holder.nameP.text = product.name1
        holder.idP.text = product.id1
        holder.postP.text = product.posttext

    }

    class Postviewholder(view: View): RecyclerView.ViewHolder(view){
      var nameP : TextView = view.findViewById(R.id.shopkeeperName)
        var idP : TextView = view.findViewById(R.id.shopkeeperId)
        var postP : TextView = view.findViewById(R.id.postA)

    }
}


