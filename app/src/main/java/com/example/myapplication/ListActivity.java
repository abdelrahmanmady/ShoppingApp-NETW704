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

public class ListActivity extends AppCompatActivity {
    private static final String PRODUCT_URL="http://192.168.1.102/LoginRegister/product.php";
    List<Product> productList;
    RecyclerView recyclerView;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        email=intent.getExtras().getString("email");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        loadProducts();

    }
    private void loadProducts(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, PRODUCT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray products=new JSONArray(response);

                    for(int i=0; i<products.length(); i++){
                        JSONObject productObject = products.getJSONObject(i);
                        int product_id = productObject.getInt("product_id");
                        String name=productObject.getString("name");
                        String description=productObject.getString("description");
                        String image_url=productObject.getString("image_url");
                        Product product=new Product(product_id,name,description,image_url);
                        productList.add(product);
                    }
                    ProductAdapter adapter = new ProductAdapter(ListActivity.this, productList,email);
                    recyclerView.setAdapter(adapter);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}