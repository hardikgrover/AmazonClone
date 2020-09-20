package com.example.amazon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amazon.Model.Cart;
import com.example.amazon.Model.FinalOrder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference finalOrderRef;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        finalOrderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.admin_final_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<FinalOrder> options = new FirebaseRecyclerOptions.Builder<FinalOrder>()
                .setQuery(finalOrderRef, FinalOrder.class).build();
        FirebaseRecyclerAdapter<FinalOrder,AdminNewOrders.OrderViewHolder>adapter =new
                FirebaseRecyclerAdapter<FinalOrder, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull FinalOrder finalOrder) {
                        final String name,phone,adderess,dateTime,Price;
                        orderViewHolder.name.setText(finalOrder.getName());
                        orderViewHolder.phone.setText(finalOrder.getPhoneNumber());
                        orderViewHolder.adderss.setText(finalOrder.getAddress());
                        orderViewHolder.price.setText(finalOrder.getTotalAmount());
                        orderViewHolder.dateTime.setText(finalOrder.getDate());

                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_orders,parent,false);
                        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
                        return orderViewHolder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }
    public static  class OrderViewHolder extends RecyclerView.ViewHolder{
        private TextView name,adderss,phone,price,dateTime;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.final_user_name);
            adderss = itemView.findViewById(R.id.final_user_address);
            price = itemView.findViewById(R.id.final_user_price);
            dateTime = itemView.findViewById(R.id.final_user_date);
            phone = itemView.findViewById(R.id.final_user_phone);


        }
    }
}