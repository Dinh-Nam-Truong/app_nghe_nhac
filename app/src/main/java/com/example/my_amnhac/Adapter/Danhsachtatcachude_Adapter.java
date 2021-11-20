package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Activity.Danhsach_theloaitheochudeActivity;
import com.example.my_amnhac.Model.Chude;
import com.example.my_amnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Danhsachtatcachude_Adapter extends RecyclerView.Adapter<Danhsachtatcachude_Adapter.ViewHolder> {
    Context context;
    ArrayList<Chude> mangchude;

    public Danhsachtatcachude_Adapter(Context context, ArrayList<Chude> mangchude) {
        this.context = context;
        this.mangchude = mangchude;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_cacchude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chude chude = mangchude.get(position);
        Picasso.with(context).load(chude.getHinhchude()).into(holder.imgchhude);
    }

    @Override
    public int getItemCount() {
        return mangchude.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgchhude;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchhude= itemView.findViewById(R.id.imageviewdongcacchude);
            imgchhude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Danhsach_theloaitheochudeActivity.class);
                    intent.putExtra("chude",mangchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
