package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.internal.indexOfFirstNonAsciiWhitespace

//import kotlinx.android.synthetic.main.vendor_register_page.*
//import kotlin.math.sign

class Vendor_register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var sharedMail : SharedPreferences
    lateinit var sharedName1 : SharedPreferences
    lateinit var sharedAddress1 : SharedPreferences
    lateinit var sharedState: SharedPreferences
    lateinit var shared_shopkeeper: SharedPreferences
    lateinit var sPhone: SharedPreferences
    lateinit var sEmail: SharedPreferences

    var email: EditText? = null
    var username: EditText? = null
    var agentCode:EditText? = null
    var setPass:EditText?= null
    var confpass:EditText?= null
    var mobile: EditText?= null
    var gst:EditText?= null
    var shopName:EditText? = null
    var address:EditText?= null
    var postal_address:EditText?= null
    var buisness_type : String = ""
    var size_type : String = ""
    var influence_type :String = ""
    var shop_type : String = ""
    var agentName : String= ""
    var username_for_vendor_login : EditText? = null
    val database = Firebase.database
    var refA = database.getReference("Users/AgCodes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vendor_register_page)
        sEmail = getSharedPreferences("sEmail",Context.MODE_PRIVATE)
        sPhone = getSharedPreferences("sPhone",Context.MODE_PRIVATE)
        sharedState = getSharedPreferences(getString(R.string.preference_boolean), Context.MODE_PRIVATE)
        sharedName1 = getSharedPreferences(getString( R.string.preference_code_name1), Context.MODE_PRIVATE)
        sharedAddress1 = getSharedPreferences(getString( R.string.preference_code_address1), Context.MODE_PRIVATE)
        sharedMail = getSharedPreferences(getString(R.string.preference_code), Context.MODE_PRIVATE)
        shared_shopkeeper = getSharedPreferences(getString(R.string.shopkeeper_code), Context.MODE_PRIVATE)
        email = findViewById(R.id.email_id)
        username = findViewById(R.id.name_vendor)
        agentCode = findViewById<EditText>(R.id.agent_code)
        setPass = findViewById(R.id.pass)
        confpass = findViewById<EditText>(R.id.confirm_pass)
        mobile = findViewById<EditText>(R.id.mobile_auth)
        gst = findViewById<EditText>(R.id.payID)
        shopName = findViewById<EditText>(R.id.shop_name)
        address = findViewById<EditText>(R.id.address)
        postal_address = findViewById<EditText>(R.id.postal_address)
        username_for_vendor_login = findViewById(R.id.username_for_vendor_login)

        //access the items of the lists
        val shop_types = resources.getStringArray((R.array.shop_type))
        val buisness_types = resources.getStringArray(R.array.business_type)
        val shop_sizes = resources.getStringArray(R.array.shop_size)
        val shop_influences = resources.getStringArray((R.array.shop_influence))
        //access the spinner
        val shop_type_spinner = findViewById<Spinner>(R.id.shop_type_spinner)
        val buisness_type_spinner = findViewById<Spinner>(R.id.buisness_type_spinner)
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)

        // Write a message to the database

        val myRef = database.getReference("Users/ShopOwners")
        val refUsername = database.getReference("Users/AgCodes")

        val refV = database.getReference("Users/SkCodes")


//      Initialize Firebase Auth
        auth = Firebase.auth
        sharedState.edit().putBoolean("isAgent", false).apply()

        val toolbar = findViewById<Toolbar>(R.id.toolbar01)
//        setSupportActionBar(toolbar)
        title = "Shop Vendor Sign-up Details"
        //Add stage2 button
        val stage2btn : ImageButton = findViewById(R.id.stage2Btn)
//        refA.addValueEventListener(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(child:DataSnapshot in snapshot.children){
//                    if(child.key == agentCode?.text.toString()){
//                        agentName = snapshot.child(agentCode?.text.toString()).child("Name").getValue(String::class.java) as String
//                        email?.visibility = View.VISIBLE
//                        break
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@Vendor_register, "please try again", Toast.LENGTH_LONG).show()
//            }
//        })
        agentCode?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                refA.addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(child:DataSnapshot in snapshot.children){
                            if(child.key == p0.toString()){
                                agentName = snapshot.child(agentCode?.text.toString()).child("Name").getValue(String::class.java) as String
                                email?.visibility = View.VISIBLE
                                break
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@Vendor_register, "please try again", Toast.LENGTH_LONG).show()
                    }
                })

