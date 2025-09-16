package com.example.cart.Adapter;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cart.Model.CartproductModel;
import com.example.cart.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
   Context con;
   ArrayList<CartproductModel> cartitems;
   public  CartProductAdapter(Context con, ArrayList<CartproductModel> cartitems)
       {
        this.con=con;
        this.cartitems=cartitems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(con).inflate(R.layout.sample_cartproductlayoutparent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.ViewHolder holder, int position) {
          holder.cart_product_name.setText(cartitems.get(position).getProductName());
          holder.cart_product_price.setText("Price :"+cartitems.get(position).getSaleRate());
          holder.cart_product_quantity.setText("Quantity :"+cartitems.get(position).getUnit());

          Glide.with(con).load(cartitems.get(position).getPicture()).into(holder.cart_productimage);
          holder.samplecart_deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(con, "delete", Toast.LENGTH_SHORT).show();
                String emailid= FirebaseAuth.getInstance().getCurrentUser()!=null?FirebaseAuth.getInstance().getCurrentUser().getEmail():"";
                if(emailid!=null && !emailid.isEmpty()){

                    ProductAdapter p=new ProductAdapter();
                    p.AddToCart(emailid,cartitems.get(position).getPid(),0);
                    cartitems.remove(position);
                    notifyDataSetChanged();

                }
                else{
                    Toast.makeText(con, "Error in adding cart", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cart_product_name, cart_product_price, cart_product_quantity;
        ImageView cart_productimage;
        private ViewGroup parent;
        ImageView samplecart_deleteproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_product_name=itemView.findViewById(R.id.samplecart_productname);
            cart_product_price=itemView.findViewById(R.id.samplecart_productselrate);
            cart_product_quantity=itemView.findViewById(R.id.samplecart_productquantity);
            cart_productimage=itemView.findViewById(R.id.samplecart_productimage);
            samplecart_deleteproduct=itemView.findViewById(R.id.samplecart_deleteproduct);


        }
    }
}
