package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amazon.Model.users;
import com.example.amazon.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinNow,loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNow = findViewById(R.id.join_now_button);
        loginButton = findViewById(R.id.main_login_button);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);
    loginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,loginActivity.class);
            startActivity(intent);
        }
    });
    joinNow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    });

//        String userPhoneKey = Paper.book().read(prevalent.phoneKey);
//        String userPassword = Paper.book().read(prevalent.passwordKey);
//    if (userPhoneKey != "" && userPassword!= ""){
//        if(!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPassword)){
//            allowAccess(userPhoneKey,userPassword);
//            loadingBar.setTitle("Already logged in");
//            loadingBar.setMessage("Please wait.....");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//        }

    //}
    }

    private void allowAccess(final String phone, final String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("users").child(phone).exists()){
                    users userData = dataSnapshot.child("users").child(phone).getValue(users.class);

                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);

                            startActivity(intent);

                        }
                        else
                        {
                            loadingBar.dismiss();
                        }
                    }



                }
                else{

                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
