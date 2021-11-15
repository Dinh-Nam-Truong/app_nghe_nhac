package com.example.my_amnhac.Service;

import com.example.my_amnhac.Model.Playlist;
import com.example.my_amnhac.Model.QuangCao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Data_service {

    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("playlistforcurentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();
}
