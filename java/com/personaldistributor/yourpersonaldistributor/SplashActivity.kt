package com.personaldistributor.yourpersonaldistributor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.personaldistributor.yourpersonaldistributor.util.SharedPreferencesUtils
import java.util.*

class SplashActivity : AppCompatActivity() {
    //    var splashSound: MediaPlayer? = null
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("Users/Login")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        supportActionBar?.hide()
        val backgroundImg: ImageView = findViewById(R.id.splashLogo)
        val backgroundTxt: TextView = findViewById(R.id.textP)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        backgroundImg.startAnimation(slideAnimation)
        val slideAnimationText = AnimationUtils.loadAnimation(this, R.anim.slide_1)
        backgroundTxt.startAnimation(slideAnimationText)
        val backgroundTxt1: TextView = findViewById(R.id.textD)
        val slideAnimationText1 = AnimationUtils.loadAnimation(this, R.anim.slide_2)
        backgroundTxt1.startAnimation(slideAnimationText1)
//       val splashsound = MediaPlayer.create (this,R.raw.droid1)
//        splashsound.start()


        Handler().postDelayed({
//
//            splashLogo.animate().apply {
//                duration = 2000
//                rotationYBy(360f)
//            }.withEndAction{
//                splashLogo.animate().apply {
//                    duration = 1000
//                    rotationYBy(3600f)
//
//                }.start()
//            }

            val loginTime = SharedPreferencesUtils.getStringFromUserDefaults(
                this@SplashActivity,
                Login_Activity.LOGIN_TIME
            )
            loginTime.let {
                if (it != null) {
                    if (it.isNotBlank()) {
                        val currentTime = Login_Activity.timeFormat.format(Date())
                        val date1 = Login_Activity.timeFormat.parse(it)
                        val date2 = Login_Activity.timeFormat.parse(currentTime)
                        val mills = date2.time - date1.time
                        val hours = (mills / (1000 * 60 * 60)).toInt()
                        Log.e("HourDiff", hours.toString())
                        if (hours >= 12) {
                            if (SharedPreferencesUtils.getBooleanFromUserDefaults(this@SplashActivity, Login_Activity.IS_AGENT)) {
                                val current_time = System.currentTimeMillis()
                                val uid = auth.currentUser?.uid
                                myRef.child(uid.toString()).child("last_logout") .setValue(current_time)
                                logOutDefaultUser();
                            }
                        }
                    }
                }
            }
            val intent = Intent(this@SplashActivity, Login_Activity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    private fun logOutDefaultUser() {
        Log.e("Call", "Logout Default user")
//        SharedPreferencesUtils.removeStringToUserDefaults(this@SplashActivity, Login_Activity.FIREBASE_TOKEN)
        SharedPreferencesUtils.removeAllUserDefaults(this@SplashActivity)
    }
//    override fun onPause() {
//        super.onPause()
//        splashSound!!.release()
//        finish()
//    }

}
