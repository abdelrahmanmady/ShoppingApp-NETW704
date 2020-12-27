package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{
    private Context mCtx;
    private List<Shop> shopList;
    private String email;
    private int product_id;
    private double curLat,curLong;
    private static final String INSERTION_URL="http://192.168.1.102/LoginRegister/favinsert.php";


    public ShopAdapter(Context mCtx, List<Shop> shopList,String email,int product_id,double curLat,double curLong){
        this.mCtx = mCtx;
        this.shopList = shopList;
        this.email=email;
        this.product_id=product_id;
        this.curLat=curLat;
        this.curLong=curLong;

    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.details_layout,null);
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
        holder.getDirection.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+shop.getLatitude()+","+shop.getLongitude()));
                mCtx.startActivity(intent);


            }
        });
        holder.addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, INSERTION_URL + "?param1='" + email + "'&param2=" + shop.getId() + "&param3=" + product_id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(mCtx, response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    Volley.newRequestQueue(mCtx).add(stringRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder{
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