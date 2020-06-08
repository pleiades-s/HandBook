package com.waterdiary.drinkreminder;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.widget.AppCompatTextView;

import android.widget.ImageView;

import com.waterdiary.drinkreminder.base.MasterBaseAppCompatActivity;
import com.waterdiary.drinkreminder.mywidgets.NewAppWidget;
import com.waterdiary.drinkreminder.utils.URLFactory;

public class Screen_Splash extends MasterBaseAppCompatActivity
{
    android.os.Handler handler;
    Runnable runnable;

    ImageView img_splash_logo;

    int millisecond=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);

//        Intent intent = new Intent(getApplicationContext(), Screen_Dashboard.class);


        img_splash_logo=findViewById(R.id.img_splash_logo);

        Intent intent = new Intent(act, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(act).getAppWidgetIds(new ComponentName(act, NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        act.sendBroadcast(intent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(ph.getFloat(URLFactory.DAILY_WATER)==0)
        {
            URLFactory.DAILY_WATER_VALUE=2500;
        }
        else
        {
            URLFactory.DAILY_WATER_VALUE=ph.getFloat(URLFactory.DAILY_WATER);
        }

        if(sh.check_blank_data(""+ph.getString(URLFactory.WATER_UNIT)))
        {
            URLFactory.WATER_UNIT_VALUE="ml";
        }
        else
        {
            URLFactory.WATER_UNIT_VALUE=ph.getString(URLFactory.WATER_UNIT);
        }

        runnable = new Runnable()
        {
            @Override
            public void run()
            {
                intent = new Intent(Screen_Splash.this, Screen_Dashboard.class);
                startActivity(intent);
                finish();
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, millisecond);
    }
}