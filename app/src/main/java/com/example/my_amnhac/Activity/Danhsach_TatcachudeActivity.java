package com.example.my_amnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.my_amnhac.Adapter.Danhsachtatcachude_Adapter;
import com.example.my_amnhac.Model.Chude;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Danhsach_TatcachudeActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcachude;
    Toolbar toolbartatcachude;
    Danhsachtatcachude_Adapter danhsachtatcachude_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_tatcachude);
        anhxa();
        init();
        GetData();
    }


    private void GetData() {
        Data_service data_service = API_service.getService();
        Call<List<Chude>> callback = data_service.GetAllchude();
        callback.enqueue(new Callback<List<Chude>>() {
            @Override
            public void onResponse(Call<List<Chude>> call, Response<List<Chude>> response) {
                ArrayList<Chude> mangchude = (ArrayList<Chude>) response.body();
                danhsachtatcachude_adapter = new Danhsachtatcachude_Adapter(Danhsach_TatcachudeActivity.this,mangchude);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(Danhsach_TatcachudeActivity.this,1));
                recyclerViewtatcachude.setAdapter(danhsachtatcachude_adapter);
            }

            @Override
            public void onFailure(Call<List<Chude>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhxa() {
        recyclerViewtatcachude = findViewById(R.id.recyclerviewallchude);
        toolbartatcachude = findViewById(R.id.toolbarallchude);
    }
}