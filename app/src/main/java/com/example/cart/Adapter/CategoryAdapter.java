package com.example.cart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cart.CategoryWiseProduct;
import com.example.cart.Model.CategoryModel;
import com.example.cart.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context con;
    ArrayList<CategoryModel> data;
    public CategoryAdapter(Context con,ArrayList<CategoryModel> data){
        this.con=con;
        this.data=data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(con).inflate(R.layout.sample_category_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.sample_catname.setText(data.get(position).getCatName());
        Glide.with(con).load(data.get(position).getPicture()).into(holder.sample_caticon);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(con, CategoryWiseProduct.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("catid",data.get(position).getCatId());
                i.putExtra("catname",data.get(position).getCatName());
                con.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView sample_caticon;
        TextView sample_catname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sample_caticon=itemView.findViewById(R.id.sample_caticon);
            sample_catname=itemView.findViewById(R.id.sample_catname);
        }
    }
}
