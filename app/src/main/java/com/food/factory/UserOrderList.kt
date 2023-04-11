package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.Admin.AdminActivity
import com.food.factory.Admin.Fragments.Adapter.OrderAdapter
import com.food.factory.Admin.OrderDetail
import com.food.factory.Chat.ChatActivity
import com.food.factory.User.Chat
import com.food.factory.User.Model.OrderModel
import com.food.factory.User.UserOrderAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserOrderList : AppCompatActivity() {
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var noDataText: TextView
    private lateinit var noDataImage: ImageView
    private lateinit var menuList: ArrayList<OrderModel>
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_list)
        menuRecyclerView = findViewById(R.id.userOrderRv)
        noDataImage = findViewById(R.id.nodata)
        noDataText = findViewById(R.id.nodataText)
        menuRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        menuRecyclerView.setHasFixedSize(true)
        auth=FirebaseAuth.getInstance()

        menuList = arrayListOf<OrderModel>()

        if (menuList.size>0)
        {
            menuRecyclerView.visibility = View.VISIBLE
            noDataImage.visibility  = View.GONE
            noDataText.visibility  = View.GONE


        }else
        {
            menuRecyclerView.visibility = View.GONE

            noDataImage.visibility  = View.VISIBLE
            noDataText.visibility  = View.VISIBLE
        }

        filterData()

    }


    private fun filterData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Order")


        databaseReference.orderByChild("userId").equalTo(auth.currentUser.phoneNumber).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(OrderModel::class.java)
                        menuList.add(menuitem!!)
                    }
                    val mAdapter = UserOrderAdapter(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserOrderAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

//                            if (menuList.get(position).status == "accepted")
//                            {
//                                val intent = Intent(applicationContext, ChatActivity::class.java)
////                            //put extras
//                            Variable.oId = menuList[position].orderId
////                            intent.putExtra("empId", menuList[position].orderId)
//                                startActivity(intent)
////
                            }
//                              Toast.makeText(thiscontext,"Touched", Toast.LENGTH_SHORT).show()
//                        }

                    })

                    if (menuList.size>0)
                    {
                        menuRecyclerView.visibility = View.VISIBLE
                        noDataImage.visibility  = View.GONE
                        noDataText.visibility  = View.GONE


                    }else
                    {
                        menuRecyclerView.visibility = View.GONE

                        noDataImage.visibility  = View.VISIBLE
                        noDataText.visibility  = View.VISIBLE
                    }
//                    tvLoadingData.visibility = View.GONE
                }
            }





            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut();
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}