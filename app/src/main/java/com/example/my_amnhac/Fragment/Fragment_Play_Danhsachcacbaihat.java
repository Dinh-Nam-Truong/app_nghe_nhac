package com.example.my_amnhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_amnhac.Activity.PlayNhacActivity;
import com.example.my_amnhac.Adapter.Playnhac_Adapter;
import com.example.my_amnhac.Model.Playlist;
import com.example.my_amnhac.R;

public class Fragment_Play_Danhsachcacbaihat extends Fragment {
    View view;
    RecyclerView recyclerViewplaynhac;
    Playnhac_Adapter playnhac_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danhsachcacbaihat,container,false);
        recyclerViewplaynhac = view.findViewById(R.id.recyclerviewplaybaihat);
        if (PlayNhacActivity.mangbaihat.size() > 0) {
            playnhac_adapter = new Playnhac_Adapter(getActivity(), PlayNhacActivity.mangbaihat);
            recyclerViewplaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewplaynhac.setAdapter(playnhac_adapter);
        }
        return view;
    }
}
