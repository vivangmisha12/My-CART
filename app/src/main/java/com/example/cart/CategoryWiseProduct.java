package com.example.cart;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cart.Adapter.ProductAdapter;
import com.example.cart.Model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryWiseProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_wise_product);
        Intent i =getIntent();
        int catid=i.getIntExtra("catid",0);
        String catname=i.getStringExtra("catname");
        //Toast.makeText(this,catid+" "+catname,Toast.LENGTH_LONG).show();
        TextView categoryname=findViewById(R.id.categoryname);
        categoryname.setText(catname);


        //Call the api to get data of all products of a perticular category
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api =retrofit.create(ApiService.class);
        Call<ArrayList< ProductModel>> products =api.getproductsbycategory(catid);
        products.enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(getApplicationContext(),"ok ",LENGTH_LONG).show();
                    ArrayList<ProductModel> data=response.body();

                    //bind the data into recycleview
                    if(data!=null){


                    RecyclerView recyler =findViewById(R.id.recyler_categorywiseproduct);
                    GridLayoutManager grid =new GridLayoutManager(getApplicationContext(),2);
                    recyler.setLayoutManager(grid);

                    ProductAdapter adapter =new ProductAdapter(getApplicationContext(),data);
                    recyler.setAdapter(adapter);}
                    else{
                        Toast.makeText(getApplicationContext(),"No Product Found",LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error in api",LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure on server",LENGTH_LONG).show();
            }
        });

    }
}