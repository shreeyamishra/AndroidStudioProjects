package edu.miami.c11926684.bigapp2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.miami.c11926684.bigapp2.Services.ServiceManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by woodyjean-louis on 10/12/16.
 */

public class SearchActivity extends AppCompatActivity {
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;
    EditText mText;
    String mAuthToken;
    private String mQuery;

    List<GalleryImage> mPictures;
    private String mMaxId, mMinId;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        mAuthToken = intent.getStringExtra("AUTH_TOKEN");
        System.out.println("In searchAct mAuthToken: " + mAuthToken);

        //Testing purposes you should change!!
        //mMinId = "1390922265529";
        //mMaxId = "1390922265528";
        //------

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) { // only when scrolling up

                    final int visibleThreshold = 2;

                    GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
                    int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = layoutManager.getItemCount();

                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view
                        // load content in background

                        getTagResults(mQuery, "", "");

                    }
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

//
        mSearchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                getTagResults(query, "", "");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }



    private void getTagResults(String query, String minId, String maxId) {
        showPD();
        Call<ResponseBody> response = ServiceManager.createService().getResponse(query, mAuthToken, "", "");//minId, maxId);
        System.out.println(" At call mAuthToken: " + mAuthToken);
        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //response = ServiceManager.createService().getResponse("dogs",  "1390922265529", "1390922265528");
                if (response.isSuccessful()) {
                    System.out.println("response was successful");
                    hidePD();
                    mPictures = new ArrayList<GalleryImage>();
                    System.out.println(response.body());
                    //mEditText.setText("secces");
                    StringBuilder sb = new StringBuilder();
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }

                        JSONObject tagResponse = new JSONObject(sb.toString());

                        for (int i = 0; i < tagResponse.length() - 2; i++) {
                            JSONObject pagination = (JSONObject) tagResponse.getJSONObject("pagination");
                            System.out.println(pagination);

                            //mMaxId = pagination.getString("max_tag_id");//("next_max_id");
                            mMinId = pagination.getString("min_tag_id");//("next_min_id");

                            JSONObject meta = tagResponse.getJSONObject("meta");
                            JSONArray data = tagResponse.getJSONArray("data");

                            for (int j = 0; j < data.length(); j++) {

                                JSONArray tags = data.getJSONObject(j).getJSONArray("tags");


                                JSONObject image = data.getJSONObject(j).getJSONObject("images").getJSONObject("low_resolution");


                                GalleryImage photo = new GalleryImage(null, image.getString("url"), null);
                                //photo.addTag();
                                mPictures.add(photo);

                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    System.out.println("response was not successful");
                }
                if (mPictures != null) {
                    System.out.println(mPictures);
                    GalleryAdapter adapter = new GalleryAdapter(mPictures);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
            }

        });


    }

    private void showPD(){

        if(mProgressDialog==null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    private void hidePD(){
        if(mProgressDialog!= null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

    }
}
