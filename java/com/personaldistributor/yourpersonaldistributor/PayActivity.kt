package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
//Return back to profile when balance is 0
//Add code in login screen to prevent login when signedOut
//Set Reminder before monday, wednesday, saturday
class PayActivity : AppCompatActivity() {
    lateinit var etname1:String
    lateinit var etamount:String
    lateinit var etUPI:String
    lateinit var btnPay:Button
    lateinit var etamount1 :EditText
    private lateinit var auth: FirebaseAuth
    var UPI_PAYMENT = 0
// ...
// Initialize Firebase Auth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        auth = Firebase.auth
        btnPay = findViewById(R.id.payment)
        etamount1 = findViewById(R.id.amount1)

        etname1 = "Adarsh Tiwari"
        etamount = "100"
        etUPI = "adarsh9tiwari@okicici"
        var totalReg = 0            //To be taken from database
        var currTotalReg = 0        //To be taken from database
        var offlineReg = 0          //To be taken from database

        btnPay.setOnClickListener {
            calAmount(totalReg, currTotalReg, offlineReg)
        }

    }

    private fun calAmount(totalReg:Int,currTotalReg:Int,offlineReg:Int){
        var sdf = SimpleDateFormat("EEEE")
        var d = Date()
        var dayOfTheWeek = sdf.format(d)

        var currAmount = etamount1.text.toString().toInt()
//        var currAmount = ((offlineReg*100) -(currTotalReg*20))
        var registrations = totalReg  +  currTotalReg // To be shown in textview


            if(currAmount>0){
                //Agent has to pay to company


                if ((dayOfTheWeek=="Monday")||(dayOfTheWeek=="Wednesday")||(dayOfTheWeek== "Saturday")) {

//                Handler().postDelayed({
//                    Toast.makeText(this@PayActivity,"Please Pay The Balance Amount : Rs.${currAmount}",Toast.LENGTH_LONG ).show()
//                },2000)

                    val dialog = AlertDialog.Builder(this@PayActivity)
                    dialog.setMessage("Please Pay The Balance Amount : Rs.${currAmount}")
                    dialog.setPositiveButton("Pay")
                    {
                            text, listener -> payUsingUpi(etname1 ,etamount ,etUPI)
                    }
                    dialog.setNegativeButton("Cancel")
                    {
                            text, listener ->
                        Toast.makeText(this@PayActivity,"You are blocked : Complete payment of Rs.${currAmount} to continue..",Toast.LENGTH_LONG ).show()
                        Firebase.auth.signOut()
                        startActivity(Intent(this, Login_Activity::class.java))
                    }
                    dialog.create()
                    dialog.show()
                }
            }else if(currAmount < 0){
                //Company has to pay to agent

                val dialog = AlertDialog.Builder(this@PayActivity)
                dialog.setMessage("Thank you! You will receive payment for Rs.${-(currAmount)} ")
                dialog.setPositiveButton("Ok")
                {
                        text, listener -> //Return back to profile
                }

                dialog.create()
                dialog.show()
//            Handler().postDelayed({
//                Toast.makeText(this@PayActivity,"You will receive payment for Rs.${-(currAmount)}",Toast.LENGTH_LONG ).show()
//            },2000)

            }else if (currAmount == 0){
                val dialog = AlertDialog.Builder(this@PayActivity)
                dialog.setMessage("Thank you! Balance Amount is ${(currAmount)}, You can continue.. ")
                dialog.setPositiveButton("Ok")
                {
                        text, listener -> //Return back to profile
                }

                dialog.create()
                dialog.show()
            }





    }

    private fun payUsingUpi(name:String, amount:String, upiId:String){

        val uri: Uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pn",name)
            .appendQueryParameter("am",amount)
            .appendQueryParameter("mc","")
            .appendQueryParameter("tr","25584584")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        when(requestCode){
            UPI_PAYMENT -> {
                if ((RESULT_OK == resultCode) || (resultCode == 11))
                {
                    if (data != null) {
                        val trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        var dataList = ArrayList<String?>();
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
//Send me the zip file tonight itself
    //He is thinking we are doing some other work
    //How can i show i am working constantly even missing my college classes.
    //Fucked my brain today on login part and chat box
    //now we have to search how to add news snippet in fragment
    //It is simple linearLayout with hozizontal orientations and 10-12 imageviews
    //Next in history fragments we have to add 10-12 new products from diff country which are not used/known in IND
    //Make the current Product Fragment as demo for shop vendor and include 4 products there
    //Each button clicked will take us to counter activity and so agent can show shopkeeper
    //how buttons of sale, resale work
    //Add no products image in vendors profile
    //Add this news snippet adds in home part i.e. fourth fragment
    //Also i need to figure out how to login vendor and agent together and make name and adress changes in the
    //screen according to new data taken form user
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
