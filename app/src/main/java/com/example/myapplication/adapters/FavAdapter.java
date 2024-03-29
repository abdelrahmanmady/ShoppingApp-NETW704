package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.units.Favourite;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private final Context mCtx;
    private final List<Favourite> FavList;
    private final String email;
    private static final String DELETE_URL="http://192.168.1.2/LoginRegister/favdelete.php";

    public FavAdapter(Context mCtx, List<Favourite> favList,String email) {
        this.mCtx = mCtx;
        this.FavList = favList;
        this.email=email;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.favourites_list_layout, null);
        return new FavViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Favourite favourite=FavList.get(position);
        Glide.with(mCtx).load(favourite.getImage_url()).into(holder.image);
        holder.product.setText(favourite.getProduct());
        holder.shop.setText("Shop : "+favourite.getShop());
        holder.price.setText("Price: "+favourite.getPrice()+" EGP");
        holder.specialOffers.setText(favourite.getSpecialOffers());
        holder.deleteFav.setOnClickListener(v -> {
            StringRequest stringRequest=new StringRequest(Request.Method.GET, DELETE_URL + "?param1='" + email + "'&param2=" + favourite.getProduct_shop_id(), response -> {
                FavList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,FavList.size());
                Toast.makeText(mCtx,"Deleted from favourites", Toast.LENGTH_SHORT).show();

            }, error -> Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show());
            Volley.newRequestQueue(mCtx).add(stringRequest);

        });
        holder.getDirection.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+favourite.getLatitude()+","+favourite.getLongitude()));
            mCtx.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return FavList.size();
    }


    static class FavViewHolder extends RecyclerView.ViewHolder{
        TextView shop, product, price, specialOffers;
        ImageView image;
        ImageButton deleteFav;
        Button getDirection;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            shop=itemView.findViewById(R.id.Shop);
            product=itemView.findViewById(R.id.Product);
            specialOffers=itemView.findViewById(R.id.SpecialOffers);
            price=itemView.findViewById(R.id.Price);
            image=itemView.findViewById(R.id.productImage);
            deleteFav=itemView.findViewById(R.id.deleteFav);
            getDirection=itemView.findViewById(R.id.getDirection);
        }
    }
}
