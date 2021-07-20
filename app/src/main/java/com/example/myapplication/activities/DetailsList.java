package com.example.myapplication.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ShopAdapter;
import com.example.myapplication.adapters.sortDialog;
import com.example.myapplication.units.Shop;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsList extends AppCompatActivity implements sortDialog.SingleChoiceListener {
    private static final String PRODUCT_SHOP_URL="http://192.168.1.2/LoginRegister/product_shop.php";
    private static final int REQUEST_CODE = 101;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private int product_id;
    TextView productName, productDescription;
    ImageView productImage;
    Button sortBtn;
    List<Shop> shopList;
    RecyclerView recyclerView;
    double currentLat,currentLong;
    public ShopAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        Intent intent=getIntent();
        sortBtn=findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(v -> {
            sortDialog sortDialog=new sortDialog();
            sortDialog.setCancelable(false);
            sortDialog.show(getSupportFragmentManager(),"Sort Dialog");

        });
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        String email = intent.getExtras().getString("email");
        product_id=intent.getExtras().getInt("product_id");
        String name = intent.getExtras().getString("productName");
        String description = intent.getExtras().getString("productDescription");
        String image_url = intent.getExtras().getString("image_url");

        productName=findViewById(R.id.Shop);
        productDescription=findViewById(R.id.productDescription);
        productImage=findViewById(R.id.productImage);

        productName.setText(name);
        productDescription.setText(description);
        Glide.with(getApplicationContext()).load(image_url).into(productImage);
        shopList = new ArrayList<>();
        adapter = new ShopAdapter(DetailsList.this, shopList, email, product_id);
        recyclerView.setAdapter(adapter);
        loadShops();

    }
    private void loadShops() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCT_SHOP_URL + "?param1=" + product_id, response -> {
            try {
                JSONArray productShops = new JSONArray(response);

                for (int i = 0; i < productShops.length(); i++) {
                    JSONObject productShop = productShops.getJSONObject(i);
                    int shop_id = productShop.getInt("shop_id");
                    int price = productShop.getInt("price");
                    String specialOffers = productShop.getString("specialOffers");
                    String shopName = productShop.getString("name");
                    double latitude = productShop.getDouble("latitude");
                    double longitude = productShop.getDouble("longitude");

                    Shop shopFinal = new Shop(shop_id, shopName, price, specialOffers, latitude, longitude);
                    shopList.add(shopFinal);

                }

                calculateDistance(currentLat,currentLong,shopList);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(DetailsList.this, error.getMessage(), Toast.LENGTH_SHORT).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        switch(list[position]) {
            case "Price":
                shopList.sort(Shop.shopPriceComparator);
                adapter.notifyDataSetChanged();
                break;

            case "Distance":
                shopList.sort(Shop.shopDistanceComparator);
                adapter.notifyDataSetChanged();
                break;
        }

    }
    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(getApplicationContext(),"No sorting parameter is selected",Toast.LENGTH_SHORT).show();
    }
    public void calculateDistance(double curLat, double curLong,List<Shop> shopList) {
        for (int i = 0; i < shopList.size(); i++) {
            Shop shop=shopList.get(i);
            Location current = new Location("");
            current.setLatitude(curLat);
            current.setLongitude(curLong);
            Location shopLocation = new Location("");
            shopLocation.setLatitude(shop.getLatitude());
            shopLocation.setLongitude(shop.getLongitude());
            double distance = shopLocation.distanceTo(current)/1000;
            shopList.get(i).setDistance(distance);
        }
    }
    private void fetchLastLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        currentLat=location.getLatitude();
                        currentLong=location.getLongitude();
                    }
                });
    }
}