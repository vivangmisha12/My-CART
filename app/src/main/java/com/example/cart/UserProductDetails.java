package com.example.cart;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cart.Model.ProductModel;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProductDetails extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_product_details);

        Intent i=getIntent();
        int id=i.getIntExtra("id",0);
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();


        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://edge.techpile.in/api/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService api =retrofit.create(ApiService.class);

        Call<ProductModel> idwiseproduct=api.getproductbyid(id);
        idwiseproduct.enqueue(new Callback<ProductModel>() {
            ImageView product_image;
            TextView product_name, product_selerate  , product_mrp , productdetails , productpacksize;
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.isSuccessful()){
                   Toast.makeText(getApplicationContext(),"ok ",LENGTH_LONG).show();

                    ProductModel p=response.body();
                    //get reference of all elements of  products details
                    ImageView product_image=findViewById(R.id.details_productimg);
                    TextView product_name=findViewById(R.id.details_productname);
                    TextView product_selerate=findViewById(R.id.details_productselerate);
                    TextView product_mrp=findViewById(R.id.details_productmrp);
                    TextView productdetails=findViewById(R.id.details_productdetail);
                    TextView productpacksize=findViewById(R.id.details_productpacksize);

                    Glide.with(getApplicationContext()).load(p.getPicture()).into(product_image);
                    product_name.setText(p.getName());
                    product_selerate.setText("₹"+p.getSaleRate());
                    product_mrp.setText("₹"+p.getMRP());
                    productdetails.setText(p.getDetails());
                    productpacksize.setText(p.getPackSize());
                    Toast.makeText(getApplicationContext(),"error in api",LENGTH_LONG).show();
                    //when api is called successfully handle the response






                }
                else{
                    Toast.makeText(getApplicationContext(),"error in api",LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"api failure",LENGTH_LONG).show();
            }
        });

    }

}
