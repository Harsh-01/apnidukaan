package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.personaldistributor.yourpersonaldistributor.models.Book

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_first.view.textView
import kotlinx.android.synthetic.main.fragment_first_v.view.*
import java.util.logging.Handler
import java.util.zip.Inflater

class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Book> ) : RecyclerView.Adapter<DashboardRecyclerAdapter.Dashboardviewholder>() {

//    lateinit var email:String
//    lateinit var subject:String
//    lateinit var body:String
//    lateinit var chooserTitle :String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Dashboardviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row2,parent, false)
        return Dashboardviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Dashboardviewholder, position: Int) {

        val product = itemList[position]
        holder.productName.text= product.productName
        holder.productCompany.text= product.productCompany
        holder.margin1.text= product.productMargin.toString()
        holder.demo1.text = product.productDemo.toString()
        holder.msp1.text = product.productMsp.toString()

//        holder.product2?.text = product.productName
//        holder.productPrice.text= product.productPrice
//        holder.productRating.text= product.productRating
//        holder.productImage.setImageResource(product.productImage)

        Glide.with(context).load(product.productImage).into(holder.productImage);




    }

    inner class Dashboardviewholder(view: View): RecyclerView.ViewHolder(view){
        val productName: TextView = view.findViewById(R.id.productName)
        val productCompany: TextView = view.findViewById(R.id.productCompany)
//        val productPrice:TextView = view.findViewById(R.id.productPrice)
//        val productRating:TextView = view.findViewById(R.id.productRating)
        val productImage:ImageView = view.findViewById(R.id.productImage)
        val tick:ImageView = view.findViewById(R.id.tick)
        val pImage = view.findViewById<RelativeLayout>(R.id.pImage)
        val margin1:TextView = view.findViewById(R.id.margin1) //defined TextView so no need to define the layout
        val demo1:TextView = view.findViewById(R.id.demo1)
        val msp1:TextView= view.findViewById(R.id.msp1)
//        val interested:TextView = view.findViewById(R.id.interested)


        init {

            if(context is VendorLogin) {
                view.setOnClickListener { v: View ->
                    val position: Int = adapterPosition
                    val builder = AlertDialog.Builder(context)

                    val dialogLayout =
                        LayoutInflater.from(context).inflate(R.layout.edit_text_layout2, null)
                    val registerSpinner = dialogLayout.findViewById<Spinner>(R.id.spinner3)
                    val timeEnquiry = dialogLayout.resources.getStringArray(R.array.TimeEnquiry)
                    val unitsV = dialogLayout.findViewById<EditText>(R.id.units)
                    val location = dialogLayout.findViewById<EditText>(R.id.location)
                    var time:String = ""
//                    val product2:TextView? = view.findViewById(R.id.product2)
//                    product2?.text = itemList[position].productName
                    if(registerSpinner != null){
                        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, timeEnquiry)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        registerSpinner.adapter = adapter

                        registerSpinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                time = timeEnquiry[position]
                            }
                        }

                    }


                    with(builder) {
                        setTitle("Interested for ${itemList[position].productName}")
                        setPositiveButton("Send Enquiry") { dialog, which ->
                            Toast.makeText(
                                view.context,
                                "Enquiry sent to ${itemList[position].productCompany} \n Please wait for approval",
                                Toast.LENGTH_LONG
                            ).show()
                            tick.visibility = View.VISIBLE
                            itemList[position].productEnquiry = true
                            itemList[position].productUnits = unitsV.text.toString().toInt()
                            itemList[position].productTime = time
                            itemList[position].productLocation = location.text.toString()

                        }
                        setNeutralButton("Cancel"){dialog, which ->

                        }
                        setView(dialogLayout)
                        show()

                    }

//                Toast.makeText(view.context, "Interested for ${itemList[position].productName} \n Enquiry sent to ${itemList[position].productCompany} \n Please wait for approval",Toast.LENGTH_LONG).show()
                }
                productImage.setOnLongClickListener() {
//                    Toast.makeText(
//                        view.context,
//                        " 180 degree flip with MRP, Margin, Units",
//                        Toast.LENGTH_LONG
//                    ).show()
                    if(itemList[adapterPosition].productEnquiry){
                        tick.visibility = View.VISIBLE
                    }
                    productImage.animate().apply {
                        duration =2000
                        rotationYBy(360f)
                    }.start()

                    productImage.visibility = View.INVISIBLE
                    pImage.visibility = View.VISIBLE
                    true
                }
                pImage.setOnLongClickListener(){
                    pImage.animate().apply {
                        duration =2000
                        rotationYBy(360f)
                    }.start()
                    productImage.visibility = View.VISIBLE
                    pImage.visibility = View.GONE
                    true
                }
            }
            else if(context is AgentLogin){
                view.setOnClickListener { v: View ->
                    val position: Int = adapterPosition
                    val builder = AlertDialog.Builder(context)

                    val dialogLayout =
                        LayoutInflater.from(context).inflate(R.layout.edit_text_layout3, null)
                    val registerSpinner = dialogLayout.findViewById<Spinner>(R.id.spinner3)
                    val timeEnquiry = dialogLayout.resources.getStringArray(R.array.TimeEnquiry)
                    val unitsA = dialogLayout.findViewById<EditText>(R.id.unitsA)
                    val shops = dialogLayout.findViewById<EditText>(R.id.shops)
                    var time :String = ""
//                    val product2:TextView? = view.findViewById(R.id.product2)
//                    product2?.text = itemList[position].productName
                    if(registerSpinner != null){
                        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, timeEnquiry)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        registerSpinner.adapter = adapter

                        registerSpinner.onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                time = timeEnquiry[position]
                            }
                        }

                    }


                    with(builder) {
                        setTitle("Interested for ${itemList[position].productName}")
                        setPositiveButton("Send Enquiry") { dialog, which ->
                            tick.visibility = View.VISIBLE
                            itemList[position].productEnquiry = true
                            itemList[position].productUnits = unitsA.text.toString().toInt()
                            itemList[position].productTime = time
                            itemList[position].productShops = shops.text.toString().toInt()

                            Toast.makeText(
                                view.context,
                                "Enquiry sent to ${itemList[position].productCompany} \n Please wait for approval",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        setNeutralButton("Cancel"){dialog, which ->  

                        }
                        setView(dialogLayout)
                        show()

                    }

//                Toast.makeText(view.context, "Interested for ${itemList[position].productName} \n Enquiry sent to ${itemList[position].productCompany} \n Please wait for approval",Toast.LENGTH_LONG).show()
                }
                productImage.setOnLongClickListener() {
//                    Toast.makeText(
//                        view.context,
//                        " 180 degree flip with MRP, Margin, Units",
//                        Toast.LENGTH_LONG
//                    ).show()
                    if(itemList[adapterPosition].productEnquiry){
                        tick.visibility = View.VISIBLE
                    }
                    productImage.animate().apply {
                        duration =2000
                        rotationYBy(360f)
                    }.start()

                    productImage.visibility = View.INVISIBLE
                    pImage.visibility = View.VISIBLE
                    true
                }
                pImage.setOnLongClickListener(){
                    pImage.animate().apply {
                        duration =2000
                        rotationYBy(360f)
                    }.start()
                    productImage.visibility = View.VISIBLE
                    pImage.visibility = View.GONE
                    true
                }
            }
        }

    }
}
