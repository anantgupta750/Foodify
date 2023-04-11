package com.food.factory.Admin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.food.factory.R
import com.food.factory.Variable
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class EditHotelDetail : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    var imageURK : String = "";

    private lateinit var hotelName: TextInputEditText
    private lateinit var hotelLocation: TextInputEditText
    private lateinit var hotelRatting: TextInputEditText
    private lateinit var btnUpload: Button
    private lateinit var btnSaveData: Button

    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_hotel_detail)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

//        thiscontext = context
        storageReference = Firebase.storage.reference;

        hotelName = findViewById(R.id.hotelName)
        hotelLocation = findViewById(R.id.hotelArea)
        hotelRatting = findViewById(R.id.hotelRatting)

        btnUpload = findViewById(R.id.uploadImage)
        btnSaveData = findViewById(R.id.submit)

        btnUpload.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            // here item is type of image
            galleryIntent.type = "image/*"
            // ActivityResultLauncher callback
            imagePickerActivityResult.launch(galleryIntent)
        }


        btnSaveData.setOnClickListener {
//            Toast.makeText(thiscontext,"hiii"+hotelName.text.toString(),Toast.LENGTH_SHORT).show()
            saveHotelData()
        }

    }


    private var imagePickerActivityResult: ActivityResultLauncher<Intent> =
    // lambda expression to receive a result back, here we
        // receive single item(photo) on selection
        registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
            if (result != null) {
                // getting URI of selected Image
                var imageUri: Uri? = result.data?.data

                // val fileName = imageUri?.pathSegments?.last()

                // extract the file name with extension
                val sd =  { getFileName(this, imageUri!!) }

                // Upload Task with upload to directory 'file'
                // and name of the file remains same
                val uploadTask = imageUri?.let { storageReference!!.child("file/$sd").putFile(it) }

                // On success, download the file URL and display it
                if (uploadTask != null) {
                    uploadTask.addOnSuccessListener {

                        Log.e("Image Uri", ": "+imageUri )
                        // using glide library to display the image
                        storageReference!!.child("file/$sd").downloadUrl.addOnSuccessListener {
                            //                        Glide.with(this@MainActivity)
                            //                            .load(it)
                            //                            .into(imageview)

                            imageURK = it.toString()
                            Toast.makeText(this, "Image Uploaded successfully", Toast.LENGTH_LONG).show()

                            Log.e("Firebase", "download passed" +it)
                        }.addOnFailureListener {
                            Log.e("Firebase", "Failed in downloading")
                        }
                    }.addOnFailureListener {
                        Log.e("Firebase", "Image Upload fail")
                    }
                }
            }
        }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    private fun saveHotelData() {

        //getting values
        val hotelNamee = hotelName.text.toString()
        val hotelAddress = hotelLocation.text.toString()
        val hotelRat = hotelRatting.text.toString()
//        = imageUri

        if (hotelNamee.isEmpty()) {
            hotelName.error = "Please enter name"
        }
        if (hotelAddress.isEmpty()) {
            hotelLocation.error = "Please enter location"
        }
        if (hotelRat.isEmpty()) {
            hotelRatting.error = "Please enter ratting"
        }

        val hotelId = Variable.hotelId!!

        val hotel = AddHotelModel(hotelId, hotelNamee, hotelAddress,imageURK ,hotelRat)

        dbRef.child(hotelId).setValue(hotel)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                hotelName.text!!.clear()
                hotelLocation.text!!.clear()
                hotelRatting.text!!.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}