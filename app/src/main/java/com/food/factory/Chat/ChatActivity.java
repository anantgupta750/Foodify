package com.food.factory.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.food.factory.R;
import com.food.factory.Variable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class ChatActivity extends AppCompatActivity {


    FirebaseStorage storage;
    StorageReference storageReference;
    TextInputEditText textField;
    NestedScrollView scrollTo;
    TextView usernameChat;
    String SAVE_REQUEST_CODE;
    ImageButton attachmentBtn,sendButton;
    String email,mobileNumber;
    String orderID;
    FirebaseAuth firebaseAuth;
    private String UPLOAD_KEY;
    RecyclerView chats;
    Uri filePath;
    private BottomSheetDialog attachmentSheet;

    ChatAdapter chatAdapter;
//    SessionManager sessionManager;
    ArrayList<ChatModel> chatModel = new ArrayList<>();
    DatabaseReference mDatabaseUser, mDatabaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
//        sessionManager = new SessionManager(this);

        scrollTo = findViewById(R.id.scrollTo);
        chats = findViewById(R.id.chats);
        sendButton = findViewById(R.id.sendButton);
        textField = findViewById(R.id.textField);
//        attachmentBtn = findViewById(R.id.attachmentBtn);
        usernameChat = findViewById(R.id.usernameChat);
//        email = getIntent().getExtras().getString("email");
//        username = getIntent().getExtras().getString("username");
        mDatabaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        chats.setLayoutManager(new LinearLayoutManager(this));

        mobileNumber = firebaseAuth.getCurrentUser().getPhoneNumber();
//        String sr[] = mobileNumber.split(Pattern.quote("+"));
        mobileNumber = mobileNumber.substring(1);
        usernameChat.setText(mobileNumber);

//        Log.e("", "onCreate: SSS "+sr.toString() );
//        HashMap<String, String> userC = sessionManager.getUserDetail();
//        Log.e("", "onCreate: " + userC.get(sessionManager.EMAIL));

//         userC.get(sessionManager.EMAIL);

//        chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textField.getText().toString().trim().isEmpty()){
                    DatabaseReference newMessageDb = mDatabaseChat.push();


                    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                    Log.e("Timing", "onClick: "+currentTime );
//                    Log.e("My Name", "onClick: "+username );

                    Map newMessage = new HashMap();
                    newMessage.put("SenderNumber", mobileNumber);
                    newMessage.put("text", textField.getText().toString());
                    newMessage.put("orderId", Variable.oId);
                    newMessage.put("type", "0");
                    newMessage.put("time", currentTime);

                    newMessageDb.setValue(newMessage);
                }
                textField.setText("");
//                getdata();
////                chatAdapter.notifyDataSetChanged();
//
            }
        });
//        attachmentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AskPermission();
//            }
//        });


        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (textField.getText().toString().isEmpty()) {

//                    sendButton.setVisibility(View.INVISIBLE);
//                    attachmentBtn.setVisibility(View.VISIBLE);


                } else {

//                    sendButton.setVisibility(View.VISIBLE);
//                    attachmentBtn.setVisibility(View.INVISIBLE);

                }
            }
        });
        loadAllCHat();
    }

    private void getdata() {

        chatModel.clear();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference chatSpaceRef = rootRef.child("Chat");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String msg = ds.child("orderId").getValue(String.class);
                    String user = ds.child("text").getValue(String.class);
                    String time = ds.child("time").getValue(String.class);
                    Log.d("TAG", msg);
                    Log.d("ChatText", user);
//                    Toast.makeText(ChatList.this, ""+msg, Toast.LENGTH_SHORT).show();
//                    HashMap<String, String> userC = sessionManager.getUserDetail();

                    if (msg.matches(orderID))
                    {
                        Log.d("TAG", msg);
                        Log.d("ChatText5", user);
                        int type = 0;
                        chatModel.add(new ChatModel(msg,user,time,type));

                    }else if (msg.matches(email))
                    {
                        Log.d("TAG", msg);
                        Log.d("ChatText", user);
                        int type = 1;
                        chatModel.add(new ChatModel(msg,user,time,type));
//
                    }
//                    ChatAdapter chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
//                    chats.setAdapter(chatAdapter);


                }
//                UsersAdapter usersAdapter = new UsersAdapter(getApplicationContext(),users);
//                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        chatSpaceRef.addListenerForSingleValueEvent(eventListener);
    }


    private void loadAllCHat()
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference chatSpaceRef = rootRef.child("Chat");

        mDatabaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    String msg = null;
                    String user = null;
                    String time = null;
                    String  type = null;
                    String  orderID = null;

                    if (snapshot.child("text").getValue() != null) {
                        msg = snapshot.child("text").getValue().toString();
                    }
                    if (snapshot.child("SenderNumber").getValue() != null) {
                        user = snapshot.child("SenderNumber").getValue().toString();
                    } if (snapshot.child("orderId").getValue() != null) {
                        orderID = snapshot.child("orderId").getValue().toString();
                    }
                    if (snapshot.child("time").getValue() != null) {
                        time = snapshot.child("time").getValue().toString();
                    }
                    if (snapshot.child("type").getValue() != null) {
                        type = snapshot.child("type").getValue().toString();
                    }

