/*
 * 
 */
package com.demo.app.kyc.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.util.Log;
import android.util.TypedValue;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *
 */
public class Util
{
    private static final String TAG = Util.class.getName();
    
    /**
     * to show the debugs logs or not. Set it to false in production.
     */
    public static final boolean LOG_ENABLED = true;

    public static String getDisplayFromattedStringFromDateString(int year, int month, int day){
        String retString = null;
        Date date = new Date(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        retString = sdf.format(date);
        return retString;
    }

    public static int get4DigitYear(int year,int month,int day){
        Date date = new Date(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        int ret_year = Integer.parseInt(sdf.format(date).split("/")[0]);
        return ret_year;
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    /**
     * 
     * @param context
     * @param message
     */
    public static AlertDialog showDialog(final Activity context, String message)
    {
        Util.lockScreen(context);
        
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Util.dismissDialog((Dialog) dialog);
                
                Util.unLockScreen(context);
            }
        };
        return showDialog(context, message, clickListener);
    }

    /**
     * @param context
     * @param message
     * @param clickListener
     * @return
     */
    public static AlertDialog showDialog(Activity context, String message, DialogInterface.OnClickListener clickListener)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", clickListener);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        return alertDialog;
    }
    /**
     * @param context
     * @param message
     * @param okListener
     * @param noListener
     * @return
     */
    public static AlertDialog showDialog(Activity context, String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener noListener)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", okListener);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", noListener);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        return alertDialog;
    }

    /**
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog showProgessDialog(Activity context, String message)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        return progressDialog;
    }

    /**
     * 
     * @param bitmap
     * @param context
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, Context context)
    {
        if (bitmap == null)
        {
            return null;
        }
        
        //dpi corner circle radius
        int cornerCircleRadiusDpi = 20;
        
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) cornerCircleRadiusDpi, context.getResources()
                .getDisplayMetrics());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * Encapsulated behavior for dismissing Dialogs, because of several android problems
     * related.
     */
    public static void dismissDialog(Dialog dialog)
    {
        if (dialog != null && dialog.isShowing())
        {
            try
            {
                dialog.setCancelable(true);
                dialog.dismiss();
            } catch (IllegalArgumentException e) // even sometimes happens?: http://stackoverflow.com/questions/12533677/illegalargumentexception-when-dismissing-dialog-in-async-task
            {
                Log.i(TAG, "Error when attempting to dismiss dialog, it is an android problem.", e);
            }
        }
    }

    /**
     * @return
     */
    public static boolean isTablet(Activity activity)
    {
        return false;
    }

    /**
     * blocks the change screen orientation android feature.
     */
    public static void lockScreen(Activity activity)
    {
        if (isTablet(activity)) // with tablet the screen rotates, block it.
        {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 
     */
    public static void unLockScreen(Activity activity)
    {
        if (isTablet(activity)) // with tablet the screen rotates, unblock it.
        {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }



}
