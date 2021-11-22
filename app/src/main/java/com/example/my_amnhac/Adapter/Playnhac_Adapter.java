package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;

import java.util.ArrayList;

public class Playnhac_Adapter extends RecyclerView.Adapter<Playnhac_Adapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public Playnhac_Adapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_playbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.textindex.setText(position + 1 +"");
        holder.textcasi.setText(baihat.getCasi());
        holder.texttenbaihat.setText(baihat.getTenbaihat());

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textindex,texttenbaihat,textcasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textcasi = itemView.findViewById(R.id.textviewplaynhactencasi);
            texttenbaihat = itemView.findViewById(R.id.textviewplaynhactenbaihat);
            textindex = itemView.findViewById(R.id.textviewplaynhacindex);
        }
    }
}
