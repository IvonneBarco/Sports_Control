package com.practicavolley.ennovic.sportscontrol.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.practicavolley.ennovic.sportscontrol.R;

public class SplashSreen extends Activity {

    ImageView img_splash;
    private static final int DURACION = 1500;
    private static final int TIEMPO_DESPUES = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);

        final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(DURACION);
        fadeIn.setStartOffset(TIEMPO_DESPUES);
        fadeIn.setFillAfter(true);

        img_splash = (ImageView) findViewById(R.id.id_imagen_splash);

        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(DURACION);
        fadeOut.setStartOffset(TIEMPO_DESPUES);
        fadeOut.setFillAfter(true);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                img_splash.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                img_splash.startAnimation(fadeOut);
            }
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_splash.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                img_splash.startAnimation(fadeIn);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashSreen.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },4000);
    }
}
