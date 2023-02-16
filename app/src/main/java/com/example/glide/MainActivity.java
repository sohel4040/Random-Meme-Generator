package com.example.glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button share,next;
    RequestQueue rq;
    ProgressBar pb;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= findViewById(R.id.imageView);
        next= findViewById(R.id.next);
        share= findViewById(R.id.share);
        pb= findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        rq= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, "https://meme-api.herokuapp.com/gimme", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    url= response.getString("url");
                    Glide.with(MainActivity.this)
                            .load(url)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    pb.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .error(R.drawable.error)
                            .into(imageView);
                }

                catch(Exception e)
                {
                    Log.d("sohel","Exception Occured");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sohel","Something went wrong");
            }
        });
        rq.add(request);





        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                rq= Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, "https://meme-api.herokuapp.com/gimme", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            url= response.getString("url");
                            Glide.with(MainActivity.this)
                                    .load(url)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            pb.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .error(R.drawable.error)
                                    .into(imageView);
                        }

                        catch(Exception e)
                        {
                            Log.d("sohel","Exception Occured");
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sohel","Something went wrong");
                    }
                });
                rq.add(request);
            }
        });
    share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this cool meme for fun "+url);

            Intent i=Intent.createChooser(intent,"Share this meme using ..");
            startActivity(i);
        }
    });

    }

}