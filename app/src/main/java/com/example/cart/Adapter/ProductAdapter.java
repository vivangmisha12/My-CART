package com.example.cart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cart.ApiService;
import com.example.cart.Model.ProductModel;
import com.example.cart.R;
import com.example.cart.UserProductDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public  ProductAdapter(){

    }
    Context con;
    ArrayList<ProductModel> data;
    public ProductAdapter(Context con,ArrayList<ProductModel> data){
        this.con=con;
        this.data=data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(con).inflate(R.layout.sample_product_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.sample_product_name.setText(data.get(position).getName());
        holder.sample_product_mrp.setText("₹"+data.get(position).getMRP()+"/-");
        holder.sample_product_selrate.setText("₹"+data.get(position).getSaleRate()+"/-");
        holder.sample_product_size.setText(data.get(position).getPackSize());
        Glide.with(con).load(data.get(position).getPicture()).into(holder.sample_product_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(con, UserProductDetails.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id",data.get(position).getId());
                con.startActivity(i);

            }
        });
        holder.sample_productadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.sample_productquantity.getText().toString());
                n++;
                holder.sample_productquantity.setText(String.valueOf(n+""));

                String emailid= FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                if(emailid!=null && !emailid.isEmpty()){
                    AddToCart(emailid,data.get(position).getId(),n);
                }
                else{
                    Toast.makeText(con, "Error in adding cart", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.sample_productremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(holder.sample_productquantity.getText().toString());
                n=n-1;
                holder.sample_productquantity.setText(n+"");
                String emailid= FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                if(emailid!=null && !emailid.isEmpty()){
                    AddToCart(emailid,data.get(position).getId(),n);
                }
                else{
                    Toast.makeText(con, "Error in adding cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddToCart(String emailid, int pid, int unit){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://edge.techpile.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);

        Call<String> addtocart = api.addtocart(emailid,pid,unit);
        addtocart.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String message = response.body();
                    //Toast.makeText(con, "ok", Toast.LENGTH_SHORT).show();

                }
                else{
                    //Toast.makeText(con, "Error in api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               // Toast.makeText(con, "errror", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder  extends  RecyclerView.ViewHolder{
        ImageView sample_product_img;
        TextView sample_product_name,sample_product_mrp,sample_product_selrate,sample_product_size;
        TextView sample_productremove,sample_productquantity,sample_productadd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sample_product_name=itemView.findViewById(R.id.sample_product_name);
            sample_product_mrp=itemView.findViewById(R.id.sample_product_mrp);
            sample_product_selrate=itemView.findViewById(R.id.sample_product_selrate);
            sample_product_size=itemView.findViewById(R.id.sample_product_size);
            sample_product_img=itemView.findViewById(R.id.sample_product_img);
            sample_productadd=itemView.findViewById(R.id.sample_productadd);
            sample_productremove=itemView.findViewById(R.id.sample_productremove);
            sample_productquantity=itemView.findViewById(R.id.sample_productquantity);



        }
    }
}
