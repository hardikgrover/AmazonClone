package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazon.Model.users;
import com.example.amazon.Prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {
    private EditText phoneNumber,inputPassword;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private String parentDbName = "users";
    private CheckBox checkBoxRememberMe;
    private TextView adminLink , notAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        phoneNumber = findViewById(R.id.login_phone_number_input);
        inputPassword = findViewById(R.id.login_phone_password_input);
        adminLink = findViewById(R.id.admin_panel1_link);
        notAdminLink = findViewById(R.id.not_admin_panel1_link);
        loadingBar = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        checkBoxRememberMe = findViewById(R.id.remember_me_check);
        Paper.init(this);
        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "admins";

            }
        });
        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "users";
            }
        });
    }

    private void loginUser() {
        String password = inputPassword.getText().toString();
        String phone = phoneNumber.getText().toString();
          if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "please enter your phone number", Toast.LENGTH_SHORT).show();
        }
         if(parentDbName.equals("admins")){
              Intent intent = new Intent(loginActivity.this,AdminCategoryActivity.class);
                               startActivity(intent);

          }
        else{
            loadingBar.setTitle("Logging in");
            loadingBar.setMessage("Please wait while checking the crediantials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            allowAccessToAccount(phone,password);
        }

    }

    private void allowAccessToAccount(final String phone, final String password) {
        if(checkBoxRememberMe.isChecked()){
            Paper.book().write(prevalent.phoneKey,phone);
            Paper.book().write(prevalent.passwordKey,password);
        }
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()){
                    users userData = dataSnapshot.child(parentDbName).child(phone).getValue(users.class);

                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
//                           if(parentDbName.equals("admins")){
//                               Toast.makeText(loginActivity.this, "Welcome admin you are Logged in successfully", Toast.LENGTH_SHORT).show();
//                               loadingBar.dismiss();
//                               Intent intent = new Intent(loginActivity.this,AdminCategoryActivity.class);
//                               startActivity(intent);
                           //}
                            if(parentDbName.equals("users")){
                               Toast.makeText(loginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();
                               Intent intent = new Intent(loginActivity.this,HomeActivity.class);
                               startActivity(intent);
                           }

                        }
                        else
                        {
                            Toast.makeText(loginActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }



                }
                else{

                    Toast.makeText(loginActivity.this, "Account with this "+ phone +" does not exist", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}












