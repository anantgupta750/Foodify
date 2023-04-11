package com.food.factory.Admin.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.*
import com.food.factory.Admin.Fragments.Adapter.HotelListAdapter
import com.food.factory.Admin.Fragments.Model.AddHotelModel
import com.food.factory.R
import com.google.firebase.database.*

class HotelList : Fragment() {

    private lateinit var hotelRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var hotelList: ArrayList<AddHotelModel>
    private lateinit var dbRef: DatabaseReference
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_hotel_list, container, false)
        thiscontext = context

        hotelRecyclerView = view.findViewById(R.id.hotelRv)
        hotelRecyclerView.layoutManager = LinearLayoutManager(thiscontext)
        hotelRecyclerView.setHasFixedSize(true)
        tvLoadingData = view.findViewById(R.id.tvLoadingData)

        hotelList = arrayListOf<AddHotelModel>()
        getEmployeesData()

        return  view
    }

    private fun getEmployeesData() {

        hotelRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hotelList.clear()
                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val hotelData = empSnap.getValue(AddHotelModel::class.java)
                        hotelList.add(hotelData!!)
                    }
                    val mAdapter = HotelListAdapter(hotelList)
                    hotelRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : HotelListAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {


//                            Toast.makeText(thiscontext,"Touched",Toast.LENGTH_SHORT).show()
//                            val intent = Intent(thiscontext, BarCodeGenrator::class.java)
//                            val intent = Intent(thiscontext, HotelDetail::class.java)
//                            //put extras
//                            Variable.hotelId = hotelList[position].hotelId
//                            intent.putExtra("empId", hotelList[position].hotelId)
////                            intent.putExtra("empName", empList[position].hotelName)
////                            intent.putExtra("empAge", empList[position].hotelAge)
////                            intent.putExtra("empSalary", empList[position].hotelSalary)
//                            startActivity(intent)
                        }

                    })

                    hotelRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    }