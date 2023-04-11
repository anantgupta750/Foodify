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
import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
import com.food.factory.Admin.Fragments.Adapter.OrderAdapter
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.Admin.OrderDetail
import com.food.factory.R
import com.food.factory.User.Model.OrderModel
import com.food.factory.Variable
import com.google.firebase.database.*

class OrderList : Fragment() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var menuList: ArrayList<OrderModel>
    private lateinit var dbRef: DatabaseReference
    var thiscontext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View =  inflater.inflate(R.layout.fragment_order_list, container, false)
        thiscontext = context

        menuRecyclerView = view.findViewById(R.id.menuRv)
        menuRecyclerView.layoutManager = LinearLayoutManager(thiscontext)
        menuRecyclerView.setHasFixedSize(true)
        tvLoadingData = view.findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<OrderModel>()

        filterData()
        return  view

    }

    private fun filterData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Order")

//        databaseReference.

        databaseReference.addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(OrderModel::class.java)
                        menuList.add(menuitem!!)
                    }
                    val mAdapter = OrderAdapter(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : OrderAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(thiscontext, OrderDetail::class.java)
                            //put extras
                            Variable.orderId = menuList[position].orderId
                            intent.putExtra("empId", menuList[position].orderId)
                            context!!.startActivity(intent)
//                            Toast.makeText(thiscontext,"Touched", Toast.LENGTH_SHORT).show()
                        }

                    })

                    menuRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}