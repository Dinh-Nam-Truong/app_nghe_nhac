package com.example.my_amnhac.Service;

public class API_service {
    private static String base_url = "https://my-am-nhac.000webhostapp.com/sever/";

    public static Data_service getService(){
        return API_Retrofit_Client.getClient(base_url).create(Data_service.class);
    }
}
