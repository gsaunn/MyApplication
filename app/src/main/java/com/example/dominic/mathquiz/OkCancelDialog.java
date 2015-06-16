package com.example.dominic.mathquiz;

/**
 * Created by dominic on 15/06/2015.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class OkCancelDialog
{
    AlertDialog.Builder alert;

    protected OkCancelDialog(Context paramContext, String paramString1, String paramString2)
    {
        this.alert = new AlertDialog.Builder(paramContext);
        this.alert.setTitle(paramString1);
        this.alert.setMessage(paramString2);
        this.alert.setPositiveButton(TextConstants.get(R.string.yes), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                OkCancelDialog.this.clickOk();
            }
        });
        this.alert.setNegativeButton(TextConstants.get(R.string.no), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                OkCancelDialog.this.clickCancel();
            }
        });
    }

    public void clickCancel() {}

    public void clickOk() {}

    public void show()
    {
        this.alert.show();
    }
}
