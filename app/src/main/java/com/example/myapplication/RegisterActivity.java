package com.example.myapplication;

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

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextFullName, editTextEmail, editTextPassword, editTextAddress , editTextMobileNumber;
    Button buttonSignUp;
    TextView textViewLogIn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFullName=findViewById(R.id.fullname);
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        editTextAddress=findViewById(R.id.address);
        editTextMobileNumber=findViewById(R.id.mobilenumber);
        textViewLogIn=findViewById(R.id.logintext);
        buttonSignUp=findViewById(R.id.btnRegister);
        progressBar=findViewById(R.id.progress);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, email, password, address, mobilenumber;
                fullname= String.valueOf(editTextFullName.getText());
                email= String.valueOf(editTextEmail.getText());
                password= String.valueOf(editTextPassword.getText());
                address= String.valueOf(editTextAddress.getText());
                mobilenumber= String.valueOf(editTextMobileNumber.getText());

                if(!fullname.equals("") && !email.equals("") && !password.equals("") && !address.equals("") && !mobilenumber.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "fullname";
                            field[1] = "email";
                            field[2] = "password";
                            field[3] = "address";
                            field[4] = "mobilenumber";
                            String[] data = new String[5];
                            data[0] = fullname;
                            data[1] = email;
                            data[2] = password;
                            data[3] = address;
                            data[4] = mobilenumber;

                            PutData putData = new PutData("http://192.168.1.102/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });


        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}