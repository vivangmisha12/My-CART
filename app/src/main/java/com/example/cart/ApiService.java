package com.example.cart;

import com.example.cart.Model.CartModel;
import com.example.cart.Model.CategoryModel;
import com.example.cart.Model.ProductModel;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("getallcategories")
    Call<ArrayList<CategoryModel>> getAllCategories();

    @GET("getallproducts")
    Call<ArrayList<ProductModel>> getAllproducts();


    @GET("getproductbyid")
    Call<ProductModel>getproductbyid(@Query("id") int id);

    @GET("getproductsbycategory")
    Call<ArrayList<ProductModel>>getproductsbycategory(@Query("catid") int  catid);


    @GET("addtocart")
    Call<String> addtocart(@Query("emailid") String userid, @Query("pid") int ptid, @Query("Unit") int unit);

    @GET("getcartdetails")
    Call<CartModel> getcartdetails(@Query("emailid") String userid);

    @GET("insertorder")
    Call<String>insertorder(@Query("name")String name,
                            @Query("mobno")String mobno,
                            @Query("email")String email,
                            @Query("houseno")String houseno,
                            @Query("landmark")String landmark,
                            @Query("fulladdress")String fulladdress,
                            @Query("pincode")String pincode,
                            @Query("totalitems")int totalitems,
                            @Query("totalbillamount") double totalbillamount,
                            @Query("orderstatus")String orderstatus,
                            @Query("orderdate") String orderdate,
                            @Query("deliverydate") Date deliverydate);


}