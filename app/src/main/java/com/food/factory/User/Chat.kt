package com.food.factory.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.food.factory.R
import com.food.factory.User.Model.CartModel
import com.food.factory.User.Model.Chat
import com.food.factory.Variable
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Chat : AppCompatActivity() {
    private lateinit var text: TextInputEditText
    lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        text = findViewById(R.id.textField)
        auth= FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Chat")

        val chatID = dbRef.push().key!!

        val menu = Chat(chatID,Variable.oId,auth.currentUser.phoneNumber,text.toString())
        dbRef.child(chatID).setValue(menu)
            .addOnCompleteListener {
//                Toast.makeText(context, "Data updated successfully", Toast.LENGTH_LONG).show()

//                         cartId = dbRef.push().key!!


            }.addOnFailureListener { err ->
//                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }





    }
}