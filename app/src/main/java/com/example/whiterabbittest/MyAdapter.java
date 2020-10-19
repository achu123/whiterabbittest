package com.example.whiterabbittest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whiterabbittest.models.Item;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ExampleViewHolder> {
    private final Context context;
    private ArrayList<Item> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title,name;
        RelativeLayout rv;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            rv=itemView.findViewById(R.id.parent);

        }
    }

    public MyAdapter(List<Item> exampleList, Context context) {
        mExampleList = (ArrayList<Item>) exampleList;
        this.context = context;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Item currentItem = mExampleList.get(position);

        String name = currentItem.getName();
        String Url = currentItem.getProfileImage();

        holder.title.setText("Name:"+name);
        try {
            holder.name.setText("Company Name:" + currentItem.getCompany().getName());
        }
        catch(NullPointerException e)
        {
            holder.name.setText("Company Name: no data available");

        }

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.item=currentItem;
                Intent i=new Intent(context,DetailsActivity.class);
                context.startActivity(i);
            }
        });



            Glide.with(context)
                    .load(Url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<Item> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}