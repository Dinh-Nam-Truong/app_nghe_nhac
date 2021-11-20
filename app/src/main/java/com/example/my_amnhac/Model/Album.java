package com.example.my_amnhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {

@SerializedName("Idalbum")
@Expose
private String idalbum;
@SerializedName("Tenalbum")
@Expose
private String tenalbum;
@SerializedName("Tencasialbum")
@Expose
private String tencasialbum;
@SerializedName("Hinhalbum")
@Expose
private String hinhalbum;

public String getIdalbum() {
return idalbum;
}

public void setIdalbum(String idalbum) {
this.idalbum = idalbum;
}

public String getTenalbum() {
return tenalbum;
}

public void setTenalbum(String tenalbum) {
this.tenalbum = tenalbum;
}

public String getTencasialbum() {
return tencasialbum;
}

public void setTencasialbum(String tencasialbum) {
this.tencasialbum = tencasialbum;
}

public String getHinhalbum() {
return hinhalbum;
}

public void setHinhalbum(String hinhalbum) {
this.hinhalbum = hinhalbum;
}

}