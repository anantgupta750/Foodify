package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity3 : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<EmployeModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<EmployeModel>()
        getEmployeesData()
    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(EmployeModel::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {


//                            val intent = Intent(this@MainActivity3, BarCodeGenrator::class.java)
                            val intent = Intent(this@MainActivity3, MainActivity5::class.java)
                            //put extras
                            intent.putExtra("empId", empList[position].hotelId)
                            intent.putExtra("empName", empList[position].hotelName)
                            intent.putExtra("empAge", empList[position].hotelAge)
                            intent.putExtra("empSalary", empList[position].hotelSalary)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }}