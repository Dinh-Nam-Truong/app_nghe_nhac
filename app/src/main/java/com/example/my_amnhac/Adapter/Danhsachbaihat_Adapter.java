package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;

import java.util.ArrayList;

public class Danhsachbaihat_Adapter extends RecyclerView.Adapter<Danhsachbaihat_Adapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public Danhsachbaihat_Adapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danhsachbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.texttencasi.setText(baihat.getCasi());
        holder.texttenbaihat.setText(baihat.getTenbaihat());
        holder.textindex.setText(position + 1 + "");

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textindex, texttenbaihat, texttencasi;
        ImageView imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            texttencasi = itemView.findViewById(R.id.textviewtencasi);
            texttenbaihat = itemView.findViewById(R.id.textviewtenbaihat);
            textindex = itemView.findViewById(R.id.textviewdanhsachindex);
            imgluotthich = itemView.findViewById(R.id.imageviewluotthichdanhsachbaihat);
        }
    }
}