//                    if (msg != null && user != null && time != null) {

//                    HashMap<String, String> userC = sessionManager.getUserDetail();



                    if (orderID.matches(Variable.oId))
                    {

                        if (user.matches( mobileNumber) && type.matches("0"))
                        {
//                            Log.d("TAG1", msg);

                            Log.d("TAG 0", msg);
                            Log.d("ChatText 0", user);
                            int type1 = 0;
                            chatModel.add(new ChatModel(msg,user,time,type1));
//                            chatAdapter.notifyDataSetChanged();

                        }else

                        {
                            Log.d("TAG 1", msg);
                            Log.d("ChatText 1", user);
                            int type2 = 1;
                            chatModel.add(new ChatModel(msg,user,time,type2));
//
//                            chatAdapter.notifyDataSetChanged();

//                        }
//                        Boolean currentUserBoolean = false;
//                        if(createdByUser.equals(currentUserID)){
//                            currentUserBoolean = true;
                        }

                    }



//                    else if (user.matches( userC.get(sessionManager.EMAIL)) && type.matches("2")){
//                        Log.d("TAG ", msg);
//                        Log.d("ChatText ", user);
//                        int type3 = 2;
//                        chatModel.add(new ChatModel(msg,user,time,type3));
////
////                            chatAdapter.notifyDataSetChanged();
//
////                        }
////                            chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
//
////                            chats.setAdapter(chatAdapter);
////                            scrollToLastPosition();
//                    }
//                    else if (user.matches( userC.get(sessionManager.EMAIL)) && type.matches("3")){
//                        Log.d("TAG ", msg);
//                        Log.d("ChatText ", user);
//                        int type3 = 4;
//                        chatModel.add(new ChatModel(msg,user,time,type3));
////
////                            chatAdapter.notifyDataSetChanged();
//
////                        }
////                            chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
//
////                            chats.setAdapter(chatAdapter);
////                            scrollToLastPosition();
//                    }
//                    else if (user.matches(email) && type.matches("2")){
//                        Log.d("TAG ", msg);
//                        Log.d("ChatText ", user);
//                        int type3 = 3;
//                        chatModel.add(new ChatModel(msg,user,time,type3));
////
////                            chatAdapter.notifyDataSetChanged();
//
////                        }
////                            chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
//
////                            chats.setAdapter(chatAdapter);
////                            scrollToLastPosition();
//                    }

                    chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);

                    chats.setAdapter(chatAdapter);
                    scrollToLastPosition();
//                        ChatObject newMessage = new ChatObject(message, currentUserBoolean);
//                        resultsChat.add(newMessage);
//                        mChatAdapter.notifyDataSetChanged();
                }







//                for(DataSnapshot ds : snapshot.getChildren()) {
//                    String msg = ds.child("SenderEmail").getValue(String.class);
//                    String user = ds.child("text").getValue(String.class);
//                    String time = ds.child("time").getValue(String.class);
//                    Log.d("TAG", time);
//                    Log.d("ChatText", user);
////                    Toast.makeText(ChatList.this, ""+msg, Toast.LENGTH_SHORT).show();
//                    HashMap<String, String> userC = sessionManager.getUserDetail();
//
//                    if (msg.matches( userC.get(sessionManager.EMAIL)))
//                    {
//                        Log.d("TAG", msg);
//                        Log.d("ChatText5", user);
//                        int type = 0;
//                        chatModel.add(new ChatModel(msg,user,time,type));
//
//                    }else if (msg.matches(email))
//                    {
//                        Log.d("TAG", msg);
//                        Log.d("ChatText", user);
//                        int type = 1;
//                        chatModel.add(new ChatModel(msg,user,time,type));
////
//                    }
//                    ChatAdapter chatAdapter = new ChatAdapter(getApplicationContext(),chatModel);
//                    chats.setAdapter(chatAdapter);
//
//
//                }

            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void scrollToLastPosition() {

      /*  new Handler().post(new Runnable() {
            @Override
            public void run() {
                binding.chatRv.smoothScrollToPosition(arrayList.size()-1);

            }
        });*/

        scrollTo.post(() -> {

            runOnUiThread(() -> {

                //    binding.scrollTo.fullScroll(View.FOCUS_DOWN);

                scrollTo.scrollTo(0, chats.getBottom());
                textField.requestFocus();
            });
            // This method works even better because there are no animations.

        });

    }









}