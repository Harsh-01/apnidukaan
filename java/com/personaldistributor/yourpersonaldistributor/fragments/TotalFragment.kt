package com.personaldistributor.yourpersonaldistributor.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.personaldistributor.yourpersonaldistributor.Ag_Users

import com.personaldistributor.yourpersonaldistributor.R
import kotlinx.android.synthetic.main.activity_agent_details.*

class TotalFragment : Fragment() {

    val database = Firebase.database
    lateinit var shopsRegVal: TextView
    lateinit var distanceCoveredVal : TextView
    lateinit var moneyEarnedVal : TextView
    lateinit var timeTakenVal : TextView
    lateinit var bonusVal : TextView
    lateinit var sharedLoginId : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_total, container, false)
        sharedLoginId = (activity as Context).getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        shopsRegVal = view.findViewById(R.id.shopsRegVal)
        distanceCoveredVal = view.findViewById(R.id.distanceCoveredVal)
        moneyEarnedVal = view.findViewById(R.id.moneyEarnedVal)
        timeTakenVal = view.findViewById(R.id.timeTakenVal)
        bonusVal = view.findViewById(R.id.bonusVal)



        val userId = sharedLoginId.getString("LoginId", "TEMPORARYUID")
        // Inflate the layout for this fragment
        var refA = database.getReference("Users/AgCodes")
        val myRef = database.getReference("Users/RoadRunners")
        var agentCode = ""
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {

                    val user= dataSnapshot.getValue(Ag_Users::class.java)
                    Toast.makeText(activity as Context, "${user?.username}",Toast.LENGTH_LONG).show()
//                Picasso.get().load(user?.profilepic).into(ivProfile)
//                ivProfile.setImageURI(user?.profilepic?.toUri())
//                    distanceCoveredVal.setText(user?.todayDistance.toString())
//                    moneyEarnedVal.setText(user?.todaysIncome.toString())
//                    timeTakenVal.setText(user?.todaysTime.toString())
//                    bonusVal.setText(user?.todaysBonus.toString())
                    shopsRegVal.setText(user?.totalShops.toString())

                    distanceCoveredVal.setText(user?.totalDistance.toString())
                    moneyEarnedVal.setText(user?.totalIncome.toString())
                    timeTakenVal.setText(user?.totalTime.toString())
                    bonusVal.setText(user?.totalBonus.toString())
                    agentCode = user?.agent_code.toString()
                    agentCode = user?.agent_code.toString()


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

            }
        }

        myRef.child(userId.toString()).addListenerForSingleValueEvent(postListener)
        val postListenercode  = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {
                    val user= dataSnapshot.getValue(Ag_Users::class.java)
//                    Toast.makeText(activity as Context, "${user?.username}",Toast.LENGTH_LONG).show()
//                Picasso.get().load(user?.profilepic).into(ivProfile)
//                ivProfile.setImageURI(user?.profilepic?.toUri())
                    shopsRegVal.setText(user?.totalShops.toString())
                    distanceCoveredVal.setText(user?.totalDistance.toString())
                    moneyEarnedVal.setText(user?.totalIncome.toString())
                    timeTakenVal.setText(user?.totalTime.toString())
                    bonusVal.setText(user?.totalBonus.toString())
                    agentCode = user?.agent_code.toString()
                    //...
                }
                else{
                    Toast.makeText(activity as Context, "No data", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(activity as Context, "Error loading page", Toast.LENGTH_SHORT).show()
            }
        }

        refA.child(agentCode).child("totalShops").addListenerForSingleValueEvent(postListenercode)

        return view
    }
}