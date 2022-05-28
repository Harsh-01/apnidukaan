package com.personaldistributor.yourpersonaldistributor

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.internal.Util
import com.personaldistributor.yourpersonaldistributor.util.SharedPreferencesUtils
import com.personaldistributor.yourpersonaldistributor.util.Utility
import kotlinx.android.synthetic.main.activity_agent_details.*
import java.text.SimpleDateFormat
import java.util.*


class Login_Activity : AppCompatActivity() {

    lateinit var etregistered_No: EditText
    lateinit var etpassword: EditText
    lateinit var btnLogin: Button
    lateinit var forgotPass: TextView
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("Users/Login")
    val refV = database.getReference("Users/SkCodes")

    //    step-1
    lateinit var sharedMail: SharedPreferences
    lateinit var sharedLoginId: SharedPreferences
    lateinit var sharedState: SharedPreferences
    var unFound: Boolean = false
    var totalChilds: Long = 0
    var newEmail = ""
    var email1: String = "personaldistributor21@gmail"
    var subject: String = "Forgot Password"
    var body: String = "Please reset my password!"
    var chooserTitle: String = "Adarsh"
    var login_counter = 0
    var isGone = false

    companion object {
        var LOGIN_TIME: String = "LoginTime"
        var FIREBASE_TOKEN: String = "FirebaseToken"
        var IS_AGENT: String = "IsAgent"
        val LOGIN_ID :  String = "LoginID"
        var E_MAIL: String = "E-mail"
        var timeFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        title = "Login Profile"

        //Check Default
        val fireBaseToken =
            SharedPreferencesUtils.getStringFromUserDefaults(this@Login_Activity, FIREBASE_TOKEN);
        fireBaseToken?.let { it ->
            Log.e("Token Found", it)
            if (it.isNotBlank()) {
                val isAgent = SharedPreferencesUtils.getBooleanFromUserDefaults(
                    this@Login_Activity,
                    IS_AGENT
                );
                isAgent?.let { it1 ->
                    goToHomeScreen(it1)
                }
            }
        }

        val register_options = resources.getStringArray((R.array.register_options))
        //access the spinner
        val registerSpinner = findViewById<Spinner>(R.id.spinner3)
        //step-2 initialse sharedpreference variable
        sharedMail = getSharedPreferences(getString(R.string.preference_code), Context.MODE_PRIVATE)
        sharedLoginId =
            getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        sharedState =
            getSharedPreferences(getString(R.string.preference_boolean), Context.MODE_PRIVATE)

// Initialize Firebase Auth
        auth = Firebase.auth
        etregistered_No = findViewById(R.id.etregistered_No)
        etpassword = findViewById(R.id.etpassword)
        btnLogin = findViewById(R.id.btnLogin)
        forgotPass = findViewById(R.id.txtforgotPassword)
        forgotPass.setOnClickListener {
            val uri: Uri = Uri.parse("mailto:$email")
                .buildUpon()
                .appendQueryParameter("subject", subject)
                .appendQueryParameter("body", body)
                .appendQueryParameter("to", email1)
                .build()

            val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
            startActivity(Intent.createChooser(emailIntent, chooserTitle))

        }

        btnLogin.setOnClickListener {
            if (Utility.isNetworkAvailable(this@Login_Activity))
                signInUser()
            else
                Toast.makeText(
                    this@Login_Activity,
                    "Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
//            signInUser()
        }

        if (registerSpinner != null) {
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, register_options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            registerSpinner.adapter = adapter

            registerSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    if (register_options[position] == "Agent") {
                        Handler().postDelayed({
                            Toast.makeText(
                                this@Login_Activity,
                                getString(R.string.selected_item) + " " + " " + register_options[position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 1000)
                        val intent1 = Intent(this@Login_Activity, Agent_register::class.java)
                        startActivity(intent1)

                    } else if (register_options[position] == "Executive") {
                        Handler().postDelayed({
                            Toast.makeText(
                                this@Login_Activity,
                                getString(R.string.selected_item) + " " + " " + register_options[position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 1000)
                        //Add Intents
                    } else if (register_options[position] == "Shop Owner") {
                        Handler().postDelayed({
                            Toast.makeText(
                                this@Login_Activity,
                                getString(R.string.selected_item) + " " + " " + register_options[position],
                                Toast.LENGTH_SHORT
                            ).show()
                        }, 1000)
                        val intent1 = Intent(this@Login_Activity, Vendor_register::class.java)
                        startActivity(intent1)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Handler().postDelayed({
                        Toast.makeText(
                            this@Login_Activity,
                            "Welcome to Login page",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, 1000)
                }

            }
        }
//        val imglogo = findViewById<ImageView>(R.id.imglogo)
//        ViewCompat.animate(imglogo)
////            .translationX(50f)
//            .translationY(-400f)
//            .setDuration(1000)
//            .setInterpolator(AccelerateDecelerateInterpolator())
//            .setStartDelay(50)

    }


    private fun signInUser() {

        if (etregistered_No.text.toString().isEmpty()) {
            etregistered_No.error = "Please enter email"
            etregistered_No.requestFocus()
            return
        }
        if (etpassword.text.toString().isEmpty()) {
            etpassword.error = "Please enter password"
            etpassword.requestFocus()
            return
        }

        Utility.showProgressDialog(this@Login_Activity)
        if (!Patterns.EMAIL_ADDRESS.matcher(etregistered_No.text.toString()).matches()) {
            val loginName = etregistered_No.text.toString()
            refV.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Login_Activity, "Please try again", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (child: DataSnapshot in snapshot.children) {
                            if (child.key == loginName) {
                                unFound = true
                                newEmail = snapshot.child(loginName).child("email")
                                    .getValue(String::class.java) as String
                                break
                            }
                        }
                        totalChilds = snapshot.childrenCount
                    }
//                    if(!unFound){
//                        Toast.makeText(this@Login_Activity, "Incorrect email or username", Toast.LENGTH_SHORT).show()
//                        return
//                    }
                }
            })
//            while(true) {
//                if(totalChilds>0) {
            if (!unFound) {
                Toast.makeText(
                    this@Login_Activity,
                    "Incorrect email or username",
                    Toast.LENGTH_SHORT
                ).show()
                Utility.hideProgressDialog()
                return
            }
//                    break
//                }
//            }
        } else {
            newEmail = etregistered_No.text.toString()
        }

//        Toast.makeText(this@Login_Activity," Blank: ${newEmail.isBlank()}", Toast.LENGTH_LONG).show()
        auth.signInWithEmailAndPassword(newEmail, etpassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //step-4 use sare pref variable to get data , until now we were storing data, now we are fetching it
                    //use getBoolean for getting boolean values, getString for getting string values
                    //kahan karna hai batao open that file, i will make sure you learn it today, where  to go? ok wait
                    var isAgent = sharedState.getBoolean("isAgent", false)
                    val userId = sharedMail.getString("UID", "TEMPORARYUID")

                    val uid = auth.currentUser?.uid
                    val currentTime = timeFormat.format(Date())


                    SharedPreferencesUtils.saveStringToUserDefaults(
                        this@Login_Activity,
                        FIREBASE_TOKEN,
                        uid
                    )
                    SharedPreferencesUtils.saveStringToUserDefaults(
                        this@Login_Activity,
                        E_MAIL,
                        etregistered_No.text.toString()
                    )
                    SharedPreferencesUtils.saveStringToUserDefaults(
                        this@Login_Activity,
                        LOGIN_TIME,
                        currentTime
                    );

//                    myRef.addValueEventListener(postListener1)

                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var idFound = false
                            for (child: DataSnapshot in snapshot.children) {
                                if (child.key == uid) {

                                    val testUser = snapshot.child(uid.toString()).child("ID")
                                        .getValue(String::class.java)
                                    val testAgent =
                                        snapshot.child(uid.toString()).child("isAgent")
                                            .getValue(Boolean::class.java)
//                                    Toast.makeText(
//                                        this@Login_Activity,
//                                        "${testAgent}",
//                                        Toast.LENGTH_LONG
//                                    ).show()
                                    //step -3 use .edit() function with shared pref variable and use putString if you want to save string
                                    //in shared pref, use putInteger for int or putBoolean for storing boolean values

//                                    ok? br
                                    sharedLoginId.edit().putString("LoginId", testUser).apply()
                                    sharedState.edit().putBoolean("isAgent", testAgent as Boolean)
                                        .apply()
                                    SharedPreferencesUtils.saveBooleanToUserDefaults(
                                        this@Login_Activity,
                                        IS_AGENT,
                                        testAgent
                                    )
                                    SharedPreferencesUtils.saveStringToUserDefaults(
                                        this@Login_Activity,
                                        LOGIN_ID,
                                        testUser
                                    )
                                    isAgent = testAgent
                                    idFound = true
                                    break
                                }
                            }
                            if (!idFound) {
                                sharedLoginId.edit().putString("LoginId", userId).apply()
                                myRef.child(uid.toString()).child("ID").setValue(userId)
                                myRef.child(uid.toString()).child("isAgent").setValue(isAgent)
                            }
                            if (!isGone) {
                                goToHomeScreen(isAgent)
                            }

                        }

                        // this will called when any problem
                        // occurs in getting data
                        override fun onCancelled(error: DatabaseError) {
                            // we are showing that error message in toast
                            Utility.hideProgressDialog()
                            Toast.makeText(
                                this@Login_Activity,
                                "Please Sign-In again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                } else {
                    // If sign in fails, display a message to the user.
                    Utility.hideProgressDialog()
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun goToHomeScreen(IsAgent: Boolean) {
        var eMail = SharedPreferencesUtils.getStringFromUserDefaults(this@Login_Activity, E_MAIL)
        if (IsAgent && Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) {
            val intent = Intent(this@Login_Activity, AgentLogin::class.java)
            Toast.makeText(this@Login_Activity, "Redirected to agent", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
            Utility.hideProgressDialog()


            auth = Firebase.auth
            val uid1 = auth.currentUser?.uid
            myRef.child(uid1.toString()).addListenerForSingleValueEvent(postListener)

        } else {
            val intent = Intent(this@Login_Activity, VendorLogin::class.java)
            Toast.makeText(this@Login_Activity, "Redirected to vendor", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
            Utility.hideProgressDialog()
        }
    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.hasChildren()) {
                try {
                    login_counter = dataSnapshot.child("login_counter").getValue(Int::class.java)!!
                    login_counter += 1

                    val uid = auth.currentUser?.uid
                    myRef.child(uid.toString()).child("login_counter").setValue(login_counter)
                } catch (e: Exception) {
                    login_counter += 1

                    val uid = auth.currentUser?.uid
                    myRef.child(uid.toString()).child("login_counter").setValue(login_counter)
                }

                isGone = true

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Toast.makeText(this@Login_Activity, "Error loading page", Toast.LENGTH_SHORT)
                .show()
        }
    }

//    var postListener1 = object : ValueEventListener
//    {
//
//        val sharedMail = context.getSharedPreferences(context.getString(R.string.preference_code), Context.MODE_PRIVATE)
//        val sharedLoginId = getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
//        val sharedState = getSharedPreferences(getString(R.string.preference_boolean), Context.MODE_PRIVATE)
//        val auth = Firebase.auth
//        val uid = auth.currentUser?.uid
//        val currentTime = timeFormat.format(Date())
//        var isAgent = sharedState.getBoolean("isAgent", false)
//        val userId = sharedMail.getString("UID", "TEMPORARYUID")
//
//        override fun onDataChange(snapshot: DataSnapshot) {
//
//            return
//
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            Utility.hideProgressDialog()
//            Toast.makeText(
//                this@Login_Activity,
//                "Please Sign-In again",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser?.uid as String
//        updateUI(currentUser)
    }

    fun updateUI(currentUser: String) {
        if (currentUser.isNotBlank()) {
            var found = false
            var token1 = false
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child: DataSnapshot in snapshot.children) {
                        if (child.key == currentUser) {
                            val ID =
                                snapshot.child(currentUser).child("ID").getValue(String::class.java)
                            val token = snapshot.child(currentUser).child("isAgent")
                                .getValue(Boolean::class.java)
                            sharedLoginId.edit().putString("LoginId", ID).apply()
                            token1 = token as Boolean
                            found = true
                            break
                        }
                    }
                    if (found && token1) {
                        startActivity(Intent(this@Login_Activity, AgentLogin::class.java))
                    } else if (found && !token1) {
                        startActivity(Intent(this@Login_Activity, VendorLogin::class.java))
                    }
                }

            })

//            startActivity(Intent(this, AgentLogin::class.java))
        }
    }
}


