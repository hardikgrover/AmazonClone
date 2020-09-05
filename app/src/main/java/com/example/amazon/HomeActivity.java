package com.example.amazon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;

import com.example.amazon.Model.Products;
import com.example.amazon.ViewHolder.ProductVeiwHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseReference productRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseRecyclerOptions<Products>options =
//                new FirebaseRecyclerOptions.Builder<Products>()
//                .setQuery(productRef,Products.class)
//                .build();
//        FirebaseRecyclerAdapter<Products, ProductVeiwHolder> adapter = new
//                FirebaseRecyclerAdapter<Products, ProductVeiwHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ProductVeiwHolder productVeiwHolder, int i, @NonNull Products products) {
//                        productVeiwHolder.productName.setText(products.getPname());
//                        productVeiwHolder.productDescription.setText(products.getPname());
//                        productVeiwHolder.productPrice.setText("Price = " + products.getPname() + "$");
//                        Picasso .get().load(products.getImage()).into(productVeiwHolder.imageView);
//                    }
//
//                    @NonNull
//                    @Override
//                    public ProductVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,parent,false);
//                            ProductVeiwHolder holder  = new ProductVeiwHolder(view);
//                            return holder;
//                            }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();


    }
}














