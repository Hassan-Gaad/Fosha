package com.example.fosha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class fullSrcreenActivity extends AppCompatActivity {
    ImageView myImage;
    ProgressBar progressBar;
    private ArrayList<String> photoItemsUrls;
    int position;

    ImageView next;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_srcreen);

        next=findViewById(R.id.imageView_next);
        back=findViewById(R.id.imageView_back);

        photoItemsUrls = getIntent().getStringArrayListExtra("image_urls");
        position=getIntent().getIntExtra("position",0);

        myImage = findViewById(R.id.image_full_screen);
        progressBar=findViewById(R.id.full_screenProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this).load(photoItemsUrls.get(position))
                .placeholder(R.color.black_overlay)
                .error(R.drawable.ic_launcher_background)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .fitCenter()
                .into(myImage);



    }

    public void btnNext(View view){


        if (photoItemsUrls.listIterator(position).hasNext()&&photoItemsUrls.get(position++)!=null){


            loadImage(photoItemsUrls.get(position));
        }else {
            Toast.makeText(this, "No more", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnBack(View view){
        if (photoItemsUrls.listIterator(position).hasPrevious()&&photoItemsUrls.get(position--)!=null){


            loadImage(photoItemsUrls.get(position));
        }else
            Toast.makeText(this, "No more", Toast.LENGTH_SHORT).show();


    }

    private void loadImage(String url){
        Glide.with(this).load(url)
                .placeholder(R.drawable.loading_place_holder)
                .error(R.drawable.ic_launcher_background)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(myImage);
    }
}

