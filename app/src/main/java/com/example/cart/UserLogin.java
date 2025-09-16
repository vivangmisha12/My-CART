package com.example.cart;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {
    TextView txt_gotoregisternow;
    MaterialButton btn_user_dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_gotoregisternow = findViewById(R.id.txt_gotoregisternow);
        btn_user_dash = findViewById(R.id.btn_user_dash);


        txt_gotoregisternow.setOnClickListener(v -> {
            Toast.makeText(this, "Going to Registration...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UserLogin.this, UserRegistration.class));
        });


        btn_user_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText edt_emailid = findViewById(R.id.edt_email);
                TextInputEditText edt_password = findViewById(R.id.edt_password);

                String email = edt_emailid.getText().toString();
                String password = edt_password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(UserLogin.this, UserDashboard.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(UserLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
}
