package com.example.whiterabbittest;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.whiterabbittest.models.Address;
import com.example.whiterabbittest.models.Company;
import com.example.whiterabbittest.models.Item;

public class DetailsActivity extends AppCompatActivity {
    ImageView dp;
    TextView address, phno, name, website, companydetails,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);

        address = findViewById(R.id.add);
        phno = findViewById(R.id.ph);
        email = findViewById(R.id.email);

        name = findViewById(R.id.name);
        website = findViewById(R.id.website);
        companydetails = findViewById(R.id.company);
        dp = findViewById(R.id.image);
        email = findViewById(R.id.email);

        Item item = MainActivity.item;
        Address address1 = item.getAddress();
        Company company = item.getCompany();
        String url = item.getProfileImage();

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(dp);
        name.setText("Name:" + item.getName());
        try {
            phno.setText("Phone:" + item.getPhone());
        } catch (NullPointerException e) {

        }

        try {
            email.setText("Email:" + item.getEmail());
        } catch (NullPointerException e) {

        }

        website.setText("Website:" + item.getWebsite());


        address.setText("Address:" + address1.getStreet() + ",\n" + address1.getSuite() + ",\n" + address1.getCity() + ",\n" + address1.getZipcode() + ",\n");

        try {
            companydetails.setText("Company Details \n\n" + "company name:" + company.getName() + ",\n" + "catch phrase:" + company.getCatchPhrase() + ",\n" + "bs:" + company.getBs());
        } catch (NullPointerException e) {

        }


    }

}
