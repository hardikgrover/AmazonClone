package com.example.amazon.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazon.Model.Cart;
import com.example.amazon.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);
        uid = getIntent().getStringExtra("uid");

        mRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("admin View").child(uid).child("Products");
        recyclerView = findViewById(R.id.r4);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(AdminUserProducts.this, uid, Toast.LENGTH_SHORT).show();

        FirebaseRecyclerOptions<Cart>options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(mRef,Cart.class).build();

        FirebaseRecyclerAdapter<Cart,AdminUserProducts.ViewHolder>adapter =
                new FirebaseRecyclerAdapter<Cart, ViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Cart cart) {
                        viewHolder.price.setText(cart.getProductPrice());
                        viewHolder.productName.setText(cart.getProductName());
                        viewHolder.quantity.setText(cart.getProductQuantity());

                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                       ViewHolder viewHolder = new ViewHolder(view);
                       return viewHolder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName,quantity,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_name);
            quantity = itemView.findViewById(R.id.cart_quantity);
            price = itemView.findViewById(R.id.cart_price);
        }
    }
}