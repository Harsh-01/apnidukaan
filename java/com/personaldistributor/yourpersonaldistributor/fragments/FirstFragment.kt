package com.personalditributor.yourpersonaldistributor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.personaldistributor.yourpersonaldistributor.*
import com.personaldistributor.yourpersonaldistributor.fragments.FirstVFragment
import com.personaldistributor.yourpersonaldistributor.models.Place
import com.personaldistributor.yourpersonaldistributor.models.UserMap
import com.personaldistributor.yourpersonaldistributor.util.Utility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_agent_details.*
import kotlinx.android.synthetic.main.fragment_first.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.lang.Exception
import java.lang.StringBuilder


class FirstFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database

    //    val userId = myRef.push().key
//    val userId = FirebaseAuth.getInstance().currentUser!!.uid
//    val myRef = database.getReference("Users/RoadRunners").child(userId)
    val TAG = "DatabaseRead"

    private lateinit var mMap : GoogleMap
    private var mapReady = false
    lateinit var ivProfile: ImageView
    lateinit var ivname : TextView
    lateinit var ivaddress: TextView
    lateinit var sharedLoginId : SharedPreferences
    lateinit var sharedName : SharedPreferences
    lateinit var sharedAddress : SharedPreferences
    lateinit var viewProfile : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        sharedName = (activity as Context).getSharedPreferences(getString( R.string.preference_code_name), Context.MODE_PRIVATE)
        sharedAddress = (activity as Context).getSharedPreferences(getString( R.string.preference_code_address), Context.MODE_PRIVATE)
        sharedLoginId = (activity as Context).getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        val userId = sharedLoginId.getString("LoginId", "TEMPORARYUID")
//        Initialize Firebase Auth
        auth = Firebase.auth
//        val userId = auth.currentUser?.uid
//        Toast.makeText(activity as Context, "${userId}", Toast.LENGTH_SHORT).show()
        val myRef = database.getReference("Users/RoadRunners")
        myRef.child(userId as String).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity , "Error loading page", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChildren()){
                    val user= snapshot.getValue(Ag_Users::class.java)
//                    name.text = user?.username
//                    agentCode.text = user?.agent_code
//                    email.text = user?.email
//                    phone.text = user?.mobile
                    shoptotal1.text = user?.totalShops
//                    Toast.makeText(this@AgentDetails, user?.username, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, user?.username as String)
                }
            }

        })
//        var Uname = sharedName.getString("AgName", "Adarsh Tiwari")
//        var Uaddress = sharedAddress.getString("AgAdd", "Sultanpur")
//        val userMaps = generateSampleData()
//        //set layout manager on the recycler view
//        rvMaps?.layoutManager = LinearLayoutManager(context)
//
//        //set adapter on the recycler view
//        rvMaps?.adapter = MapsAdapter(context as Context, userMaps, object : MapsAdapter.OnClickListener{
//            override fun onItemClick(position: Int) {
//                //when user taps on view in RV, navigate to new activity
//                val intent  = Intent(context, DisplayMapsActivity::class.java)
//                startActivity(intent)
//            }
//
//        })




        ivProfile = view.findViewById(R.id.ivProfile)
        ivname = view.findViewById(R.id.tv_name)
        ivaddress = view.findViewById(R.id.textView)
        viewProfile = view.findViewById(R.id.viewProfile)
        viewProfile.setOnClickListener{
            val intent = Intent(activity as Context,AgentDetails::class.java)
            startActivity(intent)
        }

//        ivname.setText(Uname)
//        ivaddress.setText(Uaddress)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.hasChildren()) {

                val user= dataSnapshot.getValue(Ag_Users::class.java)
//                Toast.makeText(activity as Context, "${user?.username}",Toast.LENGTH_LONG).show()
//                Picasso.get().load(user?.profilepic).into(ivProfile)
//                ivProfile.setImageURI(user?.profilepic?.toUri())
                ivname.setText(user?.username.toString())
                ivaddress.setText(user?.address.toString())
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

        ivProfile.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(activity as Context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, FirstFragment.PERMISSION_CODE);
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

        Thread {
            val mapFragment =
                childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                mMap = googleMap
                mapReady = true
                updateMap()
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


    private fun updateMap() {
        Utility.showProgressDialog(activity)
        if(mapReady && checklocationpermission()){
            val newDelhi = LatLng(28.6139, 77.2090)
            val lucknow = LatLng(26.8467, 80.9462)
            val delhiJunc = LatLng(28.6610, 77.2277)
            mMap.addMarker(MarkerOptions().position(newDelhi).title("Marker in New Delhi"))
            mMap.addMarker(MarkerOptions().position(delhiJunc).title("Marker in Delhi Junction"))
            mMap.addMarker(MarkerOptions().position(lucknow).title("Marker in Lucknow"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(delhiJunc, 10f))
            Utility.hideProgressDialog()
//            val URL = getDirectionURL(newDelhi, delhiJunc)
//            GetDirection(URL).execute()
        }
    }

    fun getDirectionURL(origin: LatLng, dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyC2ihU26DcqLB-ToftPyErHQDJJwpFTz8w"
    }

    inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg p0: Void?): List<List<LatLng>> {
            //Network Call
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            //Getting the result by making new call on the client and storing it into your value called response
            val response = client.newCall(request).execute()
            //From response getting the body and storing it into value called data
            val data = response.body!!.string()
            val result = ArrayList<List<LatLng>>()
            try {
                //Using Gson creating GoogleMapDTO object from Json data
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
                //creating list of LatLng called path in this we need to add all start & end locations
                val path = ArrayList<LatLng>()

                for(i in 0..(respObj.routes[0].legs[0].steps.size -1)){
                    //creating a LatLng object using start location lat and lng
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble(),
//                        respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble(),
//                        respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())

                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            //Set this result as PolyLine for the google maps
            val lineoption = PolylineOptions()
            for(i in result.indices){           //Looping the result object
                //Creating polyline option and adding list of LatLng to it.

                lineoption.addAll(result[i])
                //Specifying width as 10f and color as blue
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            //Finally adding this line option as Poly Line for the googleMap
            mMap.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    fun checklocationpermission(): Boolean{
        var state = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity as Context,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity as Context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){

                state = true
            }else {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            }
        }else state  = true

        return state
    }

}