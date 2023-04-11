package com.food.factory.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.Admin.Fragments.Adapter.HotelListAdapter
import com.food.factory.Admin.Fragments.Adapter.OrderListAdapter
import com.food.factory.Admin.Fragments.HotelDetail
import com.food.factory.Chat.ChatActivity
import com.food.factory.MenuModel
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.food.factory.User.Model.OrderModel
import com.food.factory.Variable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderDetail : AppCompatActivity() {
    private lateinit var orderID: TextView
    private lateinit var orderPrice: TextView
    private lateinit var orderPayment: TextView
    private lateinit var userId: TextView
    private lateinit var linearLayout2: LinearLayout
    private lateinit var complete: CardView
    private lateinit var texte: TextView
    private lateinit var oId: String
    private lateinit var orderRV: RecyclerView
    private lateinit var menuList: ArrayList<OrderModel>
    private lateinit var cartList: ArrayList<CartModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        texte = findViewById(R.id.texte)
        complete = findViewById(R.id.complete)
        linearLayout2 = findViewById(R.id.linearLayout2)
        orderID = findViewById(R.id.orderId)
        orderPrice = findViewById(R.id.orderPrice)
        orderPayment = findViewById(R.id.paymentId)
        userId = findViewById(R.id.userID)
        orderRV = findViewById(R.id.orderDetail)
        menuList = arrayListOf<OrderModel>()
        cartList = arrayListOf<CartModel>()
        orderRV.layoutManager = GridLayoutManager(applicationContext,2)
        orderRV.setHasFixedSize(true)



        complete.setOnClickListener { FirebaseDatabase.getInstance().getReference("Order").child(oId).child("status").setValue("complete") }
        filterData()

    }

    private fun filterData_cart(userId: String?) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Cart")


        databaseReference.orderByChild("userId").equalTo(userId).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (i in snapshot.children){
                        val cartlist = i.getValue(CartModel::class.java)
                        cartList.add(cartlist!!)

                    }

//                        Toast.makeText(applicationContext,"Touched"+cartList.size, Toast.LENGTH_SHORT).show()


                    val mAdapter = OrderListAdapter(cartList)
                    orderRV.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : OrderListAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                        }

                    })


        }
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })    }


    private fun filterData() {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Order")


        databaseReference.orderByChild("orderId").equalTo(Variable.orderId
        ).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(OrderModel::class.java)
                        menuList.add(menuitem!!)
//                        Toast.makeText(applicationContext,"Touched"+Variable.orderId,Toast.LENGTH_SHORT).show()

                    }

                    oId = menuList.get(0).orderId.toString()
                    orderID.setText("Order ID "+menuList.get(0).orderId)
                    orderPrice.setText("Order Amount "+menuList.get(0).paymentPrice)
                    orderPayment.setText("Payment Id "+menuList.get(0).paymentId)
                    userId.setText("User Number "+menuList.get(0).userId)


                    if (menuList.get(0).status == "pending")
                    {

                    }else if (menuList.get(0).status == "accepted"){
                        linearLayout2.visibility = View.GONE
                        complete.visibility = View.VISIBLE
                    }
                    else if (menuList.get(0).status == "reject"){
                        linearLayout2.visibility = View.GONE
                        complete.visibility = View.VISIBLE

                    }
                    else if (menuList.get(0).status == "complete"){
                        linearLayout2.visibility = View.GONE
                        complete.visibility = View.VISIBLE
                        texte.text = "Order Completed"
                    }
//                    filterData_cart(menuList.get(0).userId)
//                    cartList.add(menuList.get(0).listener.)
//
//                    cartList.size
                                            Toast.makeText(applicationContext,"Touched"+ cartList.size,Toast.LENGTH_SHORT).show()
//

                    val mAdapter = menuList.get(0).listener?.let { OrderListAdapter(it) }
                    orderRV.adapter = mAdapter
//
                    mAdapter?.setOnItemClickListener(object : OrderListAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                        }
                //
                                    })

                    }
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun updateAccept(view: View) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Order").child(oId).child("status").setValue("accepted")


//        databaseReference.child("myDb/awais@gmailcom/leftSpace").setValue("YourDateHere");

    }

    fun rejectClick(view: View) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Order").child(oId).child("status").setValue("reject")

    }

    fun chatGo(view: View) {
        Variable.oId = Variable.orderId
        val intent = Intent(this , ChatActivity::class.java)

        startActivity(intent)

    }

    fun qrScan(view: View) {
        val intent = Intent(this , AdminScanner::class.java)

        startActivity(intent)

    }
//    fun completeclick(view: View) {
//        val databaseReference = FirebaseDatabase.getInstance().getReference("Order").child(oId).child("status").setValue("complete")
//
//    }


}

private fun <E> ArrayList<E>.add(element: ArrayList<E>?) {

}
