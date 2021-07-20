package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonLogIn;
    TextView textSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        buttonLogIn=findViewById(R.id.btnLogin);
        textSignUp=findViewById(R.id.textViewSignUp);
        progressBar=findViewById(R.id.progress);

        buttonLogIn.setOnClickListener(v -> {
            String email, password;
            email= String.valueOf(editTextEmail.getText());
            password= String.valueOf(editTextPassword.getText());
            if(!email.equals("") && !password.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "password";
                    String[] data = new String[2];
                    data[0] = email;
                    data[1] = password;

                    PutData putData = new PutData("http://192.168.1.2/LoginRegister/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if(result.equals("Login Success")){
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Email and Password are required",Toast.LENGTH_SHORT).show();
            }
        });

        textSignUp.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}