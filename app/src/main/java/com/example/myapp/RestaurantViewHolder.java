package com.example.myapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    View view;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }
    public void setDetails(Context context, String title, String img){
        TextView rtitle=view.findViewById(R.id.tv_rest_title);
        ImageView rImage=view.findViewById(R.id.rest_image);

        rtitle.setText(title);
        Picasso.get().load(img).into(rImage);
    }
}
