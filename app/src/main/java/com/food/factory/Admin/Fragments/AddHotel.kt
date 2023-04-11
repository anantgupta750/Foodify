package com.food.factory.Admin.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.food.factory.EmployeModel
import com.food.factory.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class AddHotel : Fragment() {

    var thiscontext: Context? = null

    var imageURK : String = "";

    private lateinit var dbRef: DatabaseReference

    private lateinit var hotelName: TextInputEditText
    private lateinit var hotelLocation: TextInputEditText
    private lateinit var hotelRatting: TextInputEditText
    private lateinit var btnUpload: Button
    private lateinit var btnSaveData: Button

    private var storageReference: StorageReference? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_add_hotel, container, false)
//        return inflater.inflate(R.layout.fragment_add_hotel, container, false)
        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        thiscontext = context
        storageReference = Firebase.storage.reference;

        hotelName = view.findViewById(R.id.hotelName)
        hotelLocation = view.findViewById(R.id.hotelArea)
        hotelRatting = view.findViewById(R.id.hotelRatting)

        btnUpload = view.findViewById(R.id.uploadImage)
        btnSaveData = view.findViewById(R.id.submit)



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
        return  view
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
                val sd = thiscontext?.let { getFileName(it, imageUri!!) }

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
                            Toast.makeText(thiscontext, "Image Uploaded successfully", Toast.LENGTH_LONG).show()

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

        val hotelId = dbRef.push().key!!

        val hotel = AddHotelModel(hotelId, hotelNamee, hotelAddress,imageURK ,hotelRat)

        dbRef.child(hotelId).setValue(hotel)
            .addOnCompleteListener {
                Toast.makeText(thiscontext, "Data inserted successfully", Toast.LENGTH_LONG).show()

                hotelName.text!!.clear()
                hotelLocation.text!!.clear()
                hotelRatting.text!!.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(thiscontext, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }


}
