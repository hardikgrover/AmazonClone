package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrder extends AppCompatActivity {
    private EditText name,phone,address,city;
    private DatabaseReference orderRef,cartRef,userRef;
    private FirebaseAuth mAuth;
    private String currentUid;
    private String pid;



    private Button confirmBtn;
    String cartPrice = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        pid = getIntent().getStringExtra("pid");
        Toast.makeText(this, pid, Toast.LENGTH_LONG).show();
        cartPrice = getIntent().getStringExtra( "cart price");
        confirmBtn = findViewById(R.id.confirm_final_order_btn);
        name = findViewById(R.id.shipment_name);
        phone = findViewById(R.id.shipment_phone);
        address = findViewById(R.id.shipment_address);
        city = findViewById(R.id.shipment_city);
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        cartRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUid);

//        pid = cartRef.child("Cart List").child("user View").child(currentUid).child("Products").getKey().toString();


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetails();
            }
        });
    }

    private void checkDetails() {
        if (TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "please provide your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(city.getText().toString())){
            Toast.makeText(this, "please provide your city", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this, "please provide your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "please provide your phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmOrder();
        }

    }

    private void confirmOrder() {
        String time ,date;
        String productId;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat getTime = new SimpleDateFormat("MM dd ,yyyy");
        time = getTime.format(calendar.getTime());


        SimpleDateFormat getDate = new SimpleDateFormat("hh:mm:ss a");
        date = getDate.format(calendar.getTime());


        HashMap<String,Object> finalOrder = new HashMap<>();
        finalOrder.put("date",date);
        finalOrder.put("time",time);
        finalOrder.put("address",address.getText().toString());
        finalOrder.put("city",city.getText().toString());
        finalOrder.put("name",name.getText().toString());
        finalOrder.put("phoneNumber",phone.getText().toString());
        finalOrder.put("state","not shipped");
        finalOrder.put("uid",currentUid);
        finalOrder.put("totalAmount",cartPrice);

        HashMap<String,Object> user = new HashMap<>();
        user.put("name",name.getText().toString());
        user.put("city",city.getText().toString());
        user.put("address",address.getText().toString());
        user.put("phoneNumber",phone.getText().toString());
        userRef.updateChildren(user);




//        finalOrder.put("productId",pid);

        orderRef.child(currentUid).updateChildren(finalOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                cartRef.child("Cart List").child("user View").child(currentUid).child("Products").child(pid).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ConfirmFinalOrder.this, "Product is successfully added to your cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}