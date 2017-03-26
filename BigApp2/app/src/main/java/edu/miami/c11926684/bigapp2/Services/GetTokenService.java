package edu.miami.c11926684.bigapp2.Services;


import edu.miami.c11926684.bigapp2.SupportingFiles.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 * Created by woodyjean-louis on 10/12/16.
 */

public interface GetTokenService {

//    @POST("/oauth/access_token")
//      Call<TokenResponse> getAccessToken(@Body TokenRequest tokenRequest); //so this returns a token response and we need to build a pojo for that.

    @FormUrlEncoded
    @POST("/oauth/access_token")
    Call<TokenResponse> getAccessToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                                       @Field("redirect_uri") String redirect_uri, @Field("grant_type") String grant_type,
                                       @Field("code") String code);
}
