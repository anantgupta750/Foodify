package com.food.factory.User

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.food.factory.Admin.Fragments.Adapter.SubMenuAdap
import com.food.factory.Admin.Fragments.Model.SaveSubMenu
import com.food.factory.CartActivity
import com.food.factory.MainActivity
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.food.factory.UserOrderList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SubList : AppCompatActivity() {

    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var orderno: TextView
    private lateinit var cartButton: CardView

    private lateinit var empList: ArrayList<CartModel>
    private lateinit var dbRef: DatabaseReference

    private lateinit var menuList: ArrayList<SaveSubMenu>
    var thiscontext: Context? = null
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_list)

        auth=FirebaseAuth.getInstance()
        empList = arrayListOf<CartModel>()


        val isUserSignedIn = auth.currentUser == null

        if (isUserSignedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        thiscontext = application

        cartButton = findViewById(R.id.cartbutton)
        orderno = findViewById(R.id.orderno)
        menuRecyclerView = findViewById(R.id.Sub_menuRv)
        menuRecyclerView.layoutManager =  StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL);
        menuRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<SaveSubMenu>()

        cartButton.setOnClickListener {
            val intent = Intent(this@SubList, CartActivity::class.java)
            startActivity(intent) }
        getEmployeesData()
        filterData(intent.getStringExtra("scannedValue").toString())

//        Toast.makeText(thiscontext,                intent.getStringExtra("scannedValue").toString(),
//        Toast.LENGTH_SHORT).show()
    }

    private fun filterData(toString: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Sub_Menu")


        databaseReference.orderByChild("hotelId").equalTo(toString).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(SaveSubMenu::class.java)
                        menuList.add(menuitem!!)
                    }
//                            Toast.makeText(thiscontext,""+menuList.get(0).subMenuName, Toast.LENGTH_SHORT).show()
                    val mAdapter = SubMenuAdap(menuList)
                    menuRecyclerView.adapter = mAdapter

//                    mAdapter.setOnItemClickListener(object : SaveSubMenu.onItemClickListener {
//                        override fun onItemClick(position: Int) {
//
//
////                            Toast.makeText(thiscontext,"Touched", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })

                    menuRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun getEmployeesData() {


        dbRef = FirebaseDatabase.getInstance().getReference("Cart")


        dbRef.orderByChild("userId").equalTo(auth.currentUser.phoneNumber)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (empSnap in snapshot.children){
                            val empData = empSnap.getValue(CartModel::class.java)
                            if (empData?.status == "pending")
                            {
                                empList.add(empData!!)

                            }
                        }

                        if (empList.size > 0)
                        {
//                            cartButton.visibility = View.VISIBLE
                        }
//                    Toast.makeText(thiscontext,"Cart Item" +empList.size,Toast.LENGTH_SHORT).show()
                        tvLoadingData.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun cartPage(view: View) {


        val intent = Intent(this@SubList, CartActivity::class.java)
        startActivity(intent)

    }

//    fun orderList(view: View) {
//
//        val intent = Intent(this@SubList, UserOrderList::class.java)
//        startActivity(intent)
//
//    }

    fun subOrder(view: View) {

        val intent = Intent(this@SubList, UserOrderList::class.java)
        startActivity(intent)
    }
}