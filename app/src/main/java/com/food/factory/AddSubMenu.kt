package com.food.factory

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.food.factory.Admin.Fragments.Model.SaveSubMenu
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class AddSubMenu : Fragment() {

    var thiscontext: Context? = null

    var imageURK : String = "";

    private lateinit var dbRef: DatabaseReference

    private lateinit var subMenuName: TextInputEditText
    private lateinit var subMenuType: TextInputEditText
    private lateinit var btnUpload: Button
    private lateinit var btnSaveData: Button

    private var storageReference: StorageReference? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_sub_menu, container, false)
        dbRef = FirebaseDatabase.getInstance().getReference("Sub_Menu")

        thiscontext = context
        storageReference = Firebase.storage.reference;

        subMenuName = view.findViewById(R.id.subMenuName)
        subMenuType = view.findViewById(R.id.subMenuType)
//        hotelRatting = view.findViewById(R.id.hotelRatting)

        btnUpload = view.findViewById(R.id.subMenuImage)
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
            saveSubMenuData()
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

    private fun saveSubMenuData() {

        //getting values
        val hotelNamee = subMenuName.text.toString()
        val hotelAddress = subMenuType.text.toString()
//        val hotelRat = hotelRatting.text.toString()
//        = imageUri

        if (hotelNamee.isEmpty()) {
            subMenuName.error = "Please enter name"
        }
        if (hotelAddress.isEmpty()) {
            subMenuType.error = "Please enter location"
        }
//        if (hotelRat.isEmpty()) {
//            hotelRatting.error = "Please enter ratting"
//        }

        val subId = dbRef.push().key!!

        val hotel = SaveSubMenu(Variable.hotelId,subId, hotelNamee, hotelAddress,imageURK )

        dbRef.child(subId).setValue(hotel)
            .addOnCompleteListener {
                Toast.makeText(thiscontext, "Data inserted successfully", Toast.LENGTH_LONG).show()

                subMenuName.text!!.clear()
                subMenuType.text!!.clear()
//                hotelRatting.text!!.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(thiscontext, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }


}