package com.example.my_amnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.my_amnhac.Adapter.Allalbum_Adapter;
import com.example.my_amnhac.Model.Album;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Danhsach_tatcaalbumActivity extends AppCompatActivity {

    RecyclerView recyclerViewallalbum;
    Toolbar toolbaralbum;

    Allalbum_Adapter allalbum_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_tatcaalbum);
        init();
        GetData();
    }

    private void GetData() {
        Data_service data_service = API_service.getService();
        Call<List<Album>> callback = data_service.GetAllalbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangalbum = (ArrayList<Album>) response.body();
                allalbum_adapter = new Allalbum_Adapter(Danhsach_tatcaalbumActivity.this,mangalbum);
                recyclerViewallalbum.setLayoutManager(new GridLayoutManager(Danhsach_tatcaalbumActivity.this,2));
                recyclerViewallalbum.setAdapter(allalbum_adapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewallalbum = findViewById(R.id.recyclerviewallalbum);
        toolbaralbum = findViewById(R.id.toolbarallalbum);
        setSupportActionBar(toolbaralbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Album");
        toolbaralbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}