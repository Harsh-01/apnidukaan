package com.personaldistributor.yourpersonaldistributor

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.personaldirtributor.yourpersonaldistributor.SecondFragment
import com.personaldistributor.yourpersonaldistributor.fragments.*
import com.personaldistributor.yourpersonaldistributor.util.SharedPreferencesUtils
import com.personalditributor.yourpersonaldistributor.FirstFragment
import kotlinx.android.synthetic.main.activity_agent_login.*
import kotlinx.android.synthetic.main.toolbar.*

class AgentLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var frameLayout: FrameLayout
    lateinit var toggle: ActionBarDrawerToggle
    var previousMenuItem: MenuItem? = null
    val database = Firebase.database
    val myRef = database.getReference("Users/Login")
    val updateVendorListRef = database.getReference("Users/RoadRunners")
    lateinit var sharedMail : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_login)
        // supportActionBar?.hide()
        setUpNavigationDrawer()
        updateVendorList()
        auth = Firebase.auth


        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()
        val fourthFragment = FourthFragment()

        setCurrentFragment(fourthFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.person -> setCurrentFragment(firstFragment)
                R.id.home -> setCurrentFragment(secondFragment)
                R.id.settings -> setCurrentFragment(thirdFragment)
                R.id.search -> setCurrentFragment(fourthFragment)
            }
            true
        }


    }

    private fun updateVendorList() {
        updateVendorListRef.get().addOnCompleteListener{
            if(it.isSuccessful){
                val loginId = SharedPreferencesUtils.getStringFromUserDefaults(
                    this@AgentLogin,
                    Login_Activity.LOGIN_ID
                )
                val agentDatas = it.result;
                val datas = agentDatas?.children
                datas?.forEach { it1->
                    val key = it1.key
                    if(key.equals(loginId)){
                        val userData = it1.children
                        userData.forEach { dataSnap->
                            if(dataSnap.key.equals("agent_code")){
//                                Log.e("AgentCode", dataSnap.value as String)
//                                val shopCount = getAgentsShopCount(dataSnap.value as String)
//                                Log.e("ShopCount1", shopCount.toString())

                                val refAgentCodes = database.getReference("Users/AgCodes")
                                refAgentCodes.child(dataSnap.value as String).get().addOnCompleteListener{ it ->
                                    val data = it.result;
                                    var totalChildren = data?.childrenCount as Long
                                    updateShopCount(key,totalChildren);
                                }


                            }
                        }
                    }
                }
            }
        }


    }

    private fun updateShopCount(key: String?, totalChildren: Long) {
        key?.let {
            updateVendorListRef.child(it).child("monthlyShops").setValue(totalChildren.toString())
            updateVendorListRef.child(it).child("totalShops").setValue(totalChildren.toString())
            updateVendorListRef.child(it).child("todayShops").setValue(totalChildren.toString())
            updateVendorListRef.child(it).child("weeklyShops").setValue(totalChildren.toString())
        }
    }



    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, fragment)
            commit()
        }

    private fun setUpNavigationDrawer() {
        toggle = ActionBarDrawerToggle(this, dl_drawerlayout, R.string.open, R.string.close)
        dl_drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        Gravity.START
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val chatFragment = ChatFragment()
        val enquiryFragment = EnquiryFragment()
//        val postFragment2 = PostFragment2

        nv_navView.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it
            when (it.itemId) {
                R.id.chat1 -> {
                    setCurrentFragment(chatFragment)
                    dl_drawerlayout.closeDrawers()
                }
                R.id.viewPost -> {
                    setCurrentFragment(enquiryFragment)
                    dl_drawerlayout.closeDrawers()
                }
                R.id.replacement ->{
                    setCurrentFragment(enquiryFragment)
                    dl_drawerlayout.closeDrawers()
                }

                R.id.inquiry1 -> {
                    setCurrentFragment(enquiryFragment)
                    dl_drawerlayout.closeDrawers()
                }

                R.id.logout -> {
                    val dialog = AlertDialog.Builder(this@AgentLogin)
                    dialog.setMessage("Are you sure to Logout")
                    dialog.setPositiveButton("Yes")
                    { text, listener ->

                        val current_time = System.currentTimeMillis()

                        if (SharedPreferencesUtils.getBooleanFromUserDefaults(
                                this@AgentLogin,
                                Login_Activity.IS_AGENT
                            )
                        ) {
                            val uid = auth.currentUser?.uid
                            myRef.child(uid.toString()).child("last_logout").setValue(current_time)
                        }

                        Firebase.auth.signOut()
                        SharedPreferencesUtils.removeAllUserDefaults(this@AgentLogin)
                        startActivity(Intent(this, Login_Activity::class.java))
                        finish()
                    }
                    dialog.setNegativeButton("No")
                    { text, listener ->


                    }
                    dialog.create()
                    dialog.show()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
