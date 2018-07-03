package com.example.malick.apk.Picasso;

import android.content.Context;
import android.widget.ImageView;

import com.example.malick.apk.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Battosai on 3/14/2017.
 */

public class PicassoClient {

    public static void downloadImage(Context c, String imagUrl, ImageView img)
    {
        if(imagUrl != null && imagUrl.length()>0)
        {
            Picasso.with(c).load(imagUrl).placeholder(R.color.overlayBackground).into(img);
        }else {
            Picasso.with(c).load(R.color.overlayBackground).into(img);
        }
    }

}
