package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    private EditText email;
    private Button getLink;
    String getEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();


        getLink = findViewById(R.id.get_link);
        email = findViewById(R.id.email);
        getEmail = email.getText().toString();
        getLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                verify();
            }
        });
    }

    private void verify( ) {
        String Email = email.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.sendPasswordResetEmail(Email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(forgotPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}