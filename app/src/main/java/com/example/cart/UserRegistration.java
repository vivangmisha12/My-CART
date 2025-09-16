package com.example.cart;

import static android.widget.Toast.LENGTH_LONG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cart.R;
import com.example.cart.UserLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class UserRegistration extends AppCompatActivity {

    TextView registeration;

    TextInputEditText edt_name,edt_mob,edt_password,edt_email,edt_address,edt_pincode,edt_landmark,edt_house,edt_pass;
    MaterialButton btn_register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        registeration=findViewById(R.id.txt_gotologin);

        edt_name=findViewById(R.id.edt_name);
        edt_mob=findViewById(R.id.edt_mobno);
        edt_password=findViewById(R.id.edt_password);
        edt_pass=findViewById(R.id.Correct);
        edt_email=findViewById(R.id.edt_mail);
        edt_address=findViewById(R.id.edt_address);
        edt_pincode=findViewById(R.id.edt_pin);
        edt_landmark=findViewById(R.id.edt_land);
        edt_house=findViewById(R.id.edt_house);
        btn_register=findViewById(R.id.btn_register);



        registeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserRegistration.this, UserLogin.class);
                startActivity(i);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString();
                String mob = edt_mob.getText().toString();
                String addresh = edt_address.getText().toString();
                String landmark = edt_landmark.getText().toString();
                String pincode = edt_pincode.getText().toString();
                String housenumber = edt_house.getText().toString();
                String password = edt_password.getText().toString();
                String Cpass = edt_pass.getText().toString();


                if (name.isEmpty() || email.isEmpty() || mob.isEmpty() || addresh.isEmpty() ||
                        landmark.isEmpty() || pincode.isEmpty() || housenumber.isEmpty() ||
                        password.isEmpty() || Cpass.isEmpty()) {
                    Toast.makeText(UserRegistration.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.endsWith("@gmail.com")){
                    Toast.makeText(UserRegistration.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(UserRegistration.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(Cpass)) {
                    Toast.makeText(UserRegistration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap map = new HashMap();
                map.put("name", name);
                map.put("email", email);
                map.put("mob", mob);
                map.put("addresh", addresh);
                map.put("landmark", landmark);
                map.put("pincode", pincode);
                map.put("housenumber", housenumber);
                map.put("password", password);
                map.put("Cpass", Cpass);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            String uid=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                                        Intent in=new Intent(UserRegistration.this,UserLogin.class);
                                        startActivity(in);

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }


                    }
                });

            }
        });
    }
}

