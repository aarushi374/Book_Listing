package com.example.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<Books> {
    private static final String LOG_TAG = BooksAdapter.class.getSimpleName();

    public BooksAdapter(Context context, ArrayList<Books> earthquake) {
        super(context, 0, earthquake);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_layout, parent, false);
        }
        Books currentBook = getItem(position);
        ImageView image = (ImageView) listItemView.findViewById(R.id.img);
        Picasso.get().load(currentBook.getImg_link()).into(image);


        TextView author = (TextView) listItemView.findViewById(R.id.author);

        if (currentBook.getAuthors().equals("none")) {
            author.setVisibility(View.GONE);
        } else {
            author.setVisibility(View.VISIBLE);
            author.setText(currentBook.getAuthors());
        }

        return listItemView;
    }

}
