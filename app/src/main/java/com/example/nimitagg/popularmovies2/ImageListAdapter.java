package com.example.nimitagg.popularmovies2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Nimit Agg on 10-03-2016.
 */
public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public ImageListAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.image, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.image, null, true);
        }

        Picasso
                .with(context)
                .load(imageUrls[position])
                 // will explain later
                .into((ImageView)convertView.findViewById(R.id.imageView));

        return convertView;
    }
    public String getItem(int position){
        return imageUrls[position];
    }
}