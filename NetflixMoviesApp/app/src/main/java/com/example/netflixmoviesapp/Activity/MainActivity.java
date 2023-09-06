package com.example.netflixmoviesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.netflixmoviesapp.Adapter.FilmListAdapter;
import com.example.netflixmoviesapp.Domain.FilmItem;
import com.example.netflixmoviesapp.Domain.ListFilm;
import com.example.netflixmoviesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    //loading and requests
private RecyclerView.Adapter adapterNewMovies,adapterUpComing;
private RecyclerView recyclerViewNewMovies, recyclerViewUpComing;
private RequestQueue mRequestQueue;
private StringRequest mStringRequest, mStringRequest2;
private ProgressBar loading1, loading2;

//fab button and nav bar
   FloatingActionButton fabAdd, fabPerson, fabSettings, fabFav, fabCart;
    boolean isAllVisible;
@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
    sendRequest1();
    sendRequest2();


    }
    private void sendRequest1() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
            Gson gson=new Gson();
            loading1.setVisibility(View.GONE);

            ListFilm items=gson.fromJson(response, ListFilm.class);
            adapterNewMovies= new FilmListAdapter(items);
            recyclerViewNewMovies.setAdapter(adapterNewMovies);

        }, error -> {
            Log.i("SID DEV", "sendRequest1: "+error.toString());
            loading1.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest);
    }

    private void sendRequest2() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3", response -> {
            Gson gson=new Gson();
            loading2.setVisibility(View.GONE);

            ListFilm items=gson.fromJson(response, ListFilm.class);
            adapterUpComing= new FilmListAdapter(items);
            recyclerViewUpComing.setAdapter(adapterUpComing);

        }, error -> {
            loading2.setVisibility(View.GONE);
        });
        mRequestQueue.add(mStringRequest2);
    }

    private void initView() {
        recyclerViewNewMovies=findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing=findViewById(R.id.view2);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loading1=findViewById(R.id.loading1);
        loading2=findViewById(R.id.loading2);

        fabAdd=findViewById(R.id.addFab);
        fabSettings=findViewById(R.id.settingsFab);
        fabPerson=findViewById(R.id.personFab);
        fabFav=findViewById(R.id.favFab);
        fabCart=findViewById(R.id.cartFab);

        fabSettings.setVisibility(View.GONE);
        fabPerson.setVisibility(View.GONE);


        isAllVisible = false;

        fabAdd.setOnClickListener(view ->{
            if(!isAllVisible){
                fabPerson.show();
                fabSettings.show();
                isAllVisible=true;
            } else{
                fabPerson.hide();
                fabSettings.hide();
                isAllVisible=false;
            }
        });
        fabPerson.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Account Clicked", Toast.LENGTH_SHORT
                ).show());

        // below is the sample action to handle add alarm FAB. Here it shows simple Toast msg
        // The Toast will be shown only when they are visible and only when user clicks on them
        fabSettings.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Settings Clicked", Toast.LENGTH_SHORT
                ).show());

        fabFav.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Added to Favorites", Toast.LENGTH_SHORT
                ).show());
        fabCart.setOnClickListener(
                view -> Toast.makeText(MainActivity.this, "Added to Cart", Toast.LENGTH_SHORT
                ).show());
    }
}