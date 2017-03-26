package edu.miami.c11926684.bigapp2.Services;

import edu.miami.c11926684.bigapp2.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by woodyjean-louis on 10/12/16.
 */

public class ServiceGenerator{

    public static GetTokenService createTokenService(){
        return getRetrofit().create(GetTokenService.class);
    }

    //this is my adapter...
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

