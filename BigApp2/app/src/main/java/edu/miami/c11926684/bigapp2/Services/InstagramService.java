package edu.miami.c11926684.bigapp2.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 * Created by woodyjean-louis on 10/12/16.
 */

public interface InstagramService {

    //    @Headers({"Content-Length: 239", "Accept: text/plain, */*; q=0.01", "Content-Type: application/x-www-form-urlencoded;charset=UTF-8",
//            "Accept-Encoding: gzip, deflate", "Accept-Language: en-US,en;q=0.8",
//            "Host: api.instagram.com" , "X-Target-URI: https://api.instagram.com", "Connection: Keep-Alive"})
    @GET("/v1/tags/{tag-name}/media/recent")
//    Call<ResponseBody> getResponse(@Path("tag-name") String tagName, @Query("access_token") String accessToken,
//                                   @Query("max_id") String maxId, @Query("min_id") String minId);
    Call<ResponseBody> getResponse(@Path("tag-name") String tagName, @Query("access_token") String accessToken,
                                   @Query("max_id") String maxId, @Query("min_id") String minId);
}
