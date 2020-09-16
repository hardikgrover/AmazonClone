package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazon.Model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button nextBtn;
    private TextView totalAmount;
    private DatabaseReference cartRef;
    private FirebaseAuth mAuth;
    private String currentUser;
    private int totalCartAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("user View")
                .child(currentUser).child("Products");

        nextBtn = findViewById(R.id.next_button);
        totalAmount = findViewById(R.id.total_price);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CartActivity.this,ConfirmFinalOrder.class);
                intent.putExtra("cart price",String.valueOf(totalCartAmount));
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartRef,Cart.class).build();
        FirebaseRecyclerAdapter<Cart,CartActivity.CartViewHolder> adapter = new
                FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                cartViewHolder.name.setText(cart.getProductName());
                cartViewHolder.price.setText(cart.getProductPrice());
                cartViewHolder.quantity.setText(cart.getProductQuantity());

                int oneTypeProductTprice = Integer.valueOf(cart.getProductPrice()) * Integer.valueOf(cart.getProductQuantity());
                totalCartAmount = totalCartAmount+oneTypeProductTprice;

                //cartViewHolder.quantity.setText(cart);

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{

                                "Edit",
                                "Remove"
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                           if (i == 0)
                           {
                               Intent intent = new Intent(CartActivity.this,ProductDetails.class);
                               intent.putExtra("pid",cart.getProductId());
                               startActivity(intent);
                           }
                                if (i == 1) {
                                    cartRef.child(cart.getProductId()).removeValue().
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(CartActivity.this, "product deleted successfulluy", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                            startActivity(intent);
                                                    }
                                                }
                                            });


                                }
                            }
                        });
                        builder.show();
                    }
                });
                totalAmount.setText("Total Price"+ String.valueOf(totalCartAmount));



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false)
                  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                  CartViewHolder holder = new CartViewHolder(view);
                  return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_name);
            price = itemView.findViewById(R.id.cart_price);
            quantity = itemView.findViewById(R.id.cart_quantity);
        }
    }
}













