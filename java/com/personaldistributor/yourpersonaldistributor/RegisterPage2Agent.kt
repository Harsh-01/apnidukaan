package com.personaldistributor.yourpersonaldistributor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class RegisterPage2Agent: AppCompatActivity(){
    private val cameraRequest1 = 180
    private val cameraRequest2 = 280
    private val cameraRequest3 = 380
    private val cameraRequest4 = 480
    private val cameraRequest5 = 580
    var countCode = 0
    private var pb1 = false
    private var pb2 = false
    private var pb3 = false
    private var pb4 = false
    private var pb5 = false
    private var pb6 = false

    lateinit var photoButton1: Button
    lateinit var photoButton2: Button
    lateinit var photoButton3: Button
    lateinit var photoButton4: Button
    lateinit var photoButton5: Button
    lateinit var terms: TextView
    lateinit var uri1:Uri
    lateinit var uri2:Uri
    lateinit var uri3:Uri
    lateinit var uri4:Uri
    lateinit var uri5:Uri
    lateinit var sharedMail : SharedPreferences

    lateinit var currentPhotoPath: String
    var storage = Firebase.storage
    var storageRef = storage.reference
    var profileRef = storageRef.child("profiles")
    val database = Firebase.database
    val myRef = database.getReference("Users/RoadRunners")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register3_agent)
        title = "Agent Sign-up Docs"
        sharedMail = getSharedPreferences(getString(R.string.preference_code), Context.MODE_PRIVATE)
        photoButton1 = findViewById(R.id.profile_button)
        photoButton2  = findViewById(R.id.shop_image_button)
        photoButton3  = findViewById(R.id.aadhar_button)
        photoButton4  = findViewById(R.id.contract_button)
//        photoButton5  = findViewById(R.id.check_button)
        //pb6
        terms= findViewById(R.id.terms)
        val registerButton : Button = findViewById(R.id.register_button) //Register Button

        registerButton.setOnClickListener {
            if(pb1 && pb2 && pb3 && pb4 && pb5) {
                startActivity(Intent(this, Login_Activity::class.java))
                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Please verify and upload all details.", Toast.LENGTH_SHORT).show()
            }
        }

        photoButton1.setOnClickListener {
            try {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    //Ensure that there's a camera activity to handle the intent
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        //Create the file where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            //Error occured while creating the file
                            null
                        }
                        //Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.personaldistributor.yourpersonaldistributor",
                                it
                            )
                            uri1 = photoURI
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, cameraRequest1)
                        }
                    }
                }

            }catch (e: ActivityNotFoundException){
                //display error state to the user
            }
        }
        photoButton2.setOnClickListener {
            try {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    //Ensure that there's a camera activity to handle the intent
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        //Create the file where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            //Error occured while creating the file
                            null
                        }
                        //Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.personaldistributor.yourpersonaldistributor",
                                it
                            )
                            uri2 = photoURI
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, cameraRequest2)
                        }
                    }
                }

            }catch (e: ActivityNotFoundException){
                //display error state to the user
            }

        }
        photoButton3.setOnClickListener {
            try {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    //Ensure that there's a camera activity to handle the intent
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        //Create the file where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            //Error occured while creating the file
                            null
                        }
                        //Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.personaldistributor.yourpersonaldistributor",
                                it
                            )
                            uri3 = photoURI
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, cameraRequest3)
                        }
                    }
                }

            }catch (e: ActivityNotFoundException){
                //display error state to the user
            }

        }

        photoButton4.setOnClickListener {
            try {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    //Ensure that there's a camera activity to handle the intent
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        //Create the file where the photo should go
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            //Error occured while creating the file
                            null
                        }
                        //Continue only if the File was successfully created
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this,
                                "com.personaldistributor.yourpersonaldistributor",
                                it
                            )
                            uri4 = photoURI
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, cameraRequest4)
                        }
                    }
                }

            }catch (e: ActivityNotFoundException){
                //display error state to the user
            }
        }

