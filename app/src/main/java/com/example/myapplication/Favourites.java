package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {
    private static final String FAV_URL="http://192.168.1.102/LoginRegister/fav.php";
    private String email;
    List<Favourite> favList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        email=intent.getExtras().getString("email");
        favList = new ArrayList<>();
        loadFavourites();



    }
    private void loadFavourites(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, FAV_URL+"?param1='"+email+"'", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray favourites= new JSONArray(response);
                    for(int i=0;i<favourites.length();i++){
                        JSONObject favouriteObject = favourites.getJSONObject(i);
                        int id=favouriteObject.getInt("id");
                        String shop= favouriteObject.getString("shop_name");
                        String product= favouriteObject.getString("product_name");
                        int price= favouriteObject.getInt("price");
                        String specialOffers=favouriteObject.getString("specialOffers");
                        String image_url=favouriteObject.getString("image_url");
                        double latitude=favouriteObject.getDouble("latitude");
                        double longitude=favouriteObject.getDouble("longitude");
                        Favourite favourite=new Favourite(id,shop,product,price,specialOffers,image_url,latitude,longitude);
                        favList.add(favourite);

                    }
                    FavAdapter adapter=new FavAdapter(Favourites.this,favList,email);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Favourites.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}