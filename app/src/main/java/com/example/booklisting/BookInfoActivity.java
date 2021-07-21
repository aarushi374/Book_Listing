package com.example.booklisting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookInfoActivity extends AppCompatActivity {

    TextView price, published_by, title, subTitle, description, ratingsCount, category, date, rating;
    String txtprice, txtpublished_by, txtsubTitle, txtdescription, txtratingsCount, txtcategory, txtdate, txtrating;
    Button link;
    ImageView image;
    RatingBar rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


        price = findViewById(R.id.price);
        published_by = findViewById(R.id.published_by);
        title = findViewById(R.id.title);
        subTitle = findViewById(R.id.subTitle);
        description = findViewById(R.id.description);
        ratingsCount = findViewById(R.id.ratingsCount);
        category = findViewById(R.id.category);
        date = findViewById(R.id.date);
        rating = findViewById(R.id.rating);
        link = findViewById(R.id.preview);
        image = findViewById(R.id.img);
        rate = findViewById(R.id.rating_bar);

        txtprice = getIntent().getStringExtra("price");
        txtcategory = getIntent().getStringExtra("category");
        txtdate = getIntent().getStringExtra("date");
        txtdescription = getIntent().getStringExtra("description");
        txtpublished_by = getIntent().getStringExtra("publisher");
        txtrating = getIntent().getStringExtra("rating");
        txtratingsCount = getIntent().getStringExtra("ratingsCount");
        txtsubTitle = getIntent().getStringExtra("subTitle");

        title.setText(getIntent().getStringExtra("title"));
        if (txtprice.equals("none")) {
            price.setVisibility(View.GONE);
        } else {
            price.setVisibility(View.VISIBLE);
            price.setText(getResources().getString(R.string.inr) + " " + txtprice);
        }
        if (txtpublished_by.equals("none")) {
            published_by.setVisibility(View.GONE);
        } else {
            published_by.setVisibility(View.VISIBLE);
            published_by.setText(getResources().getString(R.string.publisher) + " " + txtpublished_by);
        }
        if (txtsubTitle.equals("none")) {
            subTitle.setVisibility(View.GONE);
        } else {
            subTitle.setVisibility(View.VISIBLE);
            subTitle.setText(txtsubTitle);
        }
        if (txtdescription.equals("none")) {
            description.setVisibility(View.GONE);
        } else {
            description.setVisibility(View.VISIBLE);
            description.setText(txtdescription);
        }
        if (txtratingsCount.equals("none")) {
            ratingsCount.setVisibility(View.GONE);
        } else {
            ratingsCount.setVisibility(View.VISIBLE);
            ratingsCount.setText(txtratingsCount + " " + getResources().getString(R.string.no_of_reviews));
        }
        if (txtcategory.equals("none")) {
            category.setVisibility(View.GONE);
            title.setPadding(0, 20, 0, 0);
        } else {
            category.setVisibility(View.VISIBLE);
            category.setText(txtcategory);
        }
        if (txtdate.equals("none")) {
            date.setVisibility(View.GONE);
        } else {
            date.setVisibility(View.VISIBLE);
            date.setText(txtdate);
        }
        if (txtrating.equals("none")) {
            rating.setVisibility(View.GONE);
            rate.setVisibility(View.GONE);
        } else {
            rating.setVisibility(View.VISIBLE);
            rate.setVisibility(View.VISIBLE);
            rating.setText(txtrating);
            float frate = Float.parseFloat(getIntent().getStringExtra("rating"));
            rate.setRating(frate);
        }
        Picasso.get().load(getIntent().getStringExtra("img_link")).into(image);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri IntentUri = Uri.parse(getIntent().getStringExtra("info_link"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}