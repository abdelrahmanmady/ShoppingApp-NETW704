package com.example.myapplication.adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.units.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{
    private final Context mCtx;
    private final List<Shop> shopList;
    private final String email;
    private final int product_id;
    private static final String INSERTION_URL="http://192.168.1.2/LoginRegister/favinsert.php";


    public ShopAdapter(Context mCtx, List<Shop> shopList, String email, int product_id){
        this.mCtx = mCtx;
        this.shopList = shopList;
        this.email=email;
        this.product_id=product_id;

    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.details_layout,null);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop=shopList.get(position);
        holder.shopName.setText(shop.getName());
        String string;
        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            string="Price: " + shop.getPrice()+" EGP, Distance: Disabled feature due to lack of permissions";
        }else {
            string = "Price: " + shop.getPrice() + " EGP , Distance: " +(int)shop.getDistance()+ "Km";
        }
        holder.price_distance.setText(string);
        holder.specialOffers.setText(shop.getSpecialOffers());
        holder.getDirection.setOnClickListener(v -> {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+shop.getLatitude()+","+shop.getLongitude()));
        mCtx.startActivity(intent);


    });
        holder.addToFav.setOnClickListener(v -> {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, INSERTION_URL + "?param1='" + email + "'&param2=" + shop.getId() + "&param3=" + product_id, response -> Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show(), error -> Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show());
                Volley.newRequestQueue(mCtx).add(stringRequest);
        });

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder{
        TextView shopName, price_distance, specialOffers;
        ImageButton addToFav;
        Button getDirection;


        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName=itemView.findViewById(R.id.Shop);
            price_distance=itemView.findViewById(R.id.Price);
            specialOffers=itemView.findViewById(R.id.SpecialOffers);
            addToFav=itemView.findViewById(R.id.deleteFav);
            getDirection=itemView.findViewById(R.id.getDirection);
        }
    }


}