package com.example.myapp;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapterRest extends RecyclerView.Adapter<RecyclerAdapterRest.ViewHolder> {
    private static final String tag="RecyclerView";
    private Context mcontext;
    private ArrayList<RestaurantData> restaurantData;

    public RecyclerAdapterRest(Context mcontext, ArrayList<RestaurantData> restaurantData) {
        this.mcontext = mcontext;
        this.restaurantData = restaurantData;
    }



    @NonNull
    @Override
    public RecyclerAdapterRest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_view,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(restaurantData.get(position).getTitle());
        //image glide
       /* Glide.with(mcontext)
                .load(restaurantData.get(position).getImg())
                .into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return restaurantData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView textView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageView);
        textView=itemView.findViewById(R.id.textView);

    }
}

}
