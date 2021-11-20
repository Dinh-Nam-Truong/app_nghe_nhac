package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Activity.Danhsach_BaihatActivity;
import com.example.my_amnhac.Model.Album;
import com.example.my_amnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Allalbum_Adapter extends RecyclerView.Adapter<Allalbum_Adapter.ViewHolder> {
    Context context;
    ArrayList<Album> albumArrayList;

    public Allalbum_Adapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_allalbum,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        Picasso.with(context).load(album.getHinhalbum()).into(holder.imgallalbum);
        holder.texttenallalbum.setText(album.getTenalbum());

    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgallalbum;
        TextView texttenallalbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgallalbum = itemView.findViewById(R.id.imageviewallalbum);
            texttenallalbum = itemView.findViewById(R.id.textviewtenallalbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Danhsach_BaihatActivity.class);
                    intent.putExtra("album",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
