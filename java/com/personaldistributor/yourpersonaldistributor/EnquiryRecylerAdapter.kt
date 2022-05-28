package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.style.TtsSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.personaldistributor.yourpersonaldistributor.models.Book

import com.squareup.picasso.Picasso
import java.util.logging.Handler

class EnquiryRecyclerAdapter(val context: Context, val itemList:ArrayList<Book> ) : RecyclerView.Adapter<EnquiryRecyclerAdapter.Enquiryviewholder>() {

//    lateinit var email:String
//    lateinit var subject:String
//    lateinit var body:String
//    lateinit var chooserTitle :String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Enquiryviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent, false)
        return Enquiryviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Enquiryviewholder, position: Int) {

        val product = itemList[position]
        holder.productName.text= product.productName
//        holder.productPrice.text= product.productPrice
//        holder.productRating.text= product.productRating
//        holder.productImage.setImageResource(product.productImage)

        Glide.with(context).load(product.productImage).into(holder.productImage);

        holder.productLocation.text = product.productLocation
        holder.productShop.text = product.productShops.toString()
        holder.productUnitsTime.text = product.productUnits.toString() + " Units\t- " + product.productTime
    }

    inner class Enquiryviewholder(view: View): RecyclerView.ViewHolder(view){
        val enquiryItem : CardView = view.findViewById(R.id.enquiryItem)
        val productName: TextView = view.findViewById(R.id.productName)
        val productUnitsTime: TextView = view.findViewById(R.id.productUnitsTime)
        val productShop : TextView = view.findViewById(R.id.productShop)
        val productLocation : TextView = view.findViewById(R.id.productLocation)
//        val productPrice:TextView = view.findViewById(R.id.productPrice)
//        val productRating:TextView = view.findViewById(R.id.productRating)
        val productImage:ImageView = view.findViewById(R.id.productImage)

        init {
//            enquiryItem.visibility = View.VISIBLE
            val position: Int = adapterPosition
            if(context is AgentLogin){
                enquiryItem.visibility = View.VISIBLE
//                if(itemList[position].productEnquiry){
////                    enquiryItem.visibility = View.VISIBLE
//                }else{
//                    Toast.makeText(view.context, "No data", Toast.LENGTH_SHORT).show()
//                }
            }else if(context is VendorLogin){
                enquiryItem.visibility = View.VISIBLE
//                if(itemList[position].productEnquiry){
////                    enquiryItem.visibility = View.VISIBLE
//                }else{
//                    Toast.makeText(view.context, "No data", Toast.LENGTH_SHORT).show()
//                }
            }
//            view.setOnClickListener{ v: View ->
//                val position : Int = adapterPosition
//                Toast.makeText(view.context, "Interested for ${itemList[position].productName} \n Enquiry sent to ${itemList[position].productCompany} \n Please wait for approval",Toast.LENGTH_LONG).show()
//            }
        }

    }
}
