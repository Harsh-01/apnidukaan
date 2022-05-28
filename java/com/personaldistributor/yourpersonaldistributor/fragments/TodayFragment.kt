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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.personaldistributor.yourpersonaldistributor.Ag_Users

import com.personaldistributor.yourpersonaldistributor.R
import org.w3c.dom.Text
import android.widget.Spinner as Spinner


class TodayFragment : Fragment() {


    val TAG = "DatabaseRead"
    private lateinit var auth: FirebaseAuth
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
        val view =inflater.inflate(R.layout.fragment_today, container, false)
        sharedLoginId = (activity as Context).getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        sharedLoginId = (activity as Context).getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        shopsRegVal = view.findViewById(R.id.shopsRegVal)
        distanceCoveredVal = view.findViewById(R.id.distanceCoveredVal)
        moneyEarnedVal = view.findViewById(R.id.moneyEarnedVal)
        timeTakenVal = view.findViewById(R.id.timeTakenVal)
        bonusVal = view.findViewById(R.id.bonusVal)

        val userId = sharedLoginId.getString("LoginId", "TEMPORARYUID")
        val myRef = database.getReference("Users/RoadRunners")
        var refA = database.getReference("Users/AgCodes")


        // Inflate the layout for this fragment
//        var default_option = resources.getStringArray((R.array.defaultMode))
//        //access the spinner
//        val defSpinner = view?.findViewById<Spinner>(R.id.spinnerA)
//        if (defSpinner != null) {
//            val adapter =
//                ArrayAdapter(activity as Context, android.R.layout.simple_spinner_item, default_option)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            defSpinner?.adapter = adapter
//
//            defSpinner?.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View,
//                    position: Int,
//                    id: Long
//                ) {
//
//                    if (default_option[position] == "Shop pay") {
//                        Handler().postDelayed({
//                            Toast.makeText(
//                                activity as Context,
//                                getString(R.string.selected_item) + " " + " " + default_option[position],
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }, 1000)
//
//
//                    } else if (default_option[position] == "Product pay") {
//                        Handler().postDelayed({
//                            Toast.makeText(
//                                activity as Context,
//                                getString(R.string.selected_item) + " " + " " + default_option[position],
//                                Toast.LENGTH_SHORT
//                            ).show()
//
//                        }, 1000)
//                        //Add Intents
//
//                    }else if (default_option[position] == "Deposit pay") {
//                        Handler().postDelayed({
//                            Toast.makeText(
//                                activity as Context,
//                                getString(R.string.selected_item) + " " + " " + default_option[position],
//                                Toast.LENGTH_SHORT
//                            ).show()
//
//                        }, 1000)
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    Handler().postDelayed({
//                        Toast.makeText(
//                            activity as Context,
//                            "Welcome to Payment Window page",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }, 1000)
//                }
//
//            }
//        }
        var agentCode = ""

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {
                    val user= dataSnapshot.getValue(Ag_Users::class.java)
//                    Toast.makeText(activity as Context, "${user?.username}",Toast.LENGTH_LONG).show()
//                Picasso.get().load(user?.profilepic).into(ivProfile)
//                ivProfile.setImageURI(user?.profilepic?.toUri())
                    shopsRegVal.setText(user?.todayShops.toString())
                    distanceCoveredVal.setText(user?.todayDistance.toString())
                    moneyEarnedVal.setText(user?.todaysIncome.toString())
                    timeTakenVal.setText(user?.todaysTime.toString())
                    bonusVal.setText(user?.todaysBonus.toString())
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
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        myRef.child(userId.toString()).addListenerForSingleValueEvent(postListener)
        val postListenercode = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {
                    var totalShops = dataSnapshot.childrenCount
//
                    //...
                }
                else{
//                    Toast.makeText(activity as Context, "No data", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(activity as Context, "${userId.toString()}", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Toast.makeText(activity as Context, "Error loading page", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        refA.child(agentCode).child("totalShops").addListenerForSingleValueEvent(postListenercode)
        return view


    }

}