package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_pay_window.view.*
import android.R.attr.data
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.NetworkInfo
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_pay_window.*
import kotlinx.android.synthetic.main.fragment_fourth.*


class PayWindow : AppCompatActivity() {
    lateinit var etname1:String
    lateinit var etamount:String
    lateinit var btnPay:Button
    lateinit var etUPI:String
    lateinit var btnPayFor: Button
    lateinit var webView: WebView
    lateinit var shared_shopkeeper: SharedPreferences

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationID = 101
    private val razorpay_url= "https://rzp.io/l/8EqV3Em"

     var UPI_PAYMENT = 0
   @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_window)
        createNotificationChannel()
        title = "UPI PAYMENT"
        var payOption = ""
        var pay_options = resources.getStringArray((R.array.payMode))
        //access the spinner
        val paySpinner = findViewById<Spinner>(R.id.paywith)
        shared_shopkeeper = getSharedPreferences(getString(R.string.shopkeeper_code), Context.MODE_PRIVATE)


        if (paySpinner != null) {
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, pay_options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            paySpinner.adapter = adapter

            paySpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    if (pay_options[position] == "UPI/Online") {
                        payOption = pay_options[position]
                        Handler().postDelayed({
                            Toast.makeText(
                                this@PayWindow,
                                getString(R.string.selected_item) + " " + " " + pay_options[position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 1000)


                    }
                    else if (pay_options[position] == "Cash To Agent") {
                        payOption = pay_options[position]

                        //Send message to user to verify offline payment
//                        sendNotification()
                        Handler().postDelayed({
                            Toast.makeText(
                                this@PayWindow,
                                getString(R.string.selected_item) + " " + " " + pay_options[position],
                                Toast.LENGTH_SHORT
                            ).show()

                        }, 1000)}
                    else if (pay_options[position] == "Razorpay") {
                        payOption = pay_options[position]

                        //Send message to user to verify offline payment
//                        sendNotification()
                        Handler().postDelayed({
                            Toast.makeText(
                                this@PayWindow,
                                getString(R.string.selected_item) + " " + " " + pay_options[position],
                                Toast.LENGTH_SHORT
                            ).show()

                        }, 1000)
                        //Add Intents
//                        Handler().postDelayed({
//
//                            startActivity(Intent(this@PayWindow,Login_Activity::class.java))
//                            Toast.makeText(this@PayWindow,"Please use SignIn to continue",Toast.LENGTH_SHORT).show()
//
//                        }, 2000)



                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Handler().postDelayed({
                        Toast.makeText(
                            this@PayWindow,
                            "Welcome to Payment Window page",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, 1000)
                }

            }
        }



        etname1 = "Clever Technologies"
        etamount = "1"
        etUPI = "9999058790@axl"
        btnPay = findViewById(R.id.btnPay)
        btnPayFor = findViewById(R.id.btnPayFor)
//       webView = findViewById(R.id.webView)


        btnPayFor.setOnClickListener {

            val dialog = AlertDialog.Builder(this@PayWindow)
            dialog.setMessage("The meager amount of 200Rs. includes charges for the following:\n" +
                    "•\tShop verification - Rs.20\n" +
                    "•\tShop owner verification - Rs.20\n" +
                    "•\tShop owner identity card - Rs.50\n" +
                    "•\tProfile registration - Rs.30\n" +
                    "•\tData protection - Rs.40\n" +
                    "•\tOnline communication - Rs.40")
           /** dialog.setPositiveButton("Accept")
            {
                    text, listener ->


            }
            dialog.setNegativeButton("Cancel")
            {
                    text, listener ->

            }**/
            dialog.create()
            dialog.show()

        }

        btnPay.setOnClickListener {
            if(payOption == "UPI/Online"){
                payUsingUpi(etname1 ,etamount ,etUPI)
            }else if(payOption == "Cash To Agent"){
                sendNotification()

                Handler().postDelayed({

                            startActivity(Intent(this@PayWindow,Login_Activity::class.java))
                            Toast.makeText(this@PayWindow,"Please use SignIn to continue",Toast.LENGTH_SHORT).show()

                        }, 2000)
            }else if(payOption == "Razorpay"){
             val intent = Intent(this@PayWindow,com.personaldistributor.yourpersonaldistributor.WebView::class.java)
                startActivity(intent)
            }

        }

    }
    private fun createNotificationChannel(){
        //It is required to create a notification channel for notifications to show up from API Level-26
        //due to security reasons and more user freedom
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
                description = descriptionText
            }
            //Register the channel with system
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            //This will take care of all the work we need to make sure that notification actually pops up on versions
            //of OREO and above
        }

    }

    private fun sendNotification(){
        val shopVCode =  shared_shopkeeper.getString("CODE", "TemporaryCode")

        val intent = Intent(this, Login_Activity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,intent,0)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_tick_foreground)


        //Builder that creates the notification
        //Send @ notifications ,One instantly
        //Another one when we want!!
        val builder  = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(bitmapLargeIcon)
            .setContentTitle("Shop Owner Registered!")
            .setContentText("Scroll message")
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Congratulations! your payment of Rs. 100 has been approved" +
                    " You are now a registered product evaluator. Your shop vendor ID"+ shopVCode+ "under agent code 'xxxxxx' is on the way. " +
                    "Thank you for joining us"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationID, builder.build())
        }
    }

    private fun payUsingUpi(name:String, amount:String, upiId:String){

        var uri:Uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pn",name)
            .appendQueryParameter("am",amount)
            .appendQueryParameter("mc","5285190")
            .appendQueryParameter("tr","99705828")
            .appendQueryParameter("pa",upiId)
            .appendQueryParameter("cu","INR")
            .build()

        var upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.setData(uri)

        var chooser:Intent = Intent.createChooser(upiPayIntent,"Pay With")

        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }
    private fun razorpayURL(view:View?){
        val url = "https://rzp.io/l/8EqV3Em"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        when(requestCode){
            UPI_PAYMENT -> {
                if ((RESULT_OK == resultCode) || (resultCode == 11))
                {
                if (data != null) {
                    val trxt:String? = data.getStringExtra("response");
                    Log.d("UPI", "onActivityResult: " + trxt);
                   var dataList= ArrayList<String?>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null");
                    var dataList = ArrayList<String?>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                  }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    var dataList =  ArrayList<String?>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            }
        }

    }

    private fun upiPaymentDataOperation(data:ArrayList<String?>) {

        if (isConnectionAvailable(this)) {
            var str = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: $str")
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr =
                    response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            Log.d("UPI",status)
            if (status.equals("success")) {

                //Code to handle successful transaction here.
                Toast.makeText(this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT)
                    .show();
            } else {
                Toast.makeText(
                    this,
                    "Transaction failed.Please try again",
                    Toast.LENGTH_SHORT
                ).show();
            }
        }   else {
            Toast.makeText(this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
        }

    fun isConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable
            ) {
                return true
            }
        }
        return false
    }



}



