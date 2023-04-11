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
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.MenuModel
import com.food.factory.R
import com.food.factory.Variable
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MenuEdit : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    var imageURK : String = "";

    private lateinit var menuName: TextInputEditText
    private lateinit var menuPrice: TextInputEditText
    private lateinit var menuRatting: TextInputEditText
    private lateinit var menuDescription: TextInputEditText
    private lateinit var btnUpload: Button
    private lateinit var menuList: ArrayList<AddMenuModel>

    private lateinit var btnSaveData: Button
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_edit)
        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

//        thiscontext = context
        storageReference = Firebase.storage.reference;

        menuName = findViewById(R.id.dishName)
        menuPrice = findViewById(R.id.dishPrice)
        menuDescription = findViewById(R.id.dishdiscription)
        menuRatting = findViewById(R.id.dishRatting)

        btnUpload = findViewById(R.id.dishuploadImage)
        btnSaveData = findViewById(R.id.submit)

        btnUpload.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            // here item is type of image
            galleryIntent.type = "image/*"
            // ActivityResultLauncher callback
            imagePickerActivityResult.launch(galleryIntent)
        }
        btnSaveData.setOnClickListener {
            saveMenuData(intent.getStringExtra("menuId").toString()) }
//        filterData(                intent.getStringExtra("menuId").toString(),
//        )

//        menuName.text = intent.getStringExtra("menuId").toString()

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


    private fun filterData(toString: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Menu")


        databaseReference.orderByChild("menuId").equalTo(toString).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(AddMenuModel::class.java)
                        menuList.add(menuitem!!)

//                        menuName.text = menuList.get(i).menuName
                    }

//                        Toast.makeText(applicationContext, "Data inserted "+menuModelList.get(0).menuDescription, Toast.LENGTH_LONG).show()
//                        Toast.makeText(applicationContext, "Data inserted "+menuModelList.get(1).menuDescription, Toast.LENGTH_LONG).show()
//
//                    Toast.makeText(applicationContext, "Data inserted "+menuModelList.size, Toast.LENGTH_LONG).show()
//                    Log.e("Menu Model List", "onDataChange: "+menuModelList )
//                    Log.e("Menu Model List", "onDataChange: "+menuModelList.size )
//                    studentAdapter.submitList(studentsList)
//                    binding.recyclerStudents.adapter = studentAdapter
                } else{
                    Toast.makeText(applicationContext, "Data is not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun saveMenuData(toString: String) {

        //getting values
        val menuNamee = menuName.text.toString()
        val menuPric = menuPrice.text.toString()
        val menuDes = menuDescription.text.toString()
        val menuRat = menuRatting.text.toString()
//        = imageUri

        if (menuNamee.isEmpty()) {
            menuName.error = "Please enter name"
        }
        if (menuDes.isEmpty()) {
            menuDescription.error = "Please enter location"
        }
        if (menuRat.isEmpty()) {
            menuRatting.error = "Please enter ratting"
        }
        if (menuPric.isEmpty()) {
            menuPrice.error = "Please enter ratting"
        }

        val menuId = toString!!

        val menu = AddMenuModel(Variable.hotelId,Variable.sub_menu_Id,menuId, menuNamee,menuPric,imageURK,menuDes,menuRat)

        dbRef.child(menuId).setValue(menu)
            .addOnCompleteListener {
                Toast.makeText(this, "Data Updated", Toast.LENGTH_LONG).show()

                menuName.text!!.clear()
                menuPrice.text!!.clear()
                menuRatting.text!!.clear()
                menuDescription.text!!.clear()

                finish()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}