package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity5 : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnFilter: Button
    private lateinit var menuModelList: ArrayList<MenuModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        initView()
        setValuesToViews()

        menuModelList = arrayListOf<MenuModel>()

        btnFilter.setOnClickListener{
            filterData(                intent.getStringExtra("empId").toString(),
            )
        }

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun filterData(toString: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Menu")


            databaseReference.orderByChild("hotelId").equalTo(toString).addValueEventListener(object:
                ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val female = i.getValue(MenuModel::class.java)
                        menuModelList.add(female!!)
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

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnFilter = findViewById(R.id.filter)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpSalary.text = intent.getStringExtra("empSalary")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity3::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        empName: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Updating $empName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etEmpName.text.toString()
            tvEmpAge.text = etEmpAge.text.toString()
            tvEmpSalary.text = etEmpSalary.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(


        id: String,
        name: String,
        age: String,
        salary: String
    ) {




        val dbRef = FirebaseDatabase.getInstance().getReference("Menu")
//        val dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child("menu")
//        val dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child("menu")



        val menuId = dbRef.push().key!!



//        val dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child(id).child("menu")
        val empInfo = MenuModel(menuId,id, name, age, salary)


        dbRef.child(menuId).setValue(empInfo)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

//                etEmpName.text.clear()
//                etEmpAge.text.clear()
//                etEmpSalary.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

//        dbRef.setValue(empInfo)
    }

}