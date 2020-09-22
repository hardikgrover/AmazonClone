package com.example.amazon.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazon.Model.Products;
import com.example.amazon.ProductDetails;
import com.example.amazon.R;
import com.example.amazon.ViewHolder.ProductVeiwHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private HomeViewModel homeViewModel;
    private DatabaseReference productRef,userRef;
    private FirebaseAuth mAuth;
    private String currentUser;
    private EditText search;
    private TextView go;
    private String isAdmin  = "";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        recyclerView = root.findViewById(R.id.products_display);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            isAdmin = getActivity().getIntent().getStringExtra("admin");

        }
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        search = root.findViewById(R.id.products_search);
        go = root.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchItems();
            }
        });

        
        addUserInformation();

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void showSearchItems() {
        String search = this.search.getText().toString();

        if (search.isEmpty()) {}
        else{

            recyclerView.setVisibility(View.GONE);

            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(productRef.orderByChild("name").startAt(search).endAt(search), Products.class)
                    .build();
            FirebaseRecyclerAdapter<Products, ProductVeiwHolder> adapter = new
                    FirebaseRecyclerAdapter<Products, ProductVeiwHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductVeiwHolder productVeiwHolder, int i, @NonNull final Products products) {
                            productVeiwHolder.productName.setText(products.getName());
                            productVeiwHolder.productDescription.setText(products.getDescription());
                            productVeiwHolder.productPrice.setText("Price = " + products.getPrice() + "$");
                            Picasso.get().load(products.getImage()).into(productVeiwHolder.imageView);

                            productVeiwHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getContext(), ProductDetails.class);
                                    intent.putExtra("pid", products.getPid());
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ProductVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                            ProductVeiwHolder holder = new ProductVeiwHolder(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void addUserInformation() {
        HashMap<String,Object> user= new HashMap<>();
        user.put("uid",currentUser);
        userRef.updateChildren(user);

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
                    protected void onBindViewHolder(@NonNull ProductVeiwHolder productVeiwHolder, int i, @NonNull final Products products) {
                        productVeiwHolder.productName.setText(products.getName());
                        productVeiwHolder.productDescription.setText(products.getDescription());
                        productVeiwHolder.productPrice.setText("Price = " + products.getPrice() + "$");
                        Picasso.get().load(products.getImage()).into(productVeiwHolder.imageView);

                        productVeiwHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (isAdmin.equals("admin")){
                                    CharSequence options[] = new CharSequence[]{
                                            "yes",

                                    };
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Do you want to delete this product");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (i ==0){
                                                String id = getRef(i).getKey();
                                                productRef.child(id).removeValue();


                                            }

                                        }


                                    }).setCancelable(true);
                                    builder.show();

                                }
                                else if (isAdmin.equals("")){ Intent intent = new Intent(getContext(), ProductDetails.class);
                                    intent.putExtra("pid",products.getPid());
                                    startActivity(intent);

                                }

                            }
                        });
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