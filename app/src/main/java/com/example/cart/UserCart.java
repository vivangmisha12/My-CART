package com.example.cart;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cart.Adapter.CartProductAdapter;
import com.example.cart.Model.CartModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserCart extends AppCompatActivity {

    TextView cart_username, cart_user_address, cart_user_mobileno, cart_shopping_amount,
            cart_discount, cart_dilveryfee, cart_platformfee, cart_total_amount, cart_total_amount_botton;
    MaterialButton cart_place_order;
    RecyclerView cart_recycler;
    String name="", useraddress="", pincode="",mobno="";
    int total=0,totalitems=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_cart);

        // Initialize views
        cart_username = findViewById(R.id.cart_username);
        cart_user_address = findViewById(R.id.cart_user_address);
        cart_user_mobileno = findViewById(R.id.cart_user_mobileno);

        cart_shopping_amount = findViewById(R.id.cart_shopping_amount);
        cart_discount = findViewById(R.id.cart_discount);
        cart_dilveryfee = findViewById(R.id.cart_dilveryfee);
        cart_platformfee = findViewById(R.id.cart_platformfee);
        cart_total_amount = findViewById(R.id.cart_total_amount);
        cart_total_amount_botton = findViewById(R.id.cart_total_amount_botton);
        cart_place_order = findViewById(R.id.cart_place_order);
        cart_recycler = findViewById(R.id.cart_product_recycler);


        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();



        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                             name = snapshot.child("name").getValue()!=null? snapshot.child("name").getValue(String.class):"";
                             useraddress = snapshot.child("address").getValue()!=null? snapshot.child("address").getValue(String.class):"";
                             mobno = snapshot.child("mobno").getValue()!=null? snapshot.child("mobno").getValue(String.class):"";
                             pincode=snapshot.child("pincode").getValue()!=null? snapshot.child("pincode").getValue(String.class):"";
                             cart_username.setText("welcome,"+name);
                             cart_user_address.setText("Delivery Address :"+useraddress);
                             cart_user_mobileno.setText("Context no,"+mobno);

                        } else {
                            Toast.makeText(getApplicationContext(), "Error in Uid", LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Failed", LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();

    //get all data of cart product
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://edge.techpile.in/api/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService api =retrofit.create(ApiService.class);
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Call<CartModel> cart= api.getcartdetails(email);
        cart.enqueue(new Callback<CartModel>() {
                         @Override
                         public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                             if (response.isSuccessful()) {
                                 //Toast.makeText(getApplicationContext(), "OK", LENGTH_LONG).show();
                                 CartModel data = response.body();
                                 if (data != null) {
                                     cart_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                     CartProductAdapter adapter = new CartProductAdapter(getApplicationContext(), data.getCartItems());
                                     cart_recycler.setAdapter(adapter);
                                     cart_shopping_amount.setText("Rs." + data.getMrp() + "");
                                     cart_discount.setText("Rs." + data.getDiscountAmount() + "");
                                     cart_total_amount.setText("Rs." + data.getTotalAmount() + "");
                                     cart_total_amount_botton.setText("Rs." + data.getTotalAmount() + "");
                                     cart_dilveryfee.setText("Rs.50");
                                     cart_platformfee.setText("Rs. 20");
                                     total=data.getTotalAmount();
                                     totalitems=data.getToatlItems();

                                 } else {
                                     Toast.makeText(getApplicationContext(), " No Product Added", LENGTH_LONG).show();
                                 }

                             } else {
                                 Toast.makeText(getApplicationContext(), "Error in API", LENGTH_LONG).show();
                             }

                         }

                         @Override
                         public void onFailure(Call<CartModel> call, Throwable t) {
                             Toast.makeText(getApplicationContext(), " Server Failure", LENGTH_LONG).show();
                         }
                     });

            cart_place_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                      //Call<String> placeorder=api.insertorder(name,mobno,houseno,landmark,useraddress,pincode,email,totalitems,total,"Pending",new Date().toString(),new Date());
            }

        });

    }
}
