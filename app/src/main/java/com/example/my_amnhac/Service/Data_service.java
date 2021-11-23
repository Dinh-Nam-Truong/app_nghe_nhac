package com.example.my_amnhac.Service;

import com.example.my_amnhac.Model.Album;
import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.Model.Chude;
import com.example.my_amnhac.Model.Chudevatheloai;
import com.example.my_amnhac.Model.Playlist;
import com.example.my_amnhac.Model.QuangCao;
import com.example.my_amnhac.Model.Theloai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Data_service {

    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();

    @GET("playlistforcurentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("chudevatheloaitrongngay.php")
    Call<Chudevatheloai> GetDataChuDeVaTheLoai();

    @GET("albumhost.php")
    Call<List<Album>> GetAlbumHot();

    @GET("baihatduocthich.php")
    Call<List<Baihat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("dangsachcacplaylist.php")
    Call<List<Playlist>> GetDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<Chude>> GetAllchude();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<Theloai>> GetTheloaitheochude(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GetAllalbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihatthealbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);

    @FormUrlEncoded
    @POST("seachbaihat.php")
    Call<List<Baihat>> GetSeachbaihat(@Field("tukhoa") String tukhoa);

}
