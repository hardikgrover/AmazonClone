package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.amazon.Model.Products;
import com.example.amazon.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {
    private Button addToCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productDes,productName,productPrice;
    private String productId = "";
    private DatabaseReference productRef;
    private DatabaseReference cartListRef;
    private FirebaseAuth mAuth;
    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productId = getIntent().getStringExtra("pid");
        setContentView(R.layout.activity_product_details);
        numberButton = findViewById(R.id.add_product_no);
        productImage = findViewById(R.id.product_image_details1);
        productName = findViewById(R.id.product_name_details);
        productPrice = findViewById(R.id.product_price_details);
        productDes = findViewById(R.id.product_description_details);
        addToCart = findViewById(R.id.pd_add_to_cart_button);
        cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        getProductDetails(productId);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

    }

    private void addingToCartList() {
        String saveCurrentTime , saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd ,yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productId",productId);
        cartMap.put("productName",productName.getText().toString());
        cartMap.put("productPrice",productPrice.getText().toString());
        cartMap.put("productDate",saveCurrentDate);
        cartMap.put("productTime",saveCurrentTime);
        cartMap.put("productQuantity",numberButton.getNumber());
        cartMap.put("productDiscount","");

        cartListRef.child("user View").child(currentUserId).child("Products").child(productId)
                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if (task.isSuccessful()){
               cartListRef.child("admin View").child(currentUserId).child("Products").child(productId)
                       .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(ProductDetails.this, "prouct added to cart", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(ProductDetails.this, HomeActivity.class);
                           startActivity(intent);
                       }
                   }
               });
           }
            }
        });



    }

    private void getProductDetails(String productId) {
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productDes.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}