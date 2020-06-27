package com.example.mp_plancat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView splash = (ImageView) findViewById(R.id.splash);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(splash);
        Glide.with(this).load(R.drawable.splash).into(gifImage);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(),2500); // 1초 후에 hd handler 실행
    }

    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            Splash.this.finish(); // 로딩페이지 Activity stack 에서 제거
        }
    }

    @Override
    public void onBackPressed(){
        //초반 플래시 화면에서 넘어갈 때 뒤로가기 버튼 못 누르게 함
    }
}
