package com.bojie.materialtest.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bojie.materialtest.material.MyApplication;

/**
 * Created by bojiejiang on 5/2/15.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> mLruCache = new LruCache<>((int) Runtime
                    .getRuntime().maxMemory() / 1024 / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return mLruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mLruCache.put(url, bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {

        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
