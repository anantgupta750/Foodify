package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.food.factory.Admin.AdminActivity
import com.food.factory.User.MenuList
import com.food.factory.User.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreen : AppCompatActivity() {
//    lateinit var auth: FirebaseAuth
//    private lateinit var userList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        auth=FirebaseAuth.getInstance()
//        userList = arrayListOf<UserModel>()
//
////        if (auth.currentUser != null)
////        {
////            val intent = Intent(this@SplashScreen, Scan::class.java)
////            startActivity(intent)
////        }
//
//
//        val databaseReference = FirebaseDatabase.getInstance().getReference("User")
//
//
//        databaseReference.orderByChild("userNumber").equalTo(auth.currentUser.phoneNumber).addValueEventListener(object:
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
////                    userList.clear()
//                    for (i in snapshot.children){
//                        val menuitem = i.getValue(UserModel::class.java)
//                        userList.add(menuitem!!)
//                    }
//                }
//
//                    if (userList.get(0).userType.equals("user"))
//                    {
//                        nextPage()
//                    }else
//                    {
//                        newPage()
//
//
//                }
//
//            }
//
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })

    }
//
//    private fun newPage() {
//        val intent = Intent(this , AdminActivity::class.java)
//        startActivity(intent)
//        finish()    }
//
//    private fun nextPage() {
//
//        val intent = Intent(this , Scan::class.java)
//        startActivity(intent)
//        finish()    }

    fun clinkNewPage(view: View) {



        val intent = Intent(this@SplashScreen, MainActivity::class.java)
        startActivity(intent)

    }
}