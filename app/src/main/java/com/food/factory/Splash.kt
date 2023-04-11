package com.food.factory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.food.factory.Admin.AdminActivity
import com.food.factory.User.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Splash : AppCompatActivity() {
    var videoView: VideoView? = null
    lateinit var auth: FirebaseAuth
    private lateinit var userList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        auth=FirebaseAuth.getInstance()
        userList = arrayListOf<UserModel>()

        videoView = findViewById(R.id.video)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.splash)

        videoView.run {

            this?.setVideoURI(uri)

//            setVideoURI(uri)
            this?.start()
        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("User")

        if (auth.currentUser!= null){
            databaseReference.orderByChild("userNumber").equalTo(auth.currentUser.phoneNumber).addValueEventListener(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
//                    userList.clear()
                        for (i in snapshot.children){
                            val menuitem = i.getValue(UserModel::class.java)
                            userList.add(menuitem!!)
                        }
                    }



                    if (userList.get(0).userType.equals("user"))
                    {
                        Handler().postDelayed({
                            nextPage()         }, 1500)
                    }else
                    {
                        Handler().postDelayed({
                            newPage()                              }, 1500)

//                    newPage()


                    }

                }




                override fun onCancelled(error: DatabaseError) {

                }
            })}else
            {

                Handler().postDelayed({
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)

              }


    }

    private fun newPage() {
        val intent = Intent(this , AdminActivity::class.java)
        startActivity(intent)
        finish()    }

    private fun nextPage() {

        val intent = Intent(this , Scan::class.java)
        startActivity(intent)
        finish()    }

}