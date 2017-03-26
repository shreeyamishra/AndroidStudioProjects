package edu.miami.c11926684.bigapp2.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by woodyjean-louis on 10/12/16.
 */

public class ServiceManager {

    public static InstagramService createService() {
        return getRetrofit().create(InstagramService.class);
    }



    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.instagram.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
