package com.example.cart;

import static android.app.ProgressDialog.show;
import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cart.Adapter.CategoryAdapter;
import com.example.cart.Adapter.ProductAdapter;
import com.example.cart.Model.CategoryModel;
import com.example.cart.Model.ProductModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDashboard extends AppCompatActivity {
    DrawerLayout userdrawer_layout;

    Toolbar user_toolbar;
    NavigationView user_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dashboard);


        userdrawer_layout = findViewById(R.id.userdrawer_layout);
        user_toolbar = findViewById(R.id.user_toolbar);
        user_nav = findViewById(R.id.user_nav);

        setSupportActionBar(user_toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, userdrawer_layout, user_toolbar, R.string.welcom_msg, R.string.app_name);
        userdrawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        user_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_uhome) {
                    Intent i = new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.menu_uperson) {
                    Intent i = new Intent(getApplicationContext(), Profile.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.menu_usetting) {
                    Intent i = new Intent(getApplicationContext(), UserSetting.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.menu_unotification) {
                    Intent i = new Intent(getApplicationContext(), UserNotification.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.menu_uorders) {
                    Intent i = new Intent(getApplicationContext(), UserOrders.class);
                    startActivity(i);
                } else if (menuItem.getItemId() == R.id.menu_uhelp) {
                    Intent i = new Intent(getApplicationContext(), UserHelp.class);
                    startActivity(i);
                }
                else if (menuItem.getItemId() == R.id.menu_user_cart) {
                    Intent i = new Intent(getApplicationContext(), UserCart.class);
                    startActivity(i);
                }
                else if (menuItem.getItemId() == R.id.menu_ulogout) {

                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(getApplicationContext(), UserLogin.class);
                    startActivity(i);
                }
                return true;
            }

        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())  // ✅ Correct method
                .build();

        ApiService apiService=retrofit.create(ApiService.class);
        Call<ArrayList< CategoryModel>> category= apiService.getAllCategories();
        category.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                if(response.isSuccessful()){
                   // Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                    //when api is called successfuly ,handle the response
                    ArrayList<CategoryModel> data = response.body();
                    RecyclerView recycle_cat=findViewById(R.id.recycler_category);
                    recycle_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    CategoryAdapter adapter = new CategoryAdapter(getApplication(),data);
                    recycle_cat.setAdapter(adapter);
                   // Toast.makeText(getApplicationContext(),data.size()+"",LENGTH_LONG ).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error In API", Toast.LENGTH_SHORT).show();
                    //when api fails to call server error occured
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "API not called", Toast.LENGTH_SHORT).show();
            }
        });
        //call product api and bind the products into recylerview
        Call<ArrayList<ProductModel>>  products= apiService.getAllproducts();
        products.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.isSuccessful()){
                   ArrayList<ProductModel> products=response.body();
                   RecyclerView recycle_product=findViewById(R.id.recycler_product);
                   recycle_product.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

                   ProductAdapter adapter=new ProductAdapter(getApplicationContext(),products);
                   recycle_product.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext()," not ok",LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent i = new Intent(UserDashboard.this, UserLogin.class);
            startActivity(i);
        }
    }
}
