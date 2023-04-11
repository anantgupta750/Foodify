package com.food.factory.User

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.food.factory.*
import com.food.factory.Admin.Fragments.Adapter.MenuAdapter
import com.food.factory.Admin.Fragments.Model.AddMenuModel
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MenuList : AppCompatActivity() {
    private lateinit var menuRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var cartButton: CardView
    private lateinit var orderno: TextView

    private lateinit var empList: ArrayList<CartModel>
    private lateinit var dbRef: DatabaseReference

    private lateinit var menuList: ArrayList<AddMenuModel>
    var thiscontext: Context? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)
        auth=FirebaseAuth.getInstance()
        empList = arrayListOf<CartModel>()


        val isUserSignedIn = auth.currentUser == null

        if (isUserSignedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
            thiscontext = application
        orderno = findViewById(R.id.orderno)

        cartButton = findViewById(R.id.cartbutton)
        menuRecyclerView = findViewById(R.id.menuRv)
        menuRecyclerView.layoutManager =  StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        menuRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<AddMenuModel>()

        cartButton.setOnClickListener {
            val intent = Intent(this@MenuList, CartActivity::class.java)
            startActivity(intent)
        finish()
        }
        getEmployeesData()
        filterData(intent.getStringExtra("scannedValue").toString())

//        Toast.makeText(thiscontext,                intent.getStringExtra("scannedValue").toString(),
//        Toast.LENGTH_SHORT).show()
    }

    private fun filterData(toString: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Menu")


        databaseReference.orderByChild("subMenuId").equalTo(Variable.sub_menu_Id).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(AddMenuModel::class.java)
                        menuList.add(menuitem!!)
                    }
                    val mAdapter = MenuAdap(menuList)
                    menuRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MenuAdap.onItemClickListener {
                        override fun onItemClick(position: Int) {


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


    private fun getEmployeesData() {


        dbRef = FirebaseDatabase.getInstance().getReference("Cart")


        dbRef.orderByChild("userId").equalTo(auth.currentUser.phoneNumber)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(CartModel::class.java)

                        if (empData?.status == "pending") {

                            empList.add(empData!!)
                        }
                    }


                    if (empList.size > 0)
                    {
                        cartButton.visibility = View.VISIBLE
                    }


//                    Toast.makeText(thiscontext,"Cart Item" +empList.size,Toast.LENGTH_SHORT).show()
   tvLoadingData.visibility = View.GONE
                }
                orderno.text = "${empList.size} is items added"

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun cartPage(view: View) {


        val intent = Intent(this@MenuList, CartActivity::class.java)
        startActivity(intent)

    }

    fun orderList(view: View) {

        val intent = Intent(this@MenuList, UserOrderList::class.java)
        startActivity(intent)

    }

    override fun onResume() {

        if (empList.size == 0){
            cartButton.visibility = View.GONE
        }
        super.onResume()
    }
}