//                if(p0.toString() == "HD0429") {
//                    email?.visibility = View.VISIBLE
//                }else if(p0.toString() == "AT0925"){
//                    email?.visibility = View.VISIBLE
//                }else if(p0.toString() == "JM43DUC"){
//                    email?.visibility = View.VISIBLE
//                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().trim().length == 0) {
                    email?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })

        email?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    setPass?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    setPass?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        setPass?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    confpass?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    confpass?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })

        confpass?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    username?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    username?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        username?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    mobile?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    mobile?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        mobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    gst?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    gst?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        gst?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    shopName?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    shopName?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        shopName?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    address?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    address?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        address?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    postal_address?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    postal_address?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        postal_address?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()) {
                    spinner1.visibility = View.VISIBLE
                    buisness_type_spinner.visibility = View.VISIBLE
                    shop_type_spinner.visibility = View.VISIBLE
                    spinner2.visibility = View.VISIBLE
                    username_for_vendor_login?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    spinner1.visibility = View.GONE
                    buisness_type_spinner.visibility = View.GONE
                    shop_type_spinner.visibility = View.GONE
                    spinner2.visibility = View.GONE
                    username_for_vendor_login?.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
        username_for_vendor_login?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                refV.addValueEventListener(object: ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var isFound = false
                        for(child:DataSnapshot in snapshot.children){
                            if(p0.toString()== child.key) {
                                isFound = true
                                Toast.makeText(this@Vendor_register, "Username already exists, please try again", Toast.LENGTH_LONG).show()
                                break
                            }
                        }
                        if(!isFound){
//                            Toast.makeText(this@Vendor_register, "Username created successfully", Toast.LENGTH_LONG).show()
                            stage2btn.visibility = View.VISIBLE
                        }

                    }

                })


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isEmpty()) {
                    stage2btn.visibility = View.GONE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(p0.toString().trim().length == 0) {
//                    email?.visibility = View.GONE
//                }
            }

        })
