package com.example.dominic.mathquiz;

import android.content.res.Resources;

/**
 * Created by dominic on 14/06/2015.
 */
public class TextConstants {
    public static boolean FLURRY_ON = false;
    public static final int MAX_CATEGORIES = 7;
    public static final int MAX_QUESTIONS = 5;
    private static Resources resources = null;

    public static String get(int paramInt)
    {
        return resources.getString(paramInt);
    }

    public static void setResources(Resources paramResources)
    {
        resources = paramResources;
    }
}
