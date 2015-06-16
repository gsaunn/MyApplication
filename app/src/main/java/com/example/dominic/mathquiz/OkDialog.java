package com.example.dominic.mathquiz;

/**
 * Created by dominic on 15/06/2015.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spannable;
import android.widget.TextView;

public class OkDialog
{
    AlertDialog.Builder alert;

    protected OkDialog(Context paramContext, String paramString, Spannable paramSpannable)
    {
        this.alert = new AlertDialog.Builder(paramContext);
        this.alert.setTitle(paramString);
        TextView localTextView = new TextView(paramContext);
        localTextView.setTextSize(20.0F);
        localTextView.setText(paramSpannable, TextView.BufferType.SPANNABLE);
        this.alert.setView(localTextView);
        this.alert.setPositiveButton(TextConstants.get(R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                OkDialog.this.clickOk();
            }
        });
    }

    public void clickOk() {}

    public void show()
    {
        this.alert.show();
    }
}
