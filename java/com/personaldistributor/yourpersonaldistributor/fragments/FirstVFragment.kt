package com.personaldistributor.yourpersonaldistributor.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.personaldistributor.yourpersonaldistributor.Ag_Users
import com.personaldistributor.yourpersonaldistributor.CounterActivity
import com.personaldistributor.yourpersonaldistributor.R
import com.personaldistributor.yourpersonaldistributor.Sk_users
import com.squareup.picasso.Picasso

class FirstVFragment:Fragment(){
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val TAG = "DatabaseRead"

    lateinit var ivname : TextView
    lateinit var ivagentName: TextView
    lateinit var ivProfile:ImageView
    lateinit var sharedLoginId : SharedPreferences
    lateinit var sharedName1 : SharedPreferences
    lateinit var sharedAddress1 : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_v, container, false)
        sharedLoginId = (activity as Context).getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        val userId = sharedLoginId.getString("LoginId", "TEMPORARYUID")
        sharedName1 = (activity as Context).getSharedPreferences(getString( R.string.preference_code_name1), Context.MODE_PRIVATE)
        sharedAddress1 = (activity as Context).getSharedPreferences(getString( R.string.preference_code_address1), Context.MODE_PRIVATE)
        ivProfile = view.findViewById(R.id.ivProfile)
        ivname = view.findViewById(R.id.tv_name)
        ivagentName = view.findViewById(R.id.textView)

        val myRef = database.getReference("Users/ShopOwners")
//        val refA = database.getReference("Users/AgCodes")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {
                    val user= dataSnapshot.getValue(Sk_users::class.java)
                    Toast.makeText(activity as Context, " Welcome ${user?.username}",Toast.LENGTH_LONG).show()
//                    Picasso.get().load(user?.profilepic).into(ivProfile)
//                ivProfile.setImageURI(user?.profilepic?.toUri())
                    ivname.setText(user?.username.toString())
                    ivagentName.setText(user?.agentName.toString())
                    //...
                }
                else{
                    Toast.makeText(activity as Context, "No data", Toast.LENGTH_SHORT).show()
                    Toast.makeText(activity as Context, "${userId.toString()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(activity as Context, "Error loading page", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.child(userId.toString()).addListenerForSingleValueEvent(postListener)

//        var Uname = sharedName1.getString("SkName", "Hritesh Dwivedi")
        var Uaddress = sharedAddress1.getString("SkAdd", "JM43DUC")
//        ivaddress.setText(Uaddress)
//        val string1 = "Hritesh Dwivedi"
//        val string2 = "Adarsh Tiwari"
//        val string3 = "Jack Miller"
//
//        ivname.setText(Uname)
//        if(Uaddress == "HD0429"){
//            ivaddress.setText(string1)
//        }else if(Uaddress =="AT0925" ){
//            ivaddress.setText(string2)
//        }else if(Uaddress == "JM43DUC"){
//            ivaddress.setText(string3)
//        }



        ivProfile.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(activity as Context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }




        return view
    }
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(activity as Context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ivProfile.setImageURI(data?.data)
        }
    }
}