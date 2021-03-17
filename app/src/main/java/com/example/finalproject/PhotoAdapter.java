package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.data.Photos;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoItemViewHolder> {
    ArrayList<String> imageUrls;
    ArrayList<String> authorNames;

    private ArrayList<PhotoDataItem> photoDataItems;
    private ArrayList<Photos> photos;

    public void updatePhotoResults(ArrayList<Photos> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public PhotoAdapter(MainActivity mainActivity, ArrayList<String> imageUrls, ArrayList<String> authorNames) {
        this.photoDataItems = new ArrayList<>();

        this.imageUrls = imageUrls;
        this.authorNames = authorNames;
    }


    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.photo_item, parent, false);
        return new PhotoItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return authorNames.size();
    }

    class PhotoItemViewHolder extends RecyclerView.ViewHolder {
        final private ImageView photoIV;
        final private TextView authorTV;




        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            photoIV = itemView.findViewById(R.id.iv_image);
            authorTV = itemView.findViewById(R.id.tv_author);
        }

        public void bind(int position) {
            Context ctx = this.itemView.getContext();
            authorTV.setText(authorNames.get(position));

            Glide.with(ctx)
                .load(imageUrls.get(position))
                .into(photoIV);



        }

    }

}
