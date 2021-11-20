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
import com.example.my_amnhac.Model.Theloai;
import com.example.my_amnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Danhsachtheloaitheochude_Adapter extends RecyclerView.Adapter<Danhsachtheloaitheochude_Adapter.ViewHolder> {
    Context context;
    ArrayList<Theloai> theloaiArrayList;

    public Danhsachtheloaitheochude_Adapter(Context context, ArrayList<Theloai> theloaiArrayList) {
        this.context = context;
        this.theloaiArrayList = theloaiArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_theloaitheochude,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Theloai theloai = theloaiArrayList.get(position);
        Picasso.with(context).load(theloai.getHinhtheloai()).into(holder.imghinhnen);
        holder.texttentheloai.setText(theloai.getTentheloai());
    }

    @Override
    public int getItemCount() {
        return theloaiArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imghinhnen;
        TextView texttentheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhnen = itemView.findViewById(R.id.imageviewtheloaitheochude);
            texttentheloai = itemView.findViewById(R.id.textviewtentheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Danhsach_BaihatActivity.class);
                    intent.putExtra("idtheloai",theloaiArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
