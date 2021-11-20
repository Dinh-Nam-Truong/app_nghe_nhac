package com.example.my_amnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.my_amnhac.Activity.Danhsach_BaihatActivity;
import com.example.my_amnhac.Activity.Danhsach_TatcachudeActivity;
import com.example.my_amnhac.Activity.Danhsach_theloaitheochudeActivity;
import com.example.my_amnhac.Model.Chude;
import com.example.my_amnhac.Model.Chudevatheloai;
import com.example.my_amnhac.Model.Theloai;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_chudevatheloai extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView textxemthemchudetheloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chudevatheloai,container,false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollview);
        textxemthemchudetheloai = view.findViewById(R.id.textviewxemthem);
        textxemthemchudetheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Danhsach_TatcachudeActivity.class);
                startActivity(intent);

            }
        });
        GetData();
        return view;
    }

    private void GetData() {
        Data_service data_service = API_service.getService();
        Call<Chudevatheloai> callback = data_service.GetDataChuDeVaTheLoai();
        callback.enqueue(new Callback<Chudevatheloai>() {
            @Override
            public void onResponse(Call<Chudevatheloai> call, Response<Chudevatheloai> response) {
                Chudevatheloai chudevatheloaiArrayList = response.body();

                final ArrayList<Chude> chudeArrayList = new ArrayList<>();
                chudeArrayList.addAll(chudevatheloaiArrayList.getChude());
                final ArrayList<Theloai> theloaiArrayList = new ArrayList<>();
                theloaiArrayList.addAll(chudevatheloaiArrayList.getTheloai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580,250);
                layout.setMargins(10,20,10,30);
                for (int i=0; i<chudeArrayList.size(); i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (chudeArrayList.get(i).getHinhchude() != null){
                        Picasso.with(getActivity()).load(chudeArrayList.get(i).getHinhchude()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Danhsach_theloaitheochudeActivity.class);
                            intent.putExtra("chude",chudeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });
                }
                for (int j=0; j<chudeArrayList.size(); j++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (theloaiArrayList.get(j).getHinhtheloai() != null){
                        Picasso.with(getActivity()).load(theloaiArrayList.get(j).getHinhtheloai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Danhsach_BaihatActivity.class);
                            intent.putExtra("idtheloai",theloaiArrayList.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<Chudevatheloai> call, Throwable t) {

            }
        });
    }

}
