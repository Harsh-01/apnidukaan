package com.personaldistributor.yourpersonaldistributor

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*


class WebView : AppCompatActivity() {
    lateinit var sPhone: SharedPreferences
    lateinit var sEmail: SharedPreferences
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        sEmail = getSharedPreferences("sEmail", Context.MODE_PRIVATE)
        sPhone = getSharedPreferences("sPhone", Context.MODE_PRIVATE)
        val emailId = sEmail.getString("skEmail", "TEMPORARYID")
        val mobileId = sPhone.getString("skPhone", "TEMPORARY")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://rzp.io/l/BoCJ5W1")
    }


}
//https://pages.razorpay.com/pl_HouQQuzQjupS9E/view
//https://pages.razorpay.com/pl_HouQQuzQjupS9E/view/?email=$emailId&phone=$mobileId
//https://dashboard.razorpay.com/app/paymentpages/pl_Jaf8RYC86GZHeQ/edit