/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */

package com.saptrishi.hotelkotapp.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saptrishi.hotelkotapp.R;
import com.saptrishi.hotelkotapp.model.MySqliteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.acra.ACRA.log;

import androidx.fragment.app.FragmentActivity;


public class SplashActivity extends FragmentActivity {

    String DateOfRegistration;
    private Animation animation;
    private ImageView logo;
    private TextView appTitle;
    private TextView appSlogan;
    String valid_until;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        logo = (ImageView) findViewById(R.id.logo_img);
        appTitle = (TextView) findViewById(R.id.track_txt);
        appSlogan = (TextView) findViewById(R.id.pro_txt);
         relativeLayout =(RelativeLayout)findViewById(R.id.sb_rellayout);


//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
//        DateOfRegistration =  mdformat.format(calendar.getTime());
//
//
//        Cursor c=new MySqliteDatabase(this).fetchExpiryDate();
//        if(c.moveToFirst())
//        {
//            valid_until=c.getString(0);
//        }
//        Log.e("spalsh",c.moveToFirst()+"");
//        Log.e("spalsh",valid_until+"");
//



        // Font path
//        String fontPath = "font/Roboto-Bold.ttf";
//        // Loading Font Face
//        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);

        // Applying font
//        appTitle.setTypeface(tf);
//        appSlogan.setTypeface(tf);

        if (savedInstanceState == null) {
            flyIn();
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                endSplash();
            }
        }, 5000);
    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);
    }

    private void endSplash() {
        animation = AnimationUtils.loadAnimation(this,
                R.anim.logo_animation_back);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation_back);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.pro_animation_back);
        appSlogan.startAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                SharedPreferences sf=getSharedPreferences("Restaurant",Context.MODE_PRIVATE);

                String chk=sf.getString("DeviceRegistrationValue","");
                String loginId=sf.getString("loginId","");

                Log.e("false",chk);
                if(chk.equals("true"))
                {
                    if(loginId.equals(""))
                    {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }

                    else
                    {
                        String Appname=sf.getString("Appname","");
                        String AppIp=sf.getString("Ip","");
                        String loginIdd=sf.getString("loginId","");


                        if(Appname.equals("") && AppIp.equals("") )
                        {
                            Intent intent = new Intent(getApplicationContext(),ServiceSettingActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(),RestaurantListActivity.class);
                            startActivity(intent);
                            finish();


                        }

                    }

                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(),Device_Registration.class);
                    startActivity(intent);
                    finish();


                }

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

}
