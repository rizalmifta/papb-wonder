package com.nubdev.customwidgetapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String mSharedPrefFile =
            "com.example.android.appwidgetsample";
    private static final String COUNT_KEY = "count";
    private static Calendar today = Calendar.getInstance();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences prefs = context.getSharedPreferences(
                mSharedPrefFile, 0);
        int count = prefs.getInt(COUNT_KEY + appWidgetId, 0);
        count++;

        String timePattern = "HH:mm:ss";
        String datePattern = "dd MMMM yyyy";


        String[] days = new String[] { "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu" };
        String day = days[today.get(Calendar.DAY_OF_WEEK)-1];

        String dateString =
                DateFormat.getTimeInstance(DateFormat.DEFAULT).format(new Date());
        String tanggal = DateFormat.getDateInstance(DateFormat.DEFAULT).format(new Date());

        Log.d("test", String.valueOf(appWidgetId));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);



        context.getResources().getString(
                R.string.date_count_format, count);

        views.setTextViewText(R.id.appwidget_update,
                context.getResources().getString(
                        R.string.date_update_format, day, dateString));
        views.setTextViewText(R.id.date," "+ tanggal);


        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putInt(COUNT_KEY + appWidgetId, count);
        prefEditor.apply();

        Intent intentUpdate = new Intent(context, NewAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,
                0,intent,0);

        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate);
        //otomatis update
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+1);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        AlarmManager alarmManager=(AlarmManager)context.getSystemService(
                Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC,
                calendar.getTimeInMillis(),
                60*1000,pendingUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

