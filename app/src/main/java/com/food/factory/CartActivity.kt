package com.food.factory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.User.CartAdapter
import com.food.factory.User.Model.CartModel
import com.food.factory.User.Model.OrderModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
//import com.razorpay.Checkout
//import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

class CartActivity : AppCompatActivity()
    , PaymentResultListener
{
    private lateinit var cartRV: RecyclerView
    private lateinit var dbRef: DatabaseReference
    private lateinit var amountt: TextView
    lateinit var auth: FirebaseAuth
    var grandtotal =0;

    private lateinit var cartList: ArrayList<CartModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        auth=FirebaseAuth.getInstance()
        cartList = arrayListOf<CartModel>()

        amountt = findViewById(R.id.amoun)
        cartRV = findViewById(R.id.cartRV)
        cartRV.layoutManager = LinearLayoutManager(applicationContext)
        cartRV.setHasFixedSize(true)

        getEmployeesData()
    }
    private fun getEmployeesData() {


        dbRef = FirebaseDatabase.getInstance().getReference("Cart")


        dbRef.orderByChild("userId").equalTo(auth.currentUser.phoneNumber)

            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("", "onDataChange: Something Change", )


                    cartList.clear()
                    if (snapshot.exists()){
                        for (empSnap in snapshot.children){
                            val empData = empSnap.getValue(CartModel::class.java)
                            if (empData?.status == "pending") {

                                cartList.add(empData!!)
                                total()
                            }
                        }
                        val mAdapter = CartAdapter(cartList)
                        cartRV.adapter = mAdapter
                        mAdapter.setOnItemClickListener(object : CartAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {




//                                Toast.makeText(applicationContext,"Touched", Toast.LENGTH_SHORT).show()
                            }



                        })

                        grandtotal = 0

                        for (i in 0 until cartList.size) {
                            var a = Integer.parseInt(cartList.get(i).totalPrice)
                            var b = Integer.parseInt(cartList.get(i).plateNo)
                            var c = a*b
                            Log.e("", "total: ${a},$b,$c}", )
                            grandtotal += c
                        }
                        amountt.setText("Total Amount "+Integer.toString(grandtotal))

//                        if (cartList.size > 0)
//                        {
//                            cartButton.visibility = View.VISIBLE
//                        }
//                        Toast.makeText(applicationContext,"Cart Item" +grandtotal, Toast.LENGTH_SHORT).show()
//                        tvLoadingData.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun total(){


    }

    fun pay(view: View) {
//
//        // rounding off the amount.
        val amount = Math.round(grandtotal.toFloat() * 100).toInt()

        // on below line we are
        // initializing razorpay account
        val checkout = Checkout()

        // on the below line we have to see our id.
        checkout.setKeyID("rzp_test_IEc8qM6Dl35sYD")

        // set image
        checkout.setImage(com.razorpay.R.drawable.rzp_logo)

        // initialize json object
        val obj = JSONObject()
        try {
            // to put name
            obj.put("name", auth.currentUser.phoneNumber)

            // put description
            obj.put("description", "Test payment")

            // to set theme color
//            obj.put("theme.color", "")

            // put the currency
            obj.put("currency", "INR")

            // put amount
            obj.put("amount", amount)

            // put mobile number
            obj.put("prefill.contact", auth.currentUser.phoneNumber)

            // put email
//            obj.put("prefill.email", "chaitanyamunje@gmail.com")

            // open razorpay to checkout activity
            checkout.open(this@CartActivity, obj)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
//

    }

    override fun onPaymentSuccess(p0: String?) {
//        Toast.makeText(this@CartActivity, "Payment is successful : " + p0, Toast.LENGTH_SHORT).show();
        makeOrderId(p0)
    }

    private fun makeOrderId(p0: String?) {
        lateinit var dbReff: DatabaseReference

        dbReff = FirebaseDatabase.getInstance().getReference("Order")
        var orderId = dbReff.push().key!!

        val menu = OrderModel(
            auth.currentUser.phoneNumber,orderId,p0,grandtotal.toString(),"Pending",cartList)



        dbReff.child(orderId).setValue(menu)
            .addOnCompleteListener {
//                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_LONG).show()


                dbRef = FirebaseDatabase.getInstance().getReference("Cart")


                dbRef.orderByChild("userId").equalTo(auth.currentUser.phoneNumber).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val mTask = dbRef.removeValue()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })



                        val intent = Intent(applicationContext, UserOrderList::class.java)
//                            //put extras
//                            Variable.orderId = menuList[position].orderId
//                            intent.putExtra("empId", menuList[position].orderId)
                            startActivity(intent)
                finish()
//

            }.addOnFailureListener { err ->
//                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this@CartActivity, "Payment Failed : " + p1, Toast.LENGTH_SHORT).show();
    }
}


