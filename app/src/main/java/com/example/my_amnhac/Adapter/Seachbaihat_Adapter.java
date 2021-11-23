package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Activity.PlayNhacActivity;
import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Seachbaihat_Adapter extends RecyclerView.Adapter<Seachbaihat_Adapter.ViewHolder> {
    Context context;
    ArrayList<Baihat> mangbaihat;

    public Seachbaihat_Adapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_seachbaihat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = mangbaihat.get(position);
        holder.texttenbaihat.setText(baihat.getTenbaihat());
        holder.textcasi.setText(baihat.getCasi());
        Picasso.with(context).load(baihat.getHinhbaihat()).into(holder.imgbaihat);
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView texttenbaihat,textcasi;
        ImageView imgbaihat,imgluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            texttenbaihat = itemView.findViewById(R.id.textviewseachtenbaihat);
            textcasi = itemView.findViewById(R.id.textviewseachtencasi);
            imgbaihat = itemView.findViewById(R.id.imageviewseachbaihat);
            imgluotthich = itemView.findViewById(R.id.imageviewseachluotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgluotthich.setImageResource(R.drawable.iconloved);
                    Data_service data_service = API_service.getService();
                    Call<String> callback = data_service.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if (ketqua.equals("Success")){
                                Toast.makeText(context,"Đã Thích",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"Lỗi!!!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imgluotthich.setEnabled(false);
                }
            });
        }
    }
}
