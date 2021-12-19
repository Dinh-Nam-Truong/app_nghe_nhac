package com.example.my_amnhac.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Adapter.Seachbaihat_Adapter;
import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.API_service;
import com.example.my_amnhac.Service.Data_service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_timkiem extends Fragment {

    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewseachbaihat;
    TextView textkhongcodulieu;
    Seachbaihat_Adapter seachbaihat_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timkiem,container,false);
        toolbar = view.findViewById(R.id.toolbarseachbaihat);
        recyclerViewseachbaihat = view.findViewById(R.id.recyclerviewseachbaihat);
        textkhongcodulieu = view.findViewById(R.id.textviewkhongcodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.seach_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_seach);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SeachTukhoabaihat(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SeachTukhoabaihat(String query){
        Data_service data_service = API_service.getService();
        Call<List<Baihat>> callback = data_service.GetSeachbaihat(query);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                ArrayList<Baihat> mangbaihat = (ArrayList<Baihat>) response.body();
                if (mangbaihat.size() > 0){
                    seachbaihat_adapter = new Seachbaihat_Adapter(getActivity(),mangbaihat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewseachbaihat.setLayoutManager(linearLayoutManager);
                    recyclerViewseachbaihat.setAdapter(seachbaihat_adapter);
                    textkhongcodulieu.setVisibility(View.GONE);
                    recyclerViewseachbaihat.setVisibility(View.VISIBLE);
                }else {
                    recyclerViewseachbaihat.setVisibility(View.GONE);
                    textkhongcodulieu.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}
