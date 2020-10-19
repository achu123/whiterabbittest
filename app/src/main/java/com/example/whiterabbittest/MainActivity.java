package com.example.whiterabbittest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whiterabbittest.models.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    public LinearLayoutManager mLayoutManager;
    List<Item> resultArrayList;
    public static Item item = new Item();
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupRecyclerView();

        searchFunctionality();

        db = new DatabaseHandler(MainActivity.this);

        if (db.getitemsCount()==0) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.mocky.io/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            getItems();

        } else {
            List<Item> items = db.getAllItems();
            resultArrayList = new ArrayList<>();
            resultArrayList.addAll(items);
            mAdapter.notifyDataSetChanged();

        }


    }

    private void searchFunctionality() {



        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



    }
    private void filter(String text) {
        ArrayList<Item> filteredList = new ArrayList<>();
        for (Item item : resultArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())||item.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        resultArrayList = new ArrayList<>();
        mAdapter = new MyAdapter(resultArrayList, this);
        recyclerView.setAdapter(mAdapter);


    }

    private void getItems() {

        Call<List<Item>> call = jsonPlaceHolderApi.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (!response.isSuccessful()) {
//                    textViewResult.setText("Code: " + response.code());
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Item> postResponse = response.body();
//                String content = "";
//                content += "Code: " + response.code() + "\n";
//                content += "ID: " + postResponse.getCount() + "\n";

                resultArrayList.addAll(postResponse);
                mAdapter.notifyDataSetChanged();


                for (Item item : postResponse) {
                    db.additem(item);
                }


                // Reading all contacts
                Log.d("Reading: ", "Reading all contacts..");
                List<Item> items = db.getAllItems();

                for (Item cn : items) {
                    String log = "Name: " + cn.getName() + " ,Phone: " +
                            cn.getPhone();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }


            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