//        if(agentCode?.text.toString() != ""){
//            email?.visibility = View.VISIBLE
//        }


        if(spinner1 != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shop_sizes)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter

            spinner1.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id : Long){
                        if(position != 0) {
                            Toast.makeText(
                                this@Vendor_register,
                                getString(R.string.selected_item) + " " + " " + shop_sizes[position],
                                Toast.LENGTH_SHORT
                            ).show()
                            size_type = shop_sizes[position]       //Store string
                        }
                    }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }
        if(spinner2 != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shop_influences)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter

            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id : Long){
                    if(position != 0) {
                        Toast.makeText(
                            this@Vendor_register,
                            getString(R.string.selected_item) + " " + " " + shop_influences[position],
                            Toast.LENGTH_SHORT
                        ).show()
                        influence_type = shop_influences[position]      //Store String
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }
        if(buisness_type_spinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, buisness_types)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            buisness_type_spinner.adapter = adapter

            buisness_type_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id : Long){
                    if(position != 0) {
                        Toast.makeText(
                            this@Vendor_register,
                            getString(R.string.selected_item) + " " + " " + buisness_types[position],
                            Toast.LENGTH_SHORT
                        ).show()
                        buisness_type = buisness_types[position]       //Store string
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }

        if(shop_type_spinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shop_types)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shop_type_spinner.adapter = adapter

            shop_type_spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>,view: View, position: Int, id : Long){
                    if(position != 0) {
                        Toast.makeText(
                            this@Vendor_register,
                            getString(R.string.selected_item) + " " + " " + shop_types[position],
                            Toast.LENGTH_SHORT
                        ).show()
                        shop_type = shop_types[position]       //Store string
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            }
        }


        //Setting firebase unique key for hashmap list
        var userId = myRef.push().key


        //Go to next page
        stage2btn.setOnClickListener {
            Toast.makeText(this@Vendor_register, "${username_for_vendor_login?.text.toString()}", Toast.LENGTH_SHORT).show()
            if(signUpUser()) {
                sEmail.edit().putString("skEmail", email?.text.toString()).apply()
                sPhone.edit().putString("skPhone", mobile?.text.toString()).apply()
//            val userId = auth.currentUser?.uid
                myRef.child(userId.toString()).setValue(
                    Sk_users(
                        email?.text.toString(),
                        agentCode?.text.toString(),
                        setPass?.text.toString(),
                        username?.text.toString(),
                        mobile?.text.toString(),
                        gst?.text.toString(),
                        shopName?.text.toString(),
                        address?.text.toString(),
                        postal_address?.text.toString(),
                        size_type,
                        buisness_type,
                        shop_type,
                        influence_type,
                        false,
                        agentName,
                        "",
                        "",
                        "",
                        ""
                    )
                )
                refV.child(username_for_vendor_login?.text.toString()).child("email").setValue(email?.text.toString())
                refUsername.child(agentCode?.text.toString()).child(userId.toString()).child("Name").setValue(username?.text.toString())
//            val newIntent = Intent(this@Vendor_register, RegisterPage2::class.java)
//            startActivity(newIntent)
                sharedMail.edit().putString("UID", userId.toString()).apply()
                sharedName1.edit().putString("SkName", username?.text.toString()).apply()
                sharedAddress1.edit().putString("SkAdd", agentCode?.text.toString()).apply()
                shared_shopkeeper.edit().putString("CODE",username_for_vendor_login?.text.toString()).apply()
            }
        }
    }

    private fun signUpUser():Boolean{
        if(username?.text.toString().isEmpty()){
            username?.error = "Please enter name"
            username?.requestFocus()
            return false
        }
        if(email?.text.toString().isEmpty()){
            email?.error = "Please enter email ID"
            email?.requestFocus()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email?.text.toString()).matches()){
            email?.error = "Please enter valid email"
            email?.requestFocus()
            return false
        }
        if(setPass?.text.toString().isEmpty()){
            setPass?.error = "Please set password"
            setPass?.requestFocus()
            return false
        }
        if(confpass?.text.toString().isEmpty() || confpass?.text.toString() != setPass?.text.toString()){
            confpass?.error = "Please re-enter password"
            confpass?.requestFocus()
            return false
        }
        if(agentCode?.text.toString().isEmpty()){
            agentCode?.error = "Please enter agent code"
            agentCode?.requestFocus()
            return false
        }
        if(gst?.text.toString().isEmpty()){
            gst?.error = "Please enter GST No. or UPI ID"
            gst?.requestFocus()
            return false
        }
        if(mobile?.text.toString().isEmpty()){
            mobile?.error = "Please enter mobile no."
            mobile?.requestFocus()
            return false
        }
        if(shopName?.text.toString().isEmpty()){
            shopName?.error = "Please enter shop name"
            shopName?.requestFocus()
            return false
        }
        if(address?.text.toString().isEmpty()){
            address?.error = "Please enter address"
            address?.requestFocus()
            return false
        }
        if(postal_address?.text.toString().isEmpty()){
            postal_address?.error = "Please enter postal address"
            postal_address?.requestFocus()
            return false
        }
        if(buisness_type == "Business Segment"){
            Toast.makeText(this, "please select business segment", Toast.LENGTH_SHORT).show()
            return false
        }
        if(shop_type == "Shop Type"){
            Toast.makeText(this, "please select shop type", Toast.LENGTH_SHORT).show()
            return false
        }
        if(size_type == "Shop Size"){
            Toast.makeText(this, "please select shop size", Toast.LENGTH_SHORT).show()
            return false
        }
        if(influence_type == "Shop Influence"){
            Toast.makeText(this, "please select shop influence", Toast.LENGTH_SHORT).show()
            return false
        }



        auth.createUserWithEmailAndPassword(email?.text.toString(), setPass?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    refA.child(agentCode?.text.toString()).child("totalShops").child(username_for_vendor_login?.text.toString())
                    startActivity(Intent(this, RegisterPage2::class.java ))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed, please check your internet connection",
                    Toast.LENGTH_LONG).show()
                }

                // ...
            }
        return true

    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }
    fun updateUI(currentUser : FirebaseUser?){
        if(currentUser != null){
//            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, AgentLogin::class.java))
        }
    }


}
