package com.example.amazon.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazon.Interface.ItemClickListner;
import com.example.amazon.R;

public class  ProductVeiwHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName , productDescription,productPrice;
    public ImageView imageView;
    public ItemClickListner listner;
    public ProductVeiwHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_image);
        productName = itemView.findViewById(R.id.product_home_name);
        productDescription = itemView.findViewById(R.id.product_description);
        productPrice = itemView.findViewById(R.id.product_price);


    }
    public void setItemClickListner(ItemClickListner Listner){
        this.listner = listner;
    }



    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);

    }
}
