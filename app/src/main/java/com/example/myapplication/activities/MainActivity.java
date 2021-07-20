package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class MainActivity extends AppCompatActivity {

    TextView fullname,logOut;
    Button products,favourites;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        email=intent.getExtras().getString("email");

        fullname=findViewById(R.id.fullName);
        logOut=findViewById(R.id.logOut);
        products=findViewById(R.id.productsBtn);
        favourites=findViewById(R.id.favBtn);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            String[] field = new String[1];
            field[0] = "email";
            String[] data = new String[1];
            data[0]=email;
            PutData putData = new PutData("http://192.168.1.2/LoginRegister/fetch.php", "POST", field, data);
            if (putData.startPut()) {
                if(putData.onComplete()){
                    String result=putData.getResult();
                    result=Character.toUpperCase(result.charAt(0))+result.substring(1);
                    fullname.setText(result);
                }
            }
        });

        logOut.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent1);
            finish();
        });
        products.setOnClickListener(v -> {
            Intent intent12 =new Intent(getApplicationContext(), ListActivity.class);
            intent12.putExtra("email",email);
            startActivity(intent12);
        });
        favourites.setOnClickListener(v -> {
            Intent intent13 =new Intent(getApplicationContext(), Favourites.class);
            intent13.putExtra("email",email);
            startActivity(intent13);
        });

    }
}