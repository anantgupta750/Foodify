package com.food.factory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity4 : AppCompatActivity() {


    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val hotelName = etEmpName.text.toString()
        val hotelAddress = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        if (hotelName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (hotelAddress.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter salary"
        }

        val hotelId = dbRef.push().key!!

        val hotel = EmployeModel(hotelId, hotelName, hotelAddress, empSalary)

        dbRef.child(hotelId).setValue(hotel)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}