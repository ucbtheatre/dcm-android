package com.ucb.dcm.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import com.ucb.dcm.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kurtguenther on 6/20/13.
 */
public class ImageUtil {
    public static void setImageFromUrl(String url, final ImageView view){
        AsyncTask<String, Integer, Boolean> o = new AsyncTask<String, Integer, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    final Bitmap bitmap = BitmapFactory
                            .decodeStream((InputStream) new URL(params[0])
                            .getContent());

                    if(bitmap != null){
                        Activity aaa = (Activity) view.getContext();
                        aaa.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.setImageBitmap(bitmap);
                            }
                        });
                    }
                    else {
                        return false;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (OutOfMemoryError e){
                    e.printStackTrace();
                    return false;
                }

                return true;
            }

        };

        o.execute(url);
    }
}
