package com.example.cart;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    MaterialButton btn_userlogin,btn_adminlogin;
    int currentState=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_userlogin=findViewById(R.id.btn_userlogin);
        btn_adminlogin=findViewById(R.id.btn_adminlogin);


        //click event of button
        btn_userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,UserLogin.class);
                startActivity(i);
            }
        });
        //end click event of button

        //start click event of admin login
        btn_adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(i);
            }
        });

        //end click event of admin login



//    Oncreate = first time to open app
//    Onstart = when app is open
//    Onresume = when app is in foreground
//    Onpause = when app is in background
//    Onstop = when app is closed
//    Ondestroy = when app is closed


}

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null && !FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("myadmin@gmail.com")){
            Intent i = new Intent(MainActivity.this,UserDashboard.class);
            startActivity(i);
        } else if (FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("myadmin@gmail.com")) {
            Intent i = new Intent(MainActivity.this,AdminDashboard.class);
            startActivity(i);
        }
    }
}