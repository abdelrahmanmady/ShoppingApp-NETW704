package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activities.DetailsList;
import com.example.myapplication.units.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private final Context mCtx;
    private final List<Product> productList;
    private final String email;

    public ProductAdapter(Context mCTx, List<Product> productList,String email) {
        this.mCtx = mCTx;
        this.productList = productList;
        this.email=email;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewDescription.setText(product.getDescription());
        Glide.with(mCtx).load(product.getImage_url()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(v -> {
            Intent intent=new Intent(mCtx, DetailsList.class);
            intent.putExtra("email",email);
            intent.putExtra("product_id",product.getProduct_id());
            intent.putExtra("productName",product.getName());
            intent.putExtra("productDescription",product.getDescription());
            intent.putExtra("image_url",product.getImage_url());
            mCtx.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDescription;
        ImageView imageView;
        RelativeLayout relativeLayout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
