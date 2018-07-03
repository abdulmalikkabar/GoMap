package com.example.malick.apk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.icu.util.RangeValueIterator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ActivityAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Advertise Here");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.a)
                .setDescription("Aplikasi Pencari Lokasi Spbu,ATM dan Bank")
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(adsElement)
                .addGroup("Hubungi Saya")
                .addEmail("gomaps212@gmail.com")
                .addWebsite("https://gomaps.000webhostapp.com")
                .addFacebook("Gomaps")
                .addInstagram("Gomaps")
                .addItem(getCopyRightsElement())
                .create();


        setContentView(aboutPage);


    }



    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
       // copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityAbout.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }



}
