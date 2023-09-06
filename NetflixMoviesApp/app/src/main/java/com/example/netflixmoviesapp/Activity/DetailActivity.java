package com.example.netflixmoviesapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.netflixmoviesapp.Adapter.ImageListAdapter;
import com.example.netflixmoviesapp.Domain.FilmItem;
import com.example.netflixmoviesapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;


public class DetailActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieDateTxt, movieSummaryInfo, movieActorsInfo;
    private NestedScrollView scrollView;
    private int idFilm;
    private ShapeableImageView pic1;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterImgList;
    private RecyclerView recyclerView;
    private ImageView detailFav;
    private boolean clicked;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idFilm=getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();

    }

    private void sendRequest() {

        mRequestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/"+idFilm, response -> {
            Gson gson=new Gson();
            progressBar.setVisibility(View.GONE);

            FilmItem item=gson.fromJson(response, FilmItem.class);
            Glide.with(DetailActivity.this).load(item.getPoster()).into(pic1);
            Glide.with(DetailActivity.this).load(item.getPoster()).into(pic2);
            titleTxt.setText(item.getTitle());
            movieRateTxt.setText(item.getImdbRating());
            movieTimeTxt.setText(item.getRuntime());
            movieDateTxt.setText(item.getReleased());
            movieSummaryInfo.setText(item.getPlot());
            movieActorsInfo.setText(item.getActors());

            if(item.getImages()!=null){
                adapterImgList=new ImageListAdapter(item.getImages());
                recyclerView.setAdapter(adapterImgList);
            }

        }, error -> {
            progressBar.setVisibility(View.GONE);
            Log.i("SID DEV", "onErrorResponse: "+error.toString());
        });
        mRequestQueue.add(mStringRequest);
    }

    private void initView() {
        detailFav=findViewById(R.id.detailFav);
        titleTxt=findViewById(R.id.movieNameTxt);
        progressBar=findViewById(R.id.detailLoading);
        scrollView=findViewById(R.id.scrollView3);
        pic1=findViewById(R.id.posterNormalimg);
        pic2=findViewById(R.id.posterBigimg);
        movieRateTxt=findViewById(R.id.movieRateTxt);
        movieTimeTxt=findViewById(R.id.movieTimeTxt);
        movieDateTxt=findViewById(R.id.movieDateTxt);
        movieSummaryInfo=findViewById(R.id.movieSummaryInfo);
        movieActorsInfo=findViewById(R.id.movieActorInfo);
        backImg=findViewById(R.id.backImg);
        recyclerView=findViewById(R.id.imagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backImg.setOnClickListener(view -> finish());
        clicked=false;
        detailFav.setOnClickListener(view -> {
            if(!clicked){

            color = Color.argb(255, 220, 20, 60);
            Toast.makeText(DetailActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();


        } else{
                color=Color.argb(255, 40, 45, 50);
            }
            clicked=!clicked;
            detailFav.setBackgroundTintList(ColorStateList.valueOf(color));
        });


}}