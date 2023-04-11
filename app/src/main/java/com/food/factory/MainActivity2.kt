package com.food.factory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        auth=FirebaseAuth.getInstance()

        val isUserSignedIn = auth.currentUser == null

        if (isUserSignedIn){
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Log.d("Signed", "onCreate:  Empty"+auth.currentUser.phoneNumber)
//            signout()
        }
        findViewById<Button>(R.id.logout).setOnClickListener {
            signout()
        }


        findViewById<Button>(R.id.fetch).setOnClickListener {
            val intent = Intent(this , MainActivity3::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.save).setOnClickListener {
            val intent = Intent(this , MainActivity4::class.java)
            startActivity(intent)
        }
//        but_fui_si
    }

    private fun signout() {
        Log.d("", "signout: ")
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // user is now signed out
//                showSnackbar("sign out successful")
                Log.e("", "signout: Success" )
            }
    }
}