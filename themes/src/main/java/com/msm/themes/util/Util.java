package com.msm.themes.util;


import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;
import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.msm.themes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Util {


    public static void Tag(Context ctx, String m) {

        Log.d(ctx.getClass().getSimpleName(), m);

    }

    public static MaterialDialog Progress(Context ctx) {

        MaterialDialog p = new MaterialDialog.Builder(ctx)
                .content("Aguarde...")
                .progress(true, 0).build();
        return p;
    }
    public static String ReplaceStrings(final String str) {
        return str.replace(" ", "")
                .replace(".", "").
                        replace("$", "").
                        replace("(", "").
                        replace(")", "").
                        replace("_", "").
                        replace("[", "").
                        replace("]", "").
                        replace("-", "").
                        replace("%", "").
                        replace("*", "");

    }

    public static int parseInt(String str){

        if(!str.contains("null") && str.length() >= 1){
            return   Integer.parseInt(str.replaceAll("\"",""));
        }else{
            return  0;
        }


    }

    public static double parseDouble(String str){
        if(!str.contains("null") && str.length() >= 1){
            return   Double.parseDouble(str.replaceAll("\"",""));
        }else{
            return  0;
        }


    }

    public static String parseString(String str){
        if(!str.contains("null") && str.length() >= 1){
            return  str.replaceAll("\"","");
        }else{
            return  "";
        }

    }

    public static MaterialDialog ProgressNotCancel(Context ctx) {

        MaterialDialog p = new MaterialDialog.Builder(ctx)
                .content("Aguarde...")
                .cancelable(false)
                .progress(true, 0).build();
        return p;
    }

    ///para trabalhar com datas
    @SuppressLint("SimpleDateFormat")
    public static String strToDate(String dateToFormat, String formatIn, String formatOut){
        // format date
        SimpleDateFormat formatFrom = null, formatTo = null;
        if(formatIn != null){
            formatFrom = new SimpleDateFormat(formatIn);
        }

        if(formatOut != null){
            formatTo = new SimpleDateFormat(formatOut);
        }

        if(dateToFormat != null && formatFrom != null  && formatTo != null) {
            try {
                Date mDate = formatFrom.parse(dateToFormat); // <-- aqui ocorre a exceção.
                dateToFormat = formatTo.format(mDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(dateToFormat == null && formatTo != null){
            dateToFormat = formatTo.format(new Date());
        }
        if(dateToFormat == null && formatTo == null){
            dateToFormat =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }

        return dateToFormat;
    }

	public static boolean isOnlineV1(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network internet = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(internet);
        boolean isNetwork = false;
        boolean isInternet = false;
        if(internet != null){
            isNetwork = nc.hasCapability(NET_CAPABILITY_INTERNET);
        }
        if(internet != null) {
            isInternet = nc.hasCapability(NET_CAPABILITY_VALIDATED);
        }
        return  isInternet && isNetwork;

	}

    public static void checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network internet = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(internet);
        boolean isNetwork = false;
        boolean isInternet = false;
        if(internet != null){
            isNetwork = nc.hasCapability(NET_CAPABILITY_INTERNET);
        }
        if(internet != null) {
            isInternet = nc.hasCapability(NET_CAPABILITY_VALIDATED);
        }
        if(!(isInternet && isNetwork)) {

            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_internet_alert, null);
            Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP,0,0);
            toast.setView(view);
            toast.show();

        }
    }
    public static boolean verificaInternetStatus(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        Network internet = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(internet);
        boolean isNetwork = false;
        boolean isInternet = false;
        if(internet != null){
            isNetwork = nc.hasCapability(NET_CAPABILITY_INTERNET);
        }
        if(internet != null) {
            isInternet = nc.hasCapability(NET_CAPABILITY_VALIDATED);
        }
        return  isInternet && isNetwork;
    }

    public static boolean isOnlineWifi(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        Network internet = cm.getActiveNetwork();
        NetworkCapabilities nc = cm.getNetworkCapabilities(internet);
        boolean isNetwork = false;
        boolean isInternet = false;
        boolean isOnlineWifi = false;
        if(internet != null){
            isNetwork = nc.hasCapability(NET_CAPABILITY_INTERNET);
        }
        if(internet != null) {
            isInternet = nc.hasCapability(NET_CAPABILITY_VALIDATED);
        }

        if ( isInternet && isNetwork && info != null) {
            // connected to the internet
            // connected to wifi
            isOnlineWifi =  info.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return  isOnlineWifi;
    }


    public static void showAviso(Context ctx, Drawable icon, String title, String msm) {
        new MaterialDialog.Builder(ctx)
                .title(title)
                .icon(icon)
                .content(msm)
                .positiveText("Ok").show();
    }

    public static double distanceBetweendouble(double lat,double lng,double lat2, double lng2) {
        Location loc1 = new Location(LocationManager.GPS_PROVIDER);
        Location loc2 = new Location(LocationManager.GPS_PROVIDER);
        loc1.setLatitude(lat);
        loc1.setLongitude(lng);
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);


        return loc1.distanceTo(loc2);
    }
    public static String  distEntrePontos(int valor){

        String km;
        String m;
        String rt = null;

        if(valor >= 1000){

            km = String.valueOf(valor).substring(0,String.valueOf(valor).length() - 3) + " Km";

            m =  String.valueOf(valor).substring(String.valueOf(valor).length() - 3);
            if(!m.isEmpty()){
                m +=" m";
            }
            rt = km + " "+ m;
        }else if(valor < 1000){

            m =  String.valueOf(valor) + " m";

            rt = m;
        }

        return rt;
    }


    //convert Text from bitmap
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public static boolean checkPermission(AppCompatActivity activity, int requestCode, String... permissions) {
        List<String> list = new ArrayList<String>();
        for (String permission : permissions) {
            // Valida permissão
            boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (! ok ) {
                list.add(permission);
            }
        }
        if (list.isEmpty()) {
            // Tudo ok, retorna true
            return true;
        }

        // Lista de permissões que falta acesso.
        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        // Solicita permissão
        ActivityCompat.requestPermissions(activity, newPermissions, requestCode);

        return false;
    }



    public static void formatResultHtml(TextView textView, String result){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(result, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(result));
        }
    }
}

