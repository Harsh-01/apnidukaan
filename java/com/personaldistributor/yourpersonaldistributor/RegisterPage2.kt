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
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class RegisterPage2: AppCompatActivity(){
    private val cameraRequest1 = 18
    private val cameraRequest2 = 28
    private val cameraRequest3 = 38
    lateinit var photoButton1: Button
    lateinit var photoButton2: Button
    lateinit var photoButton3: Button

    var countCode = 0
    private var pb1 = false
    private var pb2 = false
    private var pb3 = false
    private var pb4 = false

    lateinit var uri1:Uri
    lateinit var uri2:Uri
    lateinit var uri3:Uri

    lateinit var currentPhotoPath: String
    lateinit var sharedMail1 : SharedPreferences

    var storage = Firebase.storage
    var storageRef = storage.reference
    var profileRef = storageRef.child("profiles")
    val database = Firebase.database
    val myRef = database.getReference("Users/ShopOwners")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register3)
        sharedMail1 = getSharedPreferences(getString(R.string.preference_code1), Context.MODE_PRIVATE)
        title = " Shop Owner Sign-up Docs"

        photoButton1 = findViewById(R.id.profile_button)
        photoButton2 = findViewById(R.id.shop_image_button)
        photoButton3 = findViewById(R.id.aadhar_button)

        val registerButton : Button = findViewById(R.id.register_button) //Register Button


        registerButton.setOnClickListener {
            if(pb1 && pb2 && pb3 && pb4) {
                val dialog = AlertDialog.Builder(this@RegisterPage2)
                dialog.setMessage("Please pay Rs. 200 to the agent to complete registration")
                dialog.setPositiveButton("Proceed To Pay")
                { text, listener ->
                    startActivity(Intent(this, PayWindow::class.java))


                }
                dialog.create()
                dialog.show()
            }
            else{
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
        val userId = sharedMail1.getString("UID", "AICHYUDERTY")
        if(code == 0){
            profileRef = storageRef.child("profiles/${uri.lastPathSegment}")
        }else if(code ==1){
            profileRef = storageRef.child("shopImage/${uri.lastPathSegment}")
        }else if(code ==2){
            profileRef = storageRef.child("aadhar/${uri.lastPathSegment}")
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
                    myRef.child(userId.toString()).child("shop_pic").setValue(downloadUri.toString())
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

            }

        }
//        if(urlTask.isComplete) {
//            checkSum  = checkSum + (code+1)
//            print(checkSum)
//        }


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