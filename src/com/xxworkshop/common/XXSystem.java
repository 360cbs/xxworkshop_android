/*
 * Copyright (c) 2014 xxworkshop. All rights reserved.
 * Created by Broche Xu on 2/13/14 11:48 AM.
 */

package com.xxworkshop.common;

import android.content.Context;
import com.lurencun.cfuture09.androidkit.utils.lang.Installation;

public class XXSystem {
    public static String getDeviceId(Context context) {
        return Installation.getID(context);
    }

    public static double getTimeStamp() {
        return System.currentTimeMillis()/1000.0;
    }
}
