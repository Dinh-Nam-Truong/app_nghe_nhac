package com.example.my_amnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.media.MediaDrm;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.my_amnhac.Activity.Danhsach_BaihatActivity;
import com.example.my_amnhac.Model.QuangCao;
import com.example.my_amnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Banner_Adapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrayListbanner;

    public Banner_Adapter(Context context, ArrayList<QuangCao> arrayListbanner) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
    }

    @Override
    public int getCount() {
        return arrayListbanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_banner,null);
        ImageView imgbackgroundbanner = view.findViewById(R.id.imageviewbackgroundbanner);
        ImageView imgsongbanner = view.findViewById(R.id.imageviewbanner);
        TextView texttitlesongbanner =  view.findViewById(R.id.textviewtitlebannerbaihat);
        TextView textnoidung=  view.findViewById(R.id.textviewnoidung);
        Picasso.with(context).load(arrayListbanner.get(position).getHinhanh()).into(imgbackgroundbanner);
        Picasso.with(context).load(arrayListbanner.get(position).getHinhbaihat()).into(imgsongbanner);
        texttitlesongbanner.setText(arrayListbanner.get(position).getTenbaihat());
        textnoidung.setText(arrayListbanner.get(position).getNoidung());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Danhsach_BaihatActivity.class);
                intent.putExtra("banner",arrayListbanner.get(position));
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
