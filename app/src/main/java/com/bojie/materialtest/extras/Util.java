package com.bojie.materialtest.extras;

import android.os.Build;

/**
 * Created by bojiejiang on 5/7/15.
 */
public class Util {
    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isJellyBeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16;
    }
}
