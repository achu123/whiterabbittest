package com.example.whiterabbittest;


import com.example.whiterabbittest.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
//    @Headers({"Accept: application/json"})
    @GET("5d565297300000680030a986")
    Call<List<Item>> getItems();


}
