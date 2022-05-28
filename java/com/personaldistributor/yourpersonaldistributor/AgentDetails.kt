package com.personaldistributor.yourpersonaldistributor

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_agent_details.*

class AgentDetails : AppCompatActivity() {
    val TAG = "DatabaseRead"
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    lateinit var sharedLoginId : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_details)

//        Initialize Firebase Auth
        auth = Firebase.auth
        sharedLoginId = getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        val userId = sharedLoginId.getString("LoginId", "TEMPORARYUID")

        val myRef = database.getReference("Users/RoadRunners")
        myRef.child(userId as String).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AgentDetails, "Error loading page", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    val user= snapshot.getValue(Ag_Users::class.java)
                    name.text = user?.username
                    agentCode.text = user?.agent_code
                    email.text = user?.email
                    phone.text = user?.mobile
                    shoptotalval.text = user?.totalShops
//                    Toast.makeText(this@AgentDetails, user?.username, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, user?.username as String)
                }
            }

        })
    }
}