//        photoButton5.setOnClickListener {
//            try {
//                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//                    //Ensure that there's a camera activity to handle the intent
//                    takePictureIntent.resolveActivity(packageManager)?.also {
//                        //Create the file where the photo should go
//                        val photoFile: File? = try {
//                            createImageFile()
//                        } catch (ex: IOException) {
//                            //Error occured while creating the file
//                            null
//                        }
//                        //Continue only if the File was successfully created
//                        photoFile?.also {
//                            val photoURI: Uri = FileProvider.getUriForFile(
//                                this,
//                                "com.personaldistributor.yourpersonaldistributor",
//                                it
//                            )
//                            uri5 = photoURI
//                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//
//                            startActivityForResult(takePictureIntent, cameraRequest5)
//                        }
//                    }
//                }
//
//            }catch (e: ActivityNotFoundException){
//                //display error state to the user
//            }
//        }
        terms.setOnClickListener{
            val dialog = AlertDialog.Builder(this@RegisterPage2Agent)
//                    val dialogView = layoutInflater.inflate(R.layout.custom_dialog,null)
//                    dialog.setView(dialogView)
//                    dialog.setCancelable(false)
            dialog.setMessage("Agents!\n" +
                    "1. You will be held responsible for the cash collected by the shop-vendors. \n" +
                    "2. You will be responsible for the quality of data collected by the agents.\n" +
                    "3. You will be responsible for the shop-keeper defaulting on their payments. \n" +
                    "4. In case of a proprietary with no GST you will be responsible for making them sign a contract needs with the company.\n" +
                    "5. The image of the shopkeeper should be clear to apply face recognition for security logins.\n" +
                    "6. You will be responsible for keeping the integrity of the company brand.\n" +
                    "7. You will be responsible to timely report any changes in the status of the shops registered by you to personaldistributor.com.\n" +
                    "8. You will be responsible for your suggestions given to the product manufacturers who hired you on personaldistributor.com.")
            dialog.setPositiveButton("Agree")
            { text, listener ->

            }
            dialog.setNegativeButton("No")
            { text, listener ->
                startActivity(Intent(this, Login_Activity::class.java))
                finish()
            }

            dialog.create()
            dialog.show()
        }

    }

    //SOME ERROR HERE as intent = null
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == cameraRequest1 && resultCode == Activity.RESULT_OK){
            galleryAddPic()
            uploadFile(uri1, 0)
            Toast.makeText(this, "Uploading...please wait", Toast.LENGTH_SHORT).show()
//            photoButton1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)

//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView1.setImageBitmap(imageBitmap)
        }
        if(requestCode == cameraRequest2 && resultCode == Activity.RESULT_OK){
            galleryAddPic()
            uploadFile(uri2, 1)
            Toast.makeText(this, "Uploading...please wait", Toast.LENGTH_SHORT).show()
//            photoButton2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView2.setImageBitmap(imageBitmap)
        }
        if(requestCode == cameraRequest3 && resultCode == Activity.RESULT_OK){
            galleryAddPic()
            uploadFile(uri3, 2)
            Toast.makeText(this, "Uploading...please wait", Toast.LENGTH_SHORT).show()
//            photoButton3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView3.setImageBitmap(imageBitmap)
        }
        if(requestCode == cameraRequest4 && resultCode == Activity.RESULT_OK){
            galleryAddPic()
            uploadFile(uri4, 3)
            Toast.makeText(this, "Uploading...please wait", Toast.LENGTH_SHORT).show()
//            photoButton4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView3.setImageBitmap(imageBitmap)
        }
        if(requestCode == cameraRequest5 && resultCode == Activity.RESULT_OK){
            galleryAddPic()
            uploadFile(uri5, 4)
            Toast.makeText(this, "Uploading...please wait", Toast.LENGTH_SHORT).show()
//            photoButton5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView3.setImageBitmap(imageBitmap)
        }
    }


    private fun createImageFile(): File {
        //create an image file name
        val timestamp:String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "IMG_${timestamp}_" ,
            ".jpg",
            storageDir  /*directory*/
        ).apply {
            //Save a file path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    //Add to gallery
    private fun galleryAddPic(){
        val f = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this, arrayOf(f.toString()), arrayOf(f.name), null)
//        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
//            val f = File(currentPhotoPath)
//            mediaScanIntent.data = Uri.fromFile(f)
//            sendBroadcast(mediaScanIntent)
//        }
    }

    private fun uploadFile(uri : Uri, code:Int){
//        val file = Uri.fromFile(File(currentPhotoPath))
        val userId = sharedMail.getString("UID", "AICHYUDERTY")
        if(code == 0){
            profileRef = storageRef.child("profiles/${uri.lastPathSegment}")
        }else if(code ==1){
            profileRef = storageRef.child("pan card/${uri.lastPathSegment}")
        }else if(code ==2){
            profileRef = storageRef.child("aadhar/${uri.lastPathSegment}")
        }else if(code == 3){
            profileRef = storageRef.child("contract/${uri.lastPathSegment}")
        }else if(code == 4){
            profileRef = storageRef.child("check/${uri.lastPathSegment}")
        }

        val uploadTask = profileRef.putFile(uri)

            // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            Toast.makeText(this, "Upload Failed, check your internet connection", Toast.LENGTH_LONG).show()

        }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT).show()
        }
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            profileRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                if(code == 0){
                    photoButton1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("profilepic").setValue(downloadUri.toString())
                    pb1 = true
                }else if(code ==1){
                    photoButton2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("panCard").setValue(downloadUri.toString())
                    pb2 = true
                }else if(code ==2 && countCode == 0){
//                    photoButton3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("aadharCardFront").setValue(downloadUri.toString())
                    countCode = 1
                    Toast.makeText(this, "Verify back part of aadhar card", Toast.LENGTH_LONG).show()
                    pb3 = true
                }else if(code==2 && countCode>0){
                    photoButton3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("aadharCardBack").setValue(downloadUri.toString())
                    countCode =0
                    pb4 = true
                }
                else if(code == 3){
                    photoButton4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("contract").setValue(downloadUri.toString())
                    pb5 = true
                }else if(code == 4){
                    photoButton5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_username,0,R.drawable.ic_cloud_foreground,0)
                    myRef.child(userId.toString()).child("check").setValue(downloadUri.toString())
                    pb6 = true
                }
            }

        }

    }

    //Decode a scales image - dont know where to use
//    private fun setPic(imageView: ImageView){
//        //Get the dimensions of the view
//        val targetW: Int = imageView.width
//        val targetH: Int = imageView.height
//
//        val bmOptions = BitmapFactory.Options().apply{
//            //Get the dimensions of the bitmap
//            inJustDecodeBounds = true
//
//            BitmapFactory.decodeFile(currentPhotoPath, this)
//
//            val photoW: Int = outWidth
//            val photoH: Int = outHeight
//            //Determine how much to scale down the image
//            val scaleFactor : Int = Math.max(1, Math.min(photoW/targetW, photoH/targetH))
//
//            //Decode the image file into a Bitmap sized to fill the view
//            inJustDecodeBounds = false
//            inSampleSize = scaleFactor
//            inPurgeable = true
//        }
//        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also{bitmap ->
//            imageView.setImageBitmap(bitmap)
//        }
//    }

}