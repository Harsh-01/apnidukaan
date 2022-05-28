package com.personaldistributor.yourpersonaldistributor

import android.content.Intent
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
import com.google.firebase.ktx.Firebase
import com.personaldirtributor.yourpersonaldistributor.SecondFragment
import com.personaldistributor.yourpersonaldistributor.fragments.*
import com.personaldistributor.yourpersonaldistributor.util.SharedPreferencesUtils
import kotlinx.android.synthetic.main.activity_agent_login.*

class VendorLogin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var frameLayout: FrameLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_login)
//        supportActionBar?.hide()
        setUpNavigationDrawer()
        auth = Firebase.auth


        val firstVfragment = FirstVFragment()
        val secondVFragment = SecondVFragment()
        val thirdVFragment = ThirdVFragment()
        val fourthFragment = FourthFragment()

        title = "Proud Shop-Owner!"

        setCurrentFragment(fourthFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener() {
            when(it.itemId){
                R.id.person->setCurrentFragment(firstVfragment)
                R.id.home->setCurrentFragment(secondVFragment)
                R.id.settings->setCurrentFragment(thirdVFragment)
                R.id.search->setCurrentFragment(fourthFragment)
            }
            true
        }


    }
    private fun setCurrentFragment(fragment : Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flfragment, fragment)
            commit()
        }
    private fun setUpNavigationDrawer(){
        toggle = ActionBarDrawerToggle(this,dl_drawerlayout,R.string.open,R.string.close)
        dl_drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        Gravity.START
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val chatFragment = ChatFragment()
        val enquiryFragment = EnquiryFragment()

        nv_navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.chat1 -> {
                    setCurrentFragment(chatFragment)
                    dl_drawerlayout.closeDrawers()
                }
                R.id.viewPost -> {Toast.makeText(this,"post button", Toast.LENGTH_SHORT).show()
                    dl_drawerlayout.closeDrawers()}
                R.id.inquiry1 -> {setCurrentFragment(enquiryFragment)
                    dl_drawerlayout.closeDrawers()}

                R.id.logout ->{
                    val dialog = AlertDialog.Builder(this@VendorLogin)
                    dialog.setMessage("Are you sure to Logout")
                    dialog.setPositiveButton("Yes")
                    {
                            text, listener -> Firebase.auth.signOut()
                        SharedPreferencesUtils.removeAllUserDefaults(this@VendorLogin)
                        startActivity(Intent(this, Login_Activity::class.java))
                        finish()
                    }
                    dialog.setNegativeButton("No")
                    {
                            text, listener ->



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
