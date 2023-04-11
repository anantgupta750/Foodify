package com.food.factory.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.food.factory.Admin.Fragments.Adapter.SubMenuAdap
import com.food.factory.Admin.Fragments.Model.SaveSubMenu
import com.food.factory.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerifyPayment : AppCompatActivity() {

    private lateinit var verifyText: TextView
    private lateinit var verifyImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_payment)


        verifyText = findViewById(R.id.verifyText)
        verifyImage = findViewById(R.id.verifyS)


                verifyText.text = "Verification Failed"
                verifyImage.setImageResource(R.drawable.failed);
        filterData(intent.getStringExtra("scannedValue").toString())

    }

    private fun filterData(toString: String) {

        val databaseReference = FirebaseDatabase.getInstance().getReference("Order")

//        Toast.makeText(this,toString,Toast.LENGTH_SHORT).show()

        databaseReference.orderByChild("paymentId").equalTo(toString).addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    Log.e("", "onDataChange: ", )
//                    studentsList.clear()
                    for (i in snapshot.children){
                        val menuitem = i.getValue(SaveSubMenu::class.java)
//                        menuList.add(menuitem!!)
                    }

                    verifyText.text = "Success Payment Verified"
                    verifyImage.setImageResource(R.drawable.verify);

//                            Toast.makeText(thiscontext,""+menuList.get(0).subMenuName, Toast.LENGTH_SHORT).show()
//                    val mAdapter = SubMenuAdap(menuList)
//                    menuRecyclerView.adapter = mAdapter

//                    mAdapter.setOnItemClickListener(object : SaveSubMenu.onItemClickListener {
//                        override fun onItemClick(position: Int) {
//
//
////                            Toast.makeText(thiscontext,"Touched", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })

//                    menuRecyclerView.visibility = View.VISIBLE
//                    tvLoadingData.visibility = View.GONE
                }

//                verifyText.text = "Verification Failed"
//                verifyImage.setImageResource(R.drawable.failed);
            }




            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}