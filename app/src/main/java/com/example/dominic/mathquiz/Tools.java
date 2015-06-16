package com.example.dominic.mathquiz;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by dominic on 15/06/2015.
 */
public class Tools {
    public static String ToName(int paramInt, Resources paramResources)
    {
        String[] arrayOfString = paramResources.getStringArray(R.array.categoryzer);
        if ((paramInt >= 0) && (paramInt < arrayOfString.length)) {
            return arrayOfString[paramInt];
        }
        return "";
    }

    public static int ToResource(int paramInt)
    {
        if (paramInt== 0){
            return R.raw.questionsalgebra;
        }
        return R.raw.questionsgeometry;
    }

    public static String formatMathHTML(String paramString)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        int j = 0;
        String str1 = paramString.replaceAll("pi", "&#x03c0").replaceAll("delta", "&#x0394").replaceAll("integral", "&#x222B");
        for (;;)
        {
            if (i != 0)
            {
                Log.d("FORMAT MATH", "output = " + localStringBuilder.toString());
                return localStringBuilder.toString();
            }
            int k = str1.indexOf("^", j);
            if (k != -1)
            {
                int m = str1.indexOf(" ", k);
                if (m == -1) {
                    m = str1.length();
                }
                String str2 = new String(str1.substring(k + 1, m));
                Log.d("FORMAT MATH", "index = " + k + ", end = " + m + ", input = " + str1 + ", upper = " + str2);
                localStringBuilder.append(str1.substring(j, k));
                localStringBuilder.append("<sup>" + str2 + "</sup>");
                j = m;
            }
            else
            {
                localStringBuilder.append(str1.substring(j, str1.length()));
                i = 1;
            }
        }
    }

    public static Spannable formatMathSpannable(String paramString)
    {
        SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
        int i = 0;
        int j = 0;
        String str1 = paramString.replaceAll("pi", "π").replaceAll("delta", "Δ").replaceAll("integral", "∫");
        for (;;)
        {
            if (i != 0)
            {
                Log.d("FORMAT MATH", "output = " + localSpannableStringBuilder.toString());
                return localSpannableStringBuilder;
            }
            int k = str1.indexOf("^", j);
            if (k != -1)
            {
                int m = str1.indexOf(" ", k);
                if (m == -1) {
                    m = str1.length();
                }
                String str2 = new String(str1.substring(k + 1, m));
                Log.d("FORMAT MATH", "index = " + k + ", end = " + m + ", input = " + str1 + ", upper = " + str2);
                localSpannableStringBuilder.append(str1.substring(j, k));
                int n = localSpannableStringBuilder.length();
                int i1 = n + str2.length();
                localSpannableStringBuilder.append(str2);
                localSpannableStringBuilder.setSpan(new SuperscriptSpan(), n, i1, 33);
                Log.d("FORMAT MATH SPANNABLE", "start2 = " + n + ", end2 = " + i1 + ", upper = " + str2);
                j = m;
            }
            else
            {
                localSpannableStringBuilder.append(str1.substring(j, str1.length()));
                i = 1;
            }
        }
    }

    public static StringBuilder readCategory(int paramInt, Resources paramResources)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        try
        {
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramResources.openRawResource(paramInt), "ISO-8859-1"));
            for (;;)
            {
                if (!localBufferedReader.ready())
                {
                    localBufferedReader.close();
                    return localStringBuilder;
                }
                localStringBuilder.append(localBufferedReader.readLine());
                localStringBuilder.append("\n");
            }
        }
        catch (Exception localException) {}
        return localStringBuilder;
    }
}
