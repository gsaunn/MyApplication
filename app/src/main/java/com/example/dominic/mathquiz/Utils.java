package com.example.dominic.mathquiz;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by dominic on 14/06/2015.
 */
public class Utils {
    private static Resources resources;

    public static String get(int paramInt)
    {
        return resources.getString(paramInt);
    }

    public static Resources getResources()
    {
        return resources;
    }

    public static void setResources(Resources paramResources)
    {
        resources = paramResources;
    }

    public static void showToast(Context paramContext, String paramString)
    {
        showToastLong(paramContext, paramString);
    }

    public static void showToastLong(Context paramContext, String paramString)
    {
        Toast.makeText(paramContext, paramString, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context paramContext, String paramString)
    {
        Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
    }
}
