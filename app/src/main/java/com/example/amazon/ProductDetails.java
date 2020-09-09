package com.example.amazon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ImageView;
import com.rey.material.widget.TextView;

public class ProductDetails extends AppCompatActivity {
    private FloatingActionButton addToCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productDes,productName,productPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addToCart = findViewById(R.id.add_product_cart);
        numberButton = findViewById(R.id.add_product_no);
        productImage = findViewById(R.id.product_image_des);
        productName = findViewById(R.id.product_name_description);
        productDes = findViewById(R.id.product_price);

    }
}