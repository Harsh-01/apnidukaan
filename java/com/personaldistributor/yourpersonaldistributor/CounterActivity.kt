package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.core.view.LayoutInflaterCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CounterActivity : AppCompatActivity() {
    lateinit var sale:Button
    lateinit var resale:Button
    lateinit var inquiry:Button
    lateinit var referral:Button
    lateinit var returnbtn:Button
    lateinit var reason:Button
    lateinit var productDetails:Button
    var flagsale:Int = 0
    var flagresale:Int = 0
    var flaginquiry:Int = 0
    var flagreferral:Int = 0
    lateinit var shared1 : SharedPreferences
    lateinit var shared2 : SharedPreferences
    lateinit var shared3 : SharedPreferences
    lateinit var shared4 : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        shared1 = getSharedPreferences(getString(R.string.preferences_1), Context.MODE_PRIVATE)
        shared2 = getSharedPreferences(getString(R.string.preferences_2), Context.MODE_PRIVATE)
        shared3 = getSharedPreferences(getString(R.string.preferences_3), Context.MODE_PRIVATE)
        shared4 = getSharedPreferences(getString(R.string.preferences_4), Context.MODE_PRIVATE)

        sale = findViewById(R.id.sale)
        resale = findViewById(R.id.resale)
        inquiry = findViewById(R.id.inquiry)
        referral = findViewById(R.id.referral)
        returnbtn = findViewById(R.id.returnbtn)
        reason = findViewById(R.id.reason)
        productDetails = findViewById(R.id.productDetails)

        sale.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext)

            with(builder){
                setTitle("Sales for product?")
                setPositiveButton("Save"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) sale.text = "Sales \n${editText.text.toString()}"

                }
                setView(dialogLayout)
                show()
            }
//
        }
       // sale.setOnLongClickListener{
            //Edit Text DialogBox

           // true
       // }
        resale.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext)

            with(builder){
                setTitle("Resales for product?")
                setPositiveButton("Save"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) resale.text = "Resales \n${editText.text.toString()}"
                }
                setView(dialogLayout)
                show()
            }
        }
//        resale.setOnLongClickListener{
//            true
//        }
        inquiry.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext)

            with(builder){
                setTitle("Inquirys for product?")
                setPositiveButton("Save"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) inquiry.text = "Inquirys \n${editText.text.toString()}"
                }
                setView(dialogLayout)
                show()
            }
        }
//        inquiry.setOnLongClickListener{
//            true
//        }

        referral.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext)

            with(builder){
                setTitle("Referrals earned?")
                setPositiveButton("Save"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) referral.text = "Referrals \n${editText.text.toString()}"
                }
                setView(dialogLayout)
                show()
            }
        }
//        referral.setOnLongClickListener{
//            true
//        }

        returnbtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext)

            with(builder){
                setTitle("Products returned ?")
                setPositiveButton("Ok"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) returnbtn.text = "Return: ${editText.text.toString()}"
                }
                setView(dialogLayout)
                show()
            }
        }
        reason.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            val dialogLayout = layoutInflater.inflate(R.layout.edit_text_layout1,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_edittext1)

            with(builder){
                setTitle("Why return? Please explain..")
                setPositiveButton("Ok"){dialog, which ->
                    if(editText.text.toString().isNotBlank()) reason.text = "Reason \n${editText.text.toString()}"
                }
                setView(dialogLayout)
                show()
            }
        }

    }
    public fun mof( flag: Int){
//        if(flag == 0)
    }


}
