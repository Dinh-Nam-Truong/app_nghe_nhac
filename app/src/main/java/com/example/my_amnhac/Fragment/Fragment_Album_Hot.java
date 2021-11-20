package com.example.my_amnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Activity.Danhsach_tatcaalbumActivity;
import com.example.my_amnhac.Adapter.Album_Adapter;
import com.example.my_amnhac.Model.Album;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Album_Hot extends Fragment {
    View view;
    RecyclerView recyclerView;
    TextView textxemthemalbum;
    Album_Adapter album_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album_hot,container,false);
        recyclerView = view.findViewById(R.id.recyclerviewalbum);
        textxemthemalbum = view.findViewById(R.id.textviewxenthemalbum);
        textxemthemalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Danhsach_tatcaalbumActivity.class);
                startActivity(intent);
            }
        });
        GetData();  
        return view;
    }

    private void GetData() {
        Data_service data_service = API_service.getService();
        Call<List<Album>> callback = data_service.GetAlbumHot();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                album_adapter = new Album_Adapter(getActivity(),albumArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(album_adapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
