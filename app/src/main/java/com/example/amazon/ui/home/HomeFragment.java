package com.example.amazon.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazon.Model.Products;
import com.example.amazon.R;
import com.example.amazon.ViewHolder.ProductVeiwHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private HomeViewModel homeViewModel;
    private DatabaseReference productRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.products_display);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(productRef,Products.class)
                        .build();
        FirebaseRecyclerAdapter<Products, ProductVeiwHolder> adapter = new
                FirebaseRecyclerAdapter<Products, ProductVeiwHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductVeiwHolder productVeiwHolder, int i, @NonNull Products products) {
                        productVeiwHolder.productName.setText(products.getPname());
                        productVeiwHolder.productDescription.setText(products.getPname());
                        productVeiwHolder.productPrice.setText("Price = " + products.getPname() + "$");
                        Picasso.get().load(products.getImage()).into(productVeiwHolder.imageView);
                    }

                    @NonNull
                    @Override
                    public ProductVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
                        ProductVeiwHolder holder  = new ProductVeiwHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}