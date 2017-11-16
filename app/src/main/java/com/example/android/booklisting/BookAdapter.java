package com.example.android.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Pero on 6.11.2017..
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter (Context context, List<Book> books){
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView  = convertView;

        if(listItemView == null) {
            listItemView  = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }
        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        authorView.setText(currentBook.getAuthor());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        String imageUrl = currentBook.getImage();

        Picasso.with(getContext()).load(imageUrl).into(imageView);

        return listItemView;
    }
}
