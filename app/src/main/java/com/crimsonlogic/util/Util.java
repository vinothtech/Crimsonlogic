package com.crimsonlogic.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crimsonlogic.R;

public class Util {

    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable progressDrawable) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(progressDrawable);
        requestOptions.error(R.mipmap.ic_launcher);
        Glide.with(imageView.getContext()).setDefaultRequestOptions(requestOptions).load(url)
                .into(imageView);
    }


    public static CircularProgressDrawable getProgressDrawable(Context contex) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(contex);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.start();
        return progressDrawable;

    }

    @BindingAdapter("android:imageHelper")
    public static void loadImages(ImageView imageView, String url) {
        loadImage(imageView, url, getProgressDrawable(imageView.getContext()));
    }

}